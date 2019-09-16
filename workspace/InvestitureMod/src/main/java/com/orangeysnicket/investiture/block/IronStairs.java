package com.orangeysnicket.investiture.block;

import com.orangeysnicket.investiture.investiture.Investiture;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class IronStairs extends Block{
	
	public IronStairs() {
		super(Material.IRON);
		setCreativeTab(Investiture.INVESTITURE_TAB);
		setSoundType(SoundType.METAL);
	}

}