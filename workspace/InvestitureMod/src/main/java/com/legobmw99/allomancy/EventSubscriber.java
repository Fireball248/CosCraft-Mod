package com.legobmw99.allomancy;

import com.legobmw99.allomancy.block.*;
import com.legobmw99.allomancy.init.ModBlocks;
import com.legobmw99.allomancy.item.*;

import javafx.scene.paint.Material;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = Allomancy.MODID)
public final class EventSubscriber {
    @SubscribeEvent
    public static void registerBlocks(Register<Block> event) {
        final Block[] blocks = {
                new IronLever().setRegistryName("iron_lever").setTranslationKey(Allomancy.MODID + "." + "iron_lever")
        };

        event.getRegistry().registerAll(blocks);
    }

    @SubscribeEvent
    public static void registerItems(Register<Item> event) {
        final Item[] items = {
                new CoinBag("coin_bag", "coin_bag"),
                new Grinder("grinder", "grinder"),
                new Vial("vial", "vial"),
                //new Mistcloak("mistcloak", "mistcloak"),
                new Flake("flake_iron", "flake_iron"),
                new Flake("flake_steel", "flake_steel"),
                new Flake("flake_tin", "flake_tin"),
                new Flake("flake_pewter", "flake_pewter"),
                new Flake("flake_zinc", "flake_zinc"),
                new Flake("flake_brass", "flake_brass"),
                new Flake("flake_copper", "flake_copper"),
                new Flake("flake_bronze", "flake_bronze"),
        };

        final Item[] itemBlocks = {
                new ItemBlock(ModBlocks.IRON_LEVER).setRegistryName(ModBlocks.IRON_LEVER.getRegistryName()),
        };

        event.getRegistry().registerAll(items);
        event.getRegistry().registerAll(itemBlocks);
    }
}
