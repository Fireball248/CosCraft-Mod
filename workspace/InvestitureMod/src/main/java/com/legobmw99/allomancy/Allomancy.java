package com.legobmw99.allomancy;

import java.io.File;

import com.legobmw99.allomancy.handlers.ClientEventHandler;
import com.legobmw99.allomancy.handlers.CommonEventHandler;
import com.legobmw99.allomancy.network.packets.*;
import com.legobmw99.allomancy.util.*;
//import com.legobmw99.allomancy.world.OreGenerator;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

@Mod(modid = Allomancy.MODID, version = Allomancy.VERSION)
public class Allomancy {
	// TEMP
	public static final String[] flakeMetals = { "Iron", "Steel", "Tin", "Pewter", "Zinc", "Brass", "Copper", "Bronze", "Lead" };
	public static SimpleNetworkWrapper network;
	public static KeyBinding burn;

	public void initKeyBindings() {
		burn = new KeyBinding("key.burn", Keyboard.KEY_F, "key.categories.allomancy");
		ClientRegistry.registerKeyBinding(burn);
	}

	public static final String MODID = "allomancy";
	public static final String VERSION = "@VERSION@";

	public static CreativeTabs tabsAllomancy = new CreativeTabAllomancy(CreativeTabs.getNextID(), "allomancy");
	
	public static File configDirectory;

	@SidedProxy
	public static CommonProxy proxy;

	@Instance(value = "allomancy")
	public static Allomancy instance;

	@CapabilityInject(AllomancyCapability.class)
	public static final Capability<AllomancyCapability> PLAYER_CAP = null;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@EventHandler
	public void serverInit(FMLServerStartingEvent event) {
		proxy.serverInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
		initKeyBindings();
		registerPackets();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

	
	
	public static class CommonProxy {
		public void preInit(FMLPreInitializationEvent e) {
			// Load most of the mod's content
			MinecraftForge.EVENT_BUS.register(new CommonEventHandler());
			AllomancyConfig.initProps(e.getSuggestedConfigurationFile());
			configDirectory = e.getModConfigurationDirectory();
			//Registry.registerPackets();
		}

		public void postInit(FMLPostInitializationEvent e) {
			AllomancyUtils.init();

		}

		public void serverInit(FMLServerStartingEvent e) {
			e.registerServerCommand(new PowerCommand());
		}

		public void init(FMLInitializationEvent e) {
			//GameRegistry.registerWorldGenerator(new OreGenerator(), 0);
			AllomancyCapability.register();
		}
	}

	public static class ClientProxy extends CommonProxy {
		@Override
		public void preInit(FMLPreInitializationEvent e){
			super.preInit(e);
			//Registry.registerEntityRenders();
		}
		@Override
		public void init(FMLInitializationEvent e) {
			super.init(e);
			MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
			//Registry.initKeyBindings();
			//Registry.registerItemRenders();

		}
		@Override
		public void postInit(FMLPostInitializationEvent e) {
			super.postInit(e);
		}
	}

	public static class ServerProxy extends CommonProxy {

	}

	public static void registerPackets() {
		network = NetworkRegistry.INSTANCE.newSimpleChannel("allomancy");
		network.registerMessage(AllomancyPowerPacket.Handler.class, AllomancyPowerPacket.class, 0, Side.CLIENT);
		network.registerMessage(UpdateBurnPacket.Handler.class, UpdateBurnPacket.class, 1, Side.SERVER);
		network.registerMessage(AllomancyCapabilityPacket.Handler.class, AllomancyCapabilityPacket.class, 2,Side.CLIENT);
		network.registerMessage(ChangeEmotionPacket.Handler.class, ChangeEmotionPacket.class, 3, Side.SERVER);
		network.registerMessage(GetCapabilitiesPacket.Handler.class, GetCapabilitiesPacket.class, 4, Side.SERVER);
		network.registerMessage(TryPushPullEntity.Handler.class, TryPushPullEntity.class, 5, Side.SERVER);
		network.registerMessage(TryPushPullBlock.Handler.class, TryPushPullBlock.class, 6, Side.SERVER);
	}
}
