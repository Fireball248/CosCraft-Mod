package com.legobmw99.allomancy.item;

import com.legobmw99.allomancy.Allomancy;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class Grinder extends Item {

	public Grinder(String registryName, String unlocalizedName) {
		super();
		setTranslationKey(Allomancy.MODID + "." + unlocalizedName);
		setRegistryName(registryName);
		setCreativeTab(Allomancy.tabsAllomancy);
		maxStackSize = 1;
		setContainerItem(this);
	}
}