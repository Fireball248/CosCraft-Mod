package com.legobmw99.allomancy.util;

import com.legobmw99.allomancy.Allomancy;

import com.legobmw99.allomancy.init.ModItems;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RecipeItemVial extends  net.minecraftforge.registries.IForgeRegistryEntry.Impl<IRecipe> implements IRecipe{
	private ItemStack resultItem = ItemStack.EMPTY;
	
	public RecipeItemVial(){
		this.setRegistryName(new ResourceLocation(Allomancy.MODID,"vialrecipefilled"));
	}
	
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
        this.resultItem = ItemStack.EMPTY;

        boolean[] metals = {false,false,false,false,false,false,false,false};
        boolean[] ingredients = {false,false};

        for (int i = 0; i < inv.getSizeInventory(); ++i){
            ItemStack itemstack = inv.getStackInSlot(i);

            if (!itemstack.isEmpty()){
            	if(!itemstack.getItem().getRegistryName().toString().contains("allomancy:flake") && !itemstack.getItem().getRegistryName().toString().equals("allomancy:vial")){
            		return false;
            	}
            	for(int j = 0; j < 8; j++){
            		if(itemstack.getItem() == Item.getByNameOrId("allomancy:flake_" + Allomancy.flakeMetals[j])){
            			ingredients[1] = true;
            			metals[j] = true;
            		}
            	}
            	if(itemstack.getItem() == ModItems.VIAL){
            		ingredients[0] = true;
            	}
            }
        }
        

        
        for (int i = 0; i < inv.getSizeInventory(); ++i){
            ItemStack itemstack = inv.getStackInSlot(i);
            if (!itemstack.isEmpty()){
            	if(itemstack.getItem() == ModItems.VIAL){
            		for(int j = 0; j < metals.length; j++){
            			if(itemstack.getTagCompound() == null){
                    		
            			} else if(itemstack.getTagCompound().hasKey(Allomancy.flakeMetals[j])){
            				if(metals[j] == true && itemstack.getTagCompound().getBoolean(Allomancy.flakeMetals[j]) == metals[j]){
            					return false;
            				} else {
            					metals[j] = metals[j] || itemstack.getTagCompound().getBoolean(Allomancy.flakeMetals[j]);
            				}
            			}
            		}
            	}
            }
        }
        if(ingredients[0] && ingredients[1]){
        	this.resultItem = new ItemStack(ModItems.VIAL, 1);
        	NBTTagCompound nbt = new NBTTagCompound();
        	for(int i = 0; i < metals.length; i++){
        		nbt.setBoolean(Allomancy.flakeMetals[i], metals[i]);
        	}
        	this.resultItem.setTagCompound(nbt);
        	return true;
        } else {
        	return false;
        }
    }

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		return this.resultItem.copy();
	}
	
	@Override
	public boolean isDynamic(){
		return true;
	}

	@Override
	public boolean canFit(int width, int height) {
        return width * height >= 2;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return this.resultItem;
	}

}
