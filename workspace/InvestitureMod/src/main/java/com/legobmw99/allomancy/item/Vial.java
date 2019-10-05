package com.legobmw99.allomancy.item;

import java.util.List;

//import javax.annotation.Nullable;

import com.legobmw99.allomancy.Allomancy;
import com.legobmw99.allomancy.init.ModItems;
import com.legobmw99.allomancy.util.AllomancyCapability;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class Vial extends Item {
    public Vial(String registryName, String unlocalizedName) {
        super();
        setTranslationKey(Allomancy.MODID + "." + unlocalizedName);
        setRegistryName(registryName);
        this.setCreativeTab(Allomancy.tabsAllomancy);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        AllomancyCapability cap;
        cap = AllomancyCapability.forPlayer(entityLiving);


        if (cap == null) {
            return stack;
        }

        if (!stack.hasTagCompound()) {
            return stack;
        }
        
    	for(int i = 0; i < 8; i++){
    		if(stack.getTagCompound().hasKey(Allomancy.flakeMetals[i]) && stack.getTagCompound().getBoolean(Allomancy.flakeMetals[i])){
    			if(cap.getMetalAmounts(i) < 10){
    				cap.setMetalAmounts(i, cap.getMetalAmounts(i) + 1);
    			}
    		}
    	}
    	
        if (!((EntityPlayer) (entityLiving)).capabilities.isCreativeMode) {
            stack.shrink(1);
            ((EntityPlayer) (entityLiving)).inventory.addItemStackToInventory(new ItemStack(ModItems.VIAL, 1));
        }
    	
        return stack;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.DRINK;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 6;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
        AllomancyCapability cap;
        cap = AllomancyCapability.forPlayer(playerIn);
        //If all the ones being filled are full, don't allow
        int filling = 0, full = 0;
        ItemStack itemStackIn = playerIn.getHeldItem(hand);
        if (itemStackIn.getTagCompound() != null) {
            for (int i = 0; i < 8; i++) {
                if (itemStackIn.getTagCompound().hasKey(Allomancy.flakeMetals[i]) && itemStackIn.getTagCompound().getBoolean(Allomancy.flakeMetals[i])) {
                    filling++;
                    if (cap.getMetalAmounts(i) >= 10) {
                        full++;
                    }
                }
            }

            if (filling == full) {
                return new ActionResult(EnumActionResult.FAIL, itemStackIn);
            }

            playerIn.setActiveHand(hand);
            return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
        }
        return new ActionResult(EnumActionResult.FAIL, itemStackIn);
    }
    
	@Override
    public void addInformation(ItemStack stack, World playerIn, List<String> tooltip, ITooltipFlag advanced){
		if(stack.getTagCompound() != null){
			for(int i = 0; i < 8; i++){
				if(stack.getTagCompound().getBoolean(Allomancy.flakeMetals[i])){
					tooltip.add(Allomancy.flakeMetals[i]);
				}
			}
		}
	}


    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return stack.hasTagCompound() ? EnumRarity.UNCOMMON : EnumRarity.COMMON;
    }
    
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
		if (isInCreativeTab(tab)) {
			subItems.add(new ItemStack(this, 1, 0));
			
			ItemStack resultItem = new ItemStack(ModItems.VIAL, 1);
        	NBTTagCompound nbt = new NBTTagCompound();
        	for(int i = 0; i < 8; i++){
        		nbt.setBoolean(Allomancy.flakeMetals[i], true);
        	}
        	resultItem.setTagCompound(nbt);
			subItems.add(resultItem);

		}
	}
    
 
}
