package com.legobmw99.allomancy.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.legobmw99.allomancy.init.ModBlocks;
import com.legobmw99.allomancy.init.ModItems;
import org.lwjgl.opengl.GL11;

import com.legobmw99.allomancy.Allomancy;
import com.legobmw99.allomancy.entities.EntityGoldNugget;
import com.legobmw99.allomancy.entities.EntityIronNugget;
import com.legobmw99.allomancy.network.packets.AllomancyCapabilityPacket;
import com.legobmw99.allomancy.network.packets.UpdateBurnPacket;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Contains all static, common methods in one place
 * 
 */

public class AllomancyUtils {
	private static final ArrayList<String> metallist = new ArrayList<String>();
	private static File whitelist;

	public static final int PUSH = 1;
	public static final int PULL = -1;

	public static void init() {
		generateWhitelist();
		populateMetalList();
	}

	/*
	 * This code was based off the similar methods found in CoFHCore
	 */
	/**
	 * Generate a text file containing all the blocks and items considered 'metal' (if it doesn't already exist)
	 */
	private static void generateWhitelist() {
		BufferedWriter output = null;
		whitelist = new File(Allomancy.configDirectory, "allomancy-whitelist.txt");
		if (!whitelist.exists()) {

			ArrayList<String> defaultList = new ArrayList<>();
			defaultList.add(Items.IRON_AXE.getRegistryName().toString());
			defaultList.add(Items.GOLDEN_AXE.getRegistryName().toString());
			defaultList.add(Items.CHAINMAIL_BOOTS.getRegistryName().toString());
			defaultList.add(Items.GOLDEN_BOOTS.getRegistryName().toString());
			defaultList.add(Items.IRON_BOOTS.getRegistryName().toString());
			defaultList.add(Items.BUCKET.getRegistryName().toString());
			defaultList.add(Items.LAVA_BUCKET.getRegistryName().toString());
			defaultList.add(Items.MILK_BUCKET.getRegistryName().toString());
			defaultList.add(Items.WATER_BUCKET.getRegistryName().toString());
			defaultList.add(Items.CAULDRON.getRegistryName().toString());
			defaultList.add(Items.COMPASS.getRegistryName().toString());
			defaultList.add(Items.FLINT_AND_STEEL.getRegistryName().toString());
			defaultList.add(Items.GOLD_NUGGET.getRegistryName().toString());
			defaultList.add(Items.IRON_NUGGET.getRegistryName().toString());
			defaultList.add(Items.CHAINMAIL_HELMET.getRegistryName().toString());
			defaultList.add(Items.GOLDEN_HELMET.getRegistryName().toString());
			defaultList.add(Items.IRON_HELMET.getRegistryName().toString());
			defaultList.add(Items.GOLDEN_HOE.getRegistryName().toString());
			defaultList.add(Items.IRON_HOE.getRegistryName().toString());
			defaultList.add(Items.GOLDEN_HORSE_ARMOR.getRegistryName().toString());
			defaultList.add(Items.IRON_HORSE_ARMOR.getRegistryName().toString());
			defaultList.add(Items.CHAINMAIL_LEGGINGS.getRegistryName().toString());
			defaultList.add(Items.GOLDEN_LEGGINGS.getRegistryName().toString());
			defaultList.add(Items.IRON_LEGGINGS.getRegistryName().toString());
			defaultList.add(Items.MINECART.getRegistryName().toString());
			defaultList.add(Items.CHEST_MINECART.getRegistryName().toString());
			defaultList.add(Items.HOPPER_MINECART.getRegistryName().toString());
			defaultList.add(Items.FURNACE_MINECART.getRegistryName().toString());
			defaultList.add(Items.TNT_MINECART.getRegistryName().toString());
			defaultList.add(Items.IRON_PICKAXE.getRegistryName().toString());
			defaultList.add(Items.GOLDEN_PICKAXE.getRegistryName().toString());
			defaultList.add(Items.IRON_CHESTPLATE.getRegistryName().toString());
			defaultList.add(Items.CHAINMAIL_CHESTPLATE.getRegistryName().toString());
			defaultList.add(Items.GOLDEN_CHESTPLATE.getRegistryName().toString());
			defaultList.add(Items.CLOCK.getRegistryName().toString());
			defaultList.add(Items.GOLDEN_SHOVEL.getRegistryName().toString());
			defaultList.add(Items.IRON_SHOVEL.getRegistryName().toString());
			defaultList.add(Items.SHEARS.getRegistryName().toString());
			defaultList.add(Items.GOLDEN_APPLE.getRegistryName().toString());
			defaultList.add(Items.GOLDEN_APPLE.getRegistryName().toString());
			defaultList.add(Items.GOLDEN_CARROT.getRegistryName().toString());
			defaultList.add(Items.IRON_SWORD.getRegistryName().toString());
			defaultList.add(ModItems.GRINDER.getRegistryName().toString());
			defaultList.add(ModItems.COIN_BAG.getRegistryName().toString());
			defaultList.add(Blocks.ANVIL.getRegistryName().toString());
			defaultList.add(Blocks.IRON_TRAPDOOR.getRegistryName().toString());
			defaultList.add(Blocks.IRON_DOOR.getRegistryName().toString());
			defaultList.add(Blocks.CAULDRON.getRegistryName().toString());
			defaultList.add(Blocks.IRON_BARS.getRegistryName().toString());
			defaultList.add(Blocks.HOPPER.getRegistryName().toString());
			defaultList.add(Blocks.PISTON_HEAD.getRegistryName().toString());
			defaultList.add(Blocks.PISTON_EXTENSION.getRegistryName().toString());
			defaultList.add(Blocks.STICKY_PISTON.getRegistryName().toString());
			defaultList.add(Blocks.PISTON.getRegistryName().toString());
			defaultList.add(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE.getRegistryName().toString());
			defaultList.add(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE.getRegistryName().toString());
			defaultList.add(Blocks.RAIL.getRegistryName().toString());
			defaultList.add(Blocks.ACTIVATOR_RAIL.getRegistryName().toString());
			defaultList.add(Blocks.DETECTOR_RAIL.getRegistryName().toString());
			defaultList.add(Blocks.GOLDEN_RAIL.getRegistryName().toString());
			defaultList.add(ModItems.VIAL.getRegistryName().toString());
			defaultList.add(ModBlocks.IRON_LEVER.getRegistryName().toString());

			for (int i = 0; i < Allomancy.flakeMetals.length; i++) {
				defaultList.add(new Item().getByNameOrId("allomancy:" + "flake_" + Allomancy.flakeMetals[i]).getRegistryName().toString());
			}

			String[] ores = OreDictionary.getOreNames();
			for (String s : ores) {
				if (s.contains("Copper") || s.contains("Tin") || s.contains("Gold") || s.contains("Iron")
						|| s.contains("Steel") || s.contains("Lead") || s.contains("Silver") || s.contains("Brass")
						|| s.contains("Bronze") || s.contains("Aluminum") || s.contains("Zinc")) {
					for (ItemStack i : OreDictionary.getOres(s)) {
						if (i.getItem() != null) {
							String name = i.getItem().getRegistryName().toString();
							if(!defaultList.contains(name)){
								defaultList.add(name);
							}
						}
					}
				}
			}

			Collections.sort(defaultList);

			try {
				whitelist.createNewFile();
				output = new BufferedWriter(new FileWriter(whitelist));
				output.write("# Add the registry names of blocks or items to the list below for them to be treated as metals \n");

				for (String item : defaultList) {
					output.write(item + "\n");
				}
				output.close();
				defaultList.clear();
			} catch (Throwable t) {
				t.printStackTrace();
			}

		}
	}

