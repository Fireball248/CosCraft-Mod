package com.legobmw99.allomancy.item;

import com.legobmw99.allomancy.Allomancy;

import net.minecraft.item.Item;

public class Flake extends Item {
    public Flake(String registryName, String unlocalizedName) {
        super();
        setTranslationKey(Allomancy.MODID + "." + unlocalizedName);
        setRegistryName(registryName);
        setCreativeTab(Allomancy.tabsAllomancy);
    }
}
