package block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class AshTiles extends Block{
	
	public AshTiles() {
		super(Material.ROCK);
		setCreativeTab(CreativeTabs.MISC);
		setSoundType(SoundType.STONE);
	}
	
}