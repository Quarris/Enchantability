package quarris.enchantability.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ChestScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.network.NetworkEvent;
import quarris.enchantability.client.ClientEvents;
import quarris.enchantability.common.util.ModRef;

import java.util.List;
import java.util.function.Supplier;

public class OpenCloseEnderChestPacket {

    public boolean open;

    public OpenCloseEnderChestPacket(boolean open) {
        this.open = open;
    }

    public static void encode(OpenCloseEnderChestPacket msg, PacketBuffer buf) {
        buf.writeBoolean(msg.open);
    }

    public static OpenCloseEnderChestPacket decode(PacketBuffer buf) {
        return new OpenCloseEnderChestPacket(buf.readBoolean());
    }

    public static void handle(OpenCloseEnderChestPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (msg.open) {
                Minecraft mc = Minecraft.getInstance();
                ChestScreen screen = (ChestScreen) mc.currentScreen;
                List<Widget> buttons = ObfuscationReflectionHelper.getPrivateValue(Screen.class, screen, "buttons");
                buttons.add(ClientEvents.ENCH_BUTTON);
                List<IGuiEventListener> children = ObfuscationReflectionHelper.getPrivateValue(Screen.class, screen, "children");
                children.add(ClientEvents.ENCH_BUTTON);
                ClientEvents.isEnderOpen = true;
            }
            else
                ClientEvents.isEnderOpen = false;
        });
        ctx.get().setPacketHandled(true);
    }
}
