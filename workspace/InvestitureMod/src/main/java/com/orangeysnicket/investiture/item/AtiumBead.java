package com.orangeysnicket.investiture.item;

import com.orangeysnicket.investiture.investiture.Investiture;
import net.minecraft.item.Item;


public class AtiumBead extends Item {

	public AtiumBead(String registryName, String unlocalizedName) {
		setTranslationKey(Investiture.MODID + "." + unlocalizedName);
		setRegistryName(registryName);
		setCreativeTab(Investiture.INVESTITURE_TAB);
	}
}