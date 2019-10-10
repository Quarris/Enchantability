package quarris.enchantability.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ChestScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.network.NetworkEvent;
import quarris.enchantability.common.util.ModRef;

import java.util.List;
import java.util.function.Supplier;

public class OpenEnderChestPacket {

    private static final ResourceLocation ENCHANT_BUTTON = ModRef.createRes("textures/gui/gui_button.png");

    public OpenEnderChestPacket() {

    }

    public static void encode(OpenEnderChestPacket msg, PacketBuffer buf) {
    }

    public static OpenEnderChestPacket decode(PacketBuffer buf) {
        return new OpenEnderChestPacket();
    }

    public static void handle(OpenEnderChestPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            ChestScreen screen = (ChestScreen) mc.currentScreen;
            List<Widget> buttons = ObfuscationReflectionHelper.getPrivateValue(Screen.class, screen, "buttons");
            buttons.add(new ImageButton(0, 0, 18, 18, 0, 0, 9, ENCHANT_BUTTON, (b) -> System.out.println("Pressed")));
        });
        ctx.get().setPacketHandled(true);
    }
}
