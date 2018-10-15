package quarris.enchantability.mod.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import quarris.enchantability.mod.capability.player.CapabilityHandler;
import quarris.enchantability.mod.capability.player.enchant.IPlayerEnchHandler;

import java.io.IOException;
import java.util.UUID;

public class PacketSendCapsToClients implements IMessage, IMessageHandler<PacketSendCapsToClients, IMessage> {

    private NBTTagList dataList;
    private UUID playerUUID;

    public PacketSendCapsToClients() {
        dataList = new NBTTagList();
    }

    public PacketSendCapsToClients(EntityPlayer player) {
        dataList = player.getCapability(CapabilityHandler.PLAYER_ENCHANT_CAPABILITY, null).serializeNBT();
        playerUUID = player.getUniqueID();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer buffer = new PacketBuffer(buf);
        playerUUID = new UUID(buffer.readLong(), buffer.readLong());
        while (buffer.readableBytes() > 0) {
            try {
                dataList.appendTag(buffer.readCompoundTag());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer buffer = new PacketBuffer(buf);
        buffer.writeLong(playerUUID.getMostSignificantBits());
        buffer.writeLong(playerUUID.getLeastSignificantBits());
        for (NBTBase base : dataList) {
            NBTTagCompound tag = (NBTTagCompound) base;
            buffer.writeCompoundTag(tag);
        }
    }
    @SideOnly(Side.CLIENT)
    @Override
    public IMessage onMessage(PacketSendCapsToClients message, MessageContext ctx) {

        IThreadListener mainThread = Minecraft.getMinecraft();
        mainThread.addScheduledTask(() -> {
            EntityPlayer player;
            if (Minecraft.getMinecraft().player.getUniqueID().equals(message.playerUUID)) {
                player = Minecraft.getMinecraft().player;
            }
            else {
                player = Minecraft.getMinecraft().world.getPlayerEntityByUUID(message.playerUUID);
            }
            IPlayerEnchHandler cap = player.getCapability(CapabilityHandler.PLAYER_ENCHANT_CAPABILITY, null);
            cap.deserializeNBT(message.dataList);
        });

        return null;
    }
}
