package quarris.enchantability.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ChestScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.network.NetworkEvent;
import quarris.enchantability.client.ClientEvents;
import quarris.enchantability.client.screen.EnchButton;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

public class EnderChestInteractPacket {

    public boolean open;

    public EnderChestInteractPacket(boolean open) {
        this.open = open;
    }

    public static void encode(EnderChestInteractPacket packet, PacketBuffer buf) {
        buf.writeBoolean(packet.open);
    }

    public static EnderChestInteractPacket decode(PacketBuffer buf) {
        return new EnderChestInteractPacket(buf.readBoolean());
    }

    public static void handle(EnderChestInteractPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->     {
            if (packet.open) {
                EnchButton button = new EnchButton(0, 0, false);
                Minecraft mc = Minecraft.getInstance();
                ChestScreen screen = (ChestScreen) mc.currentScreen;
                //List<Widget> buttons = ObfuscationReflectionHelper.getPrivateValue(Screen.class, screen, "buttons");
                //buttons.add(button);
                //List<IGuiEventListener> children = ObfuscationReflectionHelper.getPrivateValue(Screen.class, screen, "children");
                //children.add(button);
                try {
                    ObfuscationReflectionHelper.findMethod(Screen.class, "addButton", Widget.class).invoke(screen, button);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                ClientEvents.isEnderOpen = true;
            }
            else
                ClientEvents.isEnderOpen = false;
        });
        ctx.get().setPacketHandled(true);
    }
}
