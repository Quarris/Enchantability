package quarris.enchantability.mod.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ChestScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.network.NetworkEvent;
import quarris.enchantability.mod.ModConfig;
import quarris.enchantability.mod.client.ClientEvents;
import quarris.enchantability.mod.client.screen.EnchButton;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Supplier;

public class EnderChestInteractPacket {

    private static Method ADD_BUTTON_METHOD;

    private boolean open;

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
        ctx.get().enqueueWork(new Runnable() {

            @Override
            public void run() {
                if (packet.open) {
                    ChestScreen screen = (ChestScreen) Minecraft.getInstance().currentScreen;
                    if (screen == null)
                        return;
                    EnchButton button = new EnchButton(screen.getGuiLeft() + ModConfig.get().buttonXOffset.get(), screen.getGuiTop() + ModConfig.get().buttonYOffset.get(), false);
                    try {
                        if (ADD_BUTTON_METHOD == null)
                            ADD_BUTTON_METHOD = ObfuscationReflectionHelper.findMethod(Screen.class, "func_230480_a_", Widget.class);
                        ADD_BUTTON_METHOD.invoke(screen, button);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    ClientEvents.isEnderOpen = true;
                } else {
                    ClientEvents.isEnderOpen = false;
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
