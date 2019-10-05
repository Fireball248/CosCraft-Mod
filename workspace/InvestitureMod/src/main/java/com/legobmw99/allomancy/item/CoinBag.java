package com.legobmw99.allomancy.item;

import com.legobmw99.allomancy.Allomancy;
import com.legobmw99.allomancy.entities.EntityGoldNugget;
import com.legobmw99.allomancy.entities.EntityIronNugget;
import com.legobmw99.allomancy.util.AllomancyCapability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class CoinBag extends Item {
    public CoinBag(String registryName, String unlocalizedName) {
        super();
        setTranslationKey(Allomancy.MODID + "." + unlocalizedName);
        setRegistryName(registryName);
        setCreativeTab(Allomancy.tabsAllomancy);
        maxStackSize = 1;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {

        ItemStack itemstack = playerIn.capabilities.isCreativeMode && this.findArrow(playerIn) == null ? new ItemStack(Items.GOLD_NUGGET) : this.findArrow(playerIn); //Get the item to be fired. If in creative mode,
        if (itemstack != null && AllomancyCapability.forPlayer(playerIn).getMetalBurning(AllomancyCapability.STEEL)) {                                         // make sure there is always an item available
            
            if (itemstack.getItem() == Items.GOLD_NUGGET) {
                EntityGoldNugget entitygold = new EntityGoldNugget(worldIn, playerIn);
                worldIn.spawnEntity(entitygold);
            }
            
            if (itemstack.getItem() == Items.IRON_NUGGET) {
                EntityIronNugget entityiron = new EntityIronNugget(worldIn, playerIn);
                worldIn.spawnEntity(entityiron);
            }
            
            if (!playerIn.capabilities.isCreativeMode) {
                itemstack.shrink(1);
            }
        }
        return new ActionResult(EnumActionResult.PASS, playerIn.getHeldItem(hand));
    }

    /**
     * Finds ammunition items
     */
    public static ItemStack findArrow(EntityPlayer player) {
        for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            ItemStack itemstack = player.inventory.getStackInSlot(i);
            if (isArrow(itemstack)) {
                if (itemstack.getCount() == 1) {
                    player.inventory.removeStackFromSlot(i);
                    return itemstack;

                }
                return itemstack;
            }
        }

        return null;
    }

    protected static boolean isArrow(ItemStack stack) {
        return stack != null && (stack.getItem() == Items.GOLD_NUGGET || stack.getItem() == Items.IRON_NUGGET /* IRON_NUGGET */);
    }

    @Override
    public int getItemEnchantability() {
        return 0;
    }
}