	/**
	 * Reads in the whitelist of metal blocks and items
	 */
	private static void populateMetalList() {
		try {
			if (!whitelist.exists()) {
				return;
			}

			Scanner scanner = new Scanner(whitelist);
			String[] line;
			String[] tokens;
			while (scanner.hasNext()) {
				line = scanner.next().split("\\n");
				tokens = line[0].split("#");
				if (tokens.length == 1) {
					metallist.add(line[0]);
				}
			}
			scanner.close();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	/**
	 * Draws a line from the player (denoted pX,Y,Z) to the given set of
	 * coordinates (oX,Y,Z) in a certain color (r,g,b)
	 * 
	 * @param width
	 *            the width of the line
	 */
	@SideOnly(Side.CLIENT)
	public static void drawMetalLine(double pX, double pY, double pZ, double oX, double oY, double oZ, float width,
			float r, float g, float b) {
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
		GL11.glTranslated(-pX, -pY, -pZ);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);

		GL11.glLineWidth(width);
		GL11.glColor3f(r, g, b);

		GL11.glBegin(GL11.GL_LINE_STRIP);

		GL11.glVertex3d(pX, pY + 1.2, pZ);
		GL11.glVertex3d(oX, oY, oZ);

		GL11.glEnd();
		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}

	/**
	 * Copied mostly from vanilla, this gets what the mouse is over
	 * 
	 * @param dist
	 *            distance
	 * @return the result of the raytrace
	 */
	@SideOnly(Side.CLIENT)
	public static RayTraceResult getMouseOverExtended(float dist) {
		Minecraft mc = FMLClientHandler.instance().getClient();
		Entity theRenderViewEntity = mc.getRenderViewEntity();
		AxisAlignedBB theViewBoundingBox = new AxisAlignedBB(theRenderViewEntity.posX - 0.5D,
				theRenderViewEntity.posY - 0.0D, theRenderViewEntity.posZ - 0.5D, theRenderViewEntity.posX + 0.5D,
				theRenderViewEntity.posY + 1.5D, theRenderViewEntity.posZ + 0.5D);
		RayTraceResult returnMOP = null;
		if (mc.world != null) {
			double var2 = dist;
			returnMOP = theRenderViewEntity.rayTrace(var2, 0);
			double calcdist = var2;
			Vec3d pos = theRenderViewEntity.getPositionEyes(0);
			var2 = calcdist;
			if (returnMOP != null) {
				calcdist = returnMOP.hitVec.distanceTo(pos);
			}
			Vec3d lookvec = theRenderViewEntity.getLook(0);
			Vec3d var8 = pos.add(lookvec.x * var2, lookvec.y * var2, lookvec.z * var2);
			Entity pointedEntity = null;
			float var9 = 1.0F;
			@SuppressWarnings("unchecked")
			List<Entity> list = mc.world.getEntitiesWithinAABBExcludingEntity(theRenderViewEntity,
					addCoord(theViewBoundingBox, lookvec.x * dist, lookvec.y * dist, lookvec.z * dist).expand(var9,
							var9, var9));
			double d = calcdist;
			for (Entity entity : list) {
				float bordersize = entity.getCollisionBorderSize();
				AxisAlignedBB aabb = new AxisAlignedBB(entity.posX - entity.width / 2, entity.posY,
						entity.posZ - entity.width / 2, entity.posX + entity.width / 2, entity.posY + entity.height,
						entity.posZ + entity.width / 2);
				aabb.expand(bordersize, bordersize, bordersize);
				RayTraceResult mop0 = aabb.calculateIntercept(pos, var8);
				if (aabb.contains(pos)) {
					if (0.0D < d || d == 0.0D) {
						pointedEntity = entity;
						d = 0.0D;
					}
				} else if (mop0 != null) {
					double d1 = pos.distanceTo(mop0.hitVec);
					if (d1 < d || d == 0.0D) {
						pointedEntity = entity;
						d = d1;
					}
				}
			}
			if (pointedEntity != null && (d < calcdist || returnMOP == null)) {
				returnMOP = new RayTraceResult(pointedEntity);
			}
		}
		return returnMOP;
	}

	/**
	 * Replacement for the old addCoord in AxisAlignedBB.class, necessary for
	 * getMouseOverExtended
	 * 
	 * @param a
	 *            the original box
	 * @param x
	 * @param y
	 * @param z
	 * @return Adds a coordinate to the bounding box, extending it if the point
	 *         lies outside the current ranges.
	 */
	public static AxisAlignedBB addCoord(AxisAlignedBB a, double x, double y, double z) {
		double d0 = a.minX;
		double d1 = a.minY;
		double d2 = a.minZ;
		double d3 = a.maxX;
		double d4 = a.maxY;
		double d5 = a.maxZ;

		if (x < 0.0D) {
			d0 += x;
		} else if (x > 0.0D) {
			d3 += x;
		}

		if (y < 0.0D) {
			d1 += y;
		} else if (y > 0.0D) {
			d4 += y;
		}

		if (z < 0.0D) {
			d2 += z;
		} else if (z > 0.0D) {
			d5 += z;
		}

		return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
	}

	/**
	 * Determines if a block is metal or not
	 * 
	 * @param block
	 *            to be checked
	 * @return Whether or not the item is metal
	 */
	public static boolean isBlockMetal(Block block) {
		return metallist.contains(block.getRegistryName().toString());
	}

	/**
	 * Determines if an item is metal or not
	 * 
	 * @param item
	 *            to be checked
	 * @return Whether or not the item is metal
	 */
	public static boolean isItemMetal(ItemStack item) {
		return (item != null) && metallist.contains(item.getItem().getRegistryName().toString());
	}

	/**
	 * Determines if an entity is metal or not
	 * 
	 * @param entity
	 *            to be checked
	 * @return Whether or not the entity is metallic
	 */
	public static boolean isEntityMetal(Entity entity) {
		if (entity == null) {
			return false;
		}
		
		if (entity instanceof EntityItem) {
			return isItemMetal(((EntityItem) entity).getItem());
		} 
		if (entity instanceof EntityItemFrame){ 
			return isItemMetal(((EntityItemFrame) entity).getDisplayedItem());
		} 
		if(entity instanceof EntityIronNugget || entity instanceof EntityGoldNugget) {
			return true;
		} 
		if(entity instanceof EntityMinecart) {
			return true;
		} 
		if (entity instanceof EntityLiving) {
			EntityLiving ent = (EntityLiving) entity;
			if(ent instanceof EntityIronGolem){
				return true;
			}
			if(isItemMetal(ent.getHeldItem(EnumHand.MAIN_HAND)) || isItemMetal(ent.getHeldItem(EnumHand.OFF_HAND))){
				return true;
			}
			for(ItemStack i: ent.getArmorInventoryList()){
				if(isItemMetal(i)){
					return true;
				}
			}
		} 
		
		return false;
	}

	/**
	 * Move an entity either toward or away from an anchor point
	 * 
	 * @param directionScalar
	 *            the direction and (possibly) scalar multiple of the magnitude
	 * @param toMove
	 *            the entity to move
	 * @param vec
	 *            the point being moved toward or away from
	 */
	public static void move(double directionScalar, Entity toMove, BlockPos vec) {

		double motionX, motionY, motionZ, magnitude;
		if(toMove.isRiding()){
			toMove = toMove.getRidingEntity();
		}
		// Calculate the length of the vector between the entity and anchor
		magnitude = Math.sqrt(Math.pow((toMove.posX - (double) (vec.getX() + .5)), 2)
				+ Math.pow((toMove.posY - (double) (vec.getY() + .5)), 2)
				+ Math.pow((toMove.posZ - (double) (vec.getZ() + .5)), 2));
		// Get a unit(-ish) vector in the direction of motion
		motionX = ((toMove.posX - (double) (vec.getX() + .5)) * directionScalar * (1.1) / magnitude);
		motionY = ((toMove.posY - (double) (vec.getY() + .5)) * directionScalar * (1.1) / magnitude);
		motionZ = ((toMove.posZ - (double) (vec.getZ() + .5)) * directionScalar * (1.1) / magnitude);
		// Move along that vector, additively increasing motion until you max
		// out at the above values
		toMove.motionX = Math.abs(toMove.motionX + motionX) > 0.01
				? MathHelper.clamp(toMove.motionX + motionX, -Math.abs(motionX), motionX) : 0;
		toMove.motionY = Math.abs(toMove.motionY + motionY) > 0.01
				? MathHelper.clamp(toMove.motionY + motionY, -Math.abs(motionY), motionY) : 0;
		toMove.motionZ = Math.abs(toMove.motionZ + motionZ) > 0.01
				? MathHelper.clamp(toMove.motionZ + motionZ, -Math.abs(motionZ), motionZ) : 0;

		toMove.velocityChanged = true;

		// Only save players from fall damage
		if (toMove instanceof EntityPlayerMP) {
			toMove.fallDistance = 0;
		}

	}

	/**
	 * Used to toggle a metal's burn state and play a sound effect
	 * 
	 * @param metal
	 *            the index of the metal to toggle
	 * @param capability
	 *            the capability being handled
	 */
	public static void toggleMetalBurn(int metal, AllomancyCapability capability) {
		Allomancy.network.sendToServer(new UpdateBurnPacket(metal, !capability.getMetalBurning(metal)));

		if (capability.getMetalAmounts(metal) > 0) {
			capability.setMetalBurning(metal, !capability.getMetalBurning(metal));
		}
		// play a sound effect
		if (capability.getMetalBurning(metal)) {
			Minecraft.getMinecraft().player.playSound(new SoundEvent(new ResourceLocation("item.flintandsteel.use")), 1,
					5);
		} else {
			Minecraft.getMinecraft().player.playSound(new SoundEvent(new ResourceLocation("block.fire.extinguish")), 1,
					4);
		}
	}

	public static void updateMetalBurnTime(AllomancyCapability cap1, EntityPlayerMP player) {
		for (int i = 0; i < 8; i++) {
			if (cap1.getMetalBurning(i)) {
				if (cap1.getAllomancyPower() != i && cap1.getAllomancyPower() != 8) {
					// put out any metals that the player shouldn't be able to burn
					cap1.setMetalBurning(i, false);
					Allomancy.network.sendTo(new AllomancyCapabilityPacket(cap1, player.getEntityId()), player);
				} else {
					cap1.setBurnTime(i, cap1.getBurnTime(i) - 1);
					if (cap1.getBurnTime(i) == 0) {
						cap1.setBurnTime(i, cap1.MAX_BURN_TIME[i]);
						cap1.setMetalAmounts(i, cap1.getMetalAmounts(i) - 1);
						Allomancy.network.sendTo(new AllomancyCapabilityPacket(cap1, player.getEntityId()), player);
						if (cap1.getMetalAmounts(i) == 0) {
							cap1.setMetalBurning(i, false);
							Allomancy.network.sendTo(new AllomancyCapabilityPacket(cap1, player.getEntityId()), player);
						}
					}
				}

			}
		}
	}
}
