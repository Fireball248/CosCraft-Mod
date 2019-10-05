package com.legobmw99.allomancy.client;

import com.legobmw99.allomancy.Allomancy;
import com.legobmw99.allomancy.init.ModBlocks;
import com.legobmw99.allomancy.init.ModItems;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(value = Side.CLIENT, modid = Allomancy.MODID)
public class ModelRegistrationHandler {
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        registerModel(ModItems.COIN_BAG, 0);
        registerModel(ModItems.VIAL, 0);
        //registerModel(ModItems.MISTCLOAK, 0);
        registerModel(ModItems.GRINDER, 0);
        registerModel(ModItems.FLAKE_IRON, 0);
        registerModel(ModItems.FLAKE_STEEL, 0);
        registerModel(ModItems.FLAKE_TIN, 0);
        registerModel(ModItems.FLAKE_PEWTER, 0);
        registerModel(ModItems.FLAKE_ZINC, 0);
        registerModel(ModItems.FLAKE_BRASS, 0);
        registerModel(ModItems.FLAKE_COPPER, 0);
        registerModel(ModItems.FLAKE_BRONZE, 0);

        registerModel(Item.getItemFromBlock(ModBlocks.IRON_LEVER), 0);
    }

    public static void registerModel(Item item, int meta) {
        ModelLoader.setCustomModelResourceLocation(item, meta,
                new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
