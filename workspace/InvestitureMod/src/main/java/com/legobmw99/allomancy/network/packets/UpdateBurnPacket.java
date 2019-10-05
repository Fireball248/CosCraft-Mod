package com.legobmw99.allomancy.network.packets;

import com.legobmw99.allomancy.util.AllomancyCapability;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class UpdateBurnPacket implements IMessage {

	public UpdateBurnPacket() {
	}

	private int mat;
	private boolean value;

	/**
	 * Send request to the server to change the burning state of a metal
	 * 
	 * @param mat
	 *            the index of the metal
	 * @param value
	 *            whether or not it is burning
	 */
	public UpdateBurnPacket(int mat, boolean value) {
		this.mat = mat;
		this.value = value; // Convert bool to int
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		mat = ByteBufUtils.readVarInt(buf, 5);
		value = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeVarInt(buf, mat, 5);
		buf.writeBoolean(value);
	}

	public static class Handler implements IMessageHandler<UpdateBurnPacket, IMessage> {

		@Override
		public IMessage onMessage(final UpdateBurnPacket message, final MessageContext ctx) {
			IThreadListener mainThread = (WorldServer) ctx.getServerHandler().player.world; 
			mainThread.addScheduledTask(new Runnable() {
				@Override
				public void run() {

					EntityPlayerMP player = ctx.getServerHandler().player;
					AllomancyCapability cap = AllomancyCapability.forPlayer(player);

					if (cap.getMetalAmounts(message.mat) != 0) {
						cap.setMetalBurning(message.mat, message.value);
					} else {
						cap.setMetalBurning(message.mat, false);
					}
				}
			});
			return null;
		}
	}
}
