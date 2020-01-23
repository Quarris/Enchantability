package quarris.enchantability.mod.common.network;

import net.minecraft.block.EnderChestBlock;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import quarris.enchantability.mod.common.container.EnchContainer;

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
            ServerPlayerEntity splayer = ctx.get().getSender();
            if (splayer == null)
                return;

            if (packet.openEnchInv) {
                splayer.openContainer(new SimpleNamedContainerProvider(
                        (id, inv, player) -> new EnchContainer(id, player),
                        EnderChestBlock.field_220115_d
                ));
            } else {
                EnderChestInventory einv = ctx.get().getSender().getInventoryEnderChest();
                splayer.openContainer(new SimpleNamedContainerProvider((id, inv, player) -> ChestContainer.createGeneric9X3(id, inv, einv), EnderChestBlock.field_220115_d));
            }
        });

        ctx.get().setPacketHandled(true);
    }

}
