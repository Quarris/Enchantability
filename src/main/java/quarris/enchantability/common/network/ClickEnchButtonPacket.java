package quarris.enchantability.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import quarris.enchantability.common.container.EnchContainer;

import java.util.function.Supplier;

public class ClickEnchButtonPacket {

    private boolean openEnchInv;

    public ClickEnchButtonPacket(boolean openEnchInv) {
        this.openEnchInv = openEnchInv;
    }

    public static void encode(ClickEnchButtonPacket packet, PacketBuffer buf) {
        buf.writeBoolean(packet.openEnchInv);
    }

    public static ClickEnchButtonPacket decode(PacketBuffer buffer) {
        return new ClickEnchButtonPacket(buffer.readBoolean());
    }

    public static void handle(ClickEnchButtonPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (packet.openEnchInv) {
                ctx.get().getSender().openContainer(new SimpleNamedContainerProvider(
                        (id, inv, player) -> new EnchContainer(id, player),
                        new StringTextComponent("ench")
                ));
            } else {

            }
        });

        ctx.get().setPacketHandled(true);
    }

}
