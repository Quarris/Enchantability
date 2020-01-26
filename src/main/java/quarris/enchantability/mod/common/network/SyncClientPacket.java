package quarris.enchantability.mod.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import quarris.enchantability.api.EnchantabilityApi;

import java.util.function.Supplier;

public class SyncClientPacket {

    private CompoundNBT data;

    public SyncClientPacket(CompoundNBT data) {
        this.data = data;
    }

    public static void encode(SyncClientPacket packet, PacketBuffer buf) {
        buf.writeCompoundTag(packet.data);
    }

    public static SyncClientPacket decode(PacketBuffer buf) {
        return new SyncClientPacket(buf.readCompoundTag());
    }

    public static void handle(SyncClientPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(new Runnable() {
            @Override
            public void run() {
                Minecraft.getInstance().player.getCapability(EnchantabilityApi.playerEnchant).ifPresent(cap -> cap.deserializeEffects(packet.data));
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
