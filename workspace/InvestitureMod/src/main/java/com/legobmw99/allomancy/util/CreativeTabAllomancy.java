package com.legobmw99.allomancy.util;

import com.legobmw99.allomancy.init.ModItems;

import mcp.MethodsReturnNonnullByDefault;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTabAllomancy extends CreativeTabs {
	public CreativeTabAllomancy(int id, String mod_id) {
		super(id, mod_id);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public String getTabLabel() {
		return "Allomancy";
	}

	@SideOnly(Side.CLIENT)
	@Override
	@MethodsReturnNonnullByDefault
	public ItemStack createIcon () {
		return new ItemStack(ModItems.VIAL);
	}
}
