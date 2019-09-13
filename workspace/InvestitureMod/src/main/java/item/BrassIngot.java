package item;

import investiture.Investiture;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;


public class BrassIngot extends Item {

	public BrassIngot(String registryName, String unlocalizedName) {
		setTranslationKey(Investiture.MODID + "." + unlocalizedName);
		setRegistryName(registryName);
		setCreativeTab(CreativeTabs.MISC);
	}
}
