package init;

import investiture.Investiture;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraft.creativetab.CreativeTabs;

@ObjectHolder(Investiture.MODID)

public class ModItems {	
	public static final Item Aluminum_Ingot = null;

	@EventBusSubscriber(modid = Investiture.MODID)
	public static class RegistrationHandler {
		
		@SubscribeEvent
		public static void registerItems(Register<Item> event) {
			final Item[] items = {
					new Item().setRegistryName(Investiture.MODID, "aluminum_ingot").setTranslationKey(Investiture.MODID + "." + "aluminum_ingot").setCreativeTab(CreativeTabs.MISC)
			};

			event.getRegistry().registerAll(items);
		}
		
	}
	
}