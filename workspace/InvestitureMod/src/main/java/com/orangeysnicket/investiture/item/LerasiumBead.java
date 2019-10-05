package com.orangeysnicket.investiture.item;
import com.orangeysnicket.investiture.Investiture;

import com.legobmw99.allomancy.util.AllomancyCapability;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;

import java.util.List;


public class LerasiumBead extends ItemFood {

	public LerasiumBead(String registryName, String unlocalizedName) {
		super(0, false);
		setAlwaysEdible();
		setHasSubtypes(false);
		setTranslationKey(Investiture.MODID + "." + unlocalizedName);
		setRegistryName(registryName);
		setCreativeTab(Investiture.investitureTab);
		maxStackSize = 1;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.EAT;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 1;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand){
		ItemStack itemStackIn = playerIn.getHeldItem(hand);

		if (Loader.isModLoaded("allomancy")) {
			AllomancyCapability cap = AllomancyCapability.forPlayer(playerIn);
			if (cap.getAllomancyPower() != 8) {
				playerIn.setActiveHand(hand);
				return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);

			} else {
				return new ActionResult(EnumActionResult.FAIL, itemStackIn);
			}
		}
		return new ActionResult(EnumActionResult.FAIL, itemStackIn);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving){
		if (Loader.isModLoaded("allomancy")) {
			AllomancyCapability cap = AllomancyCapability.forPlayer((EntityPlayer) entityLiving);
			double x = entityLiving.posX;
			double y = entityLiving.posY + 3;
			double z = entityLiving.posZ;
			if (cap.getAllomancyPower() != 8) {
				cap.setAllomancyPower(8);
			}
			//Fancy shmancy effects
			worldIn.spawnEntity(new EntityLightningBolt(worldIn, x, y, z, true));
			entityLiving.addPotionEffect(new PotionEffect(Potion.getPotionById(12),
					20, 0, true, false));
		}

		return super.onItemUseFinish(stack, worldIn, entityLiving);
	}

	@Override
	public void addInformation(ItemStack stack, World playerIn, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add("\u00A75" + "Replace with lerasium lore!!!");
	}

	@Override
	public EnumRarity getRarity(ItemStack stack)
	{
		return EnumRarity.EPIC;
	}

	@Override
	public boolean hasEffect(ItemStack par1ItemStack) {
		//Add enchantment glint
		return true;
	}
}
