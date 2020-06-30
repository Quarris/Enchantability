package quarris.enchantability.mod.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;
import quarris.enchantability.mod.client.ClientEvents;
import quarris.enchantability.mod.common.network.ClickEnchButtonPacket;
import quarris.enchantability.mod.common.network.PacketHandler;
import quarris.enchantability.mod.common.util.ModRef;

public class EnchButton extends ImageButton {

    public static ResourceLocation TEXTURE = ModRef.createRes("textures/gui/gui_button.png");

    private boolean pressed;

    public EnchButton(int x, int y, boolean pressed) {
        super(x, y, 16, 16, 0, 0, 0, TEXTURE, (b) -> {
            EnchButton button = (EnchButton)b;
            ClientEvents.clickMouseX = Minecraft.getInstance().mouseHelper.getMouseX();
            ClientEvents.clickMouseY = Minecraft.getInstance().mouseHelper.getMouseY();
            PacketHandler.INSTANCE.sendToServer(new ClickEnchButtonPacket(!button.pressed));
        });
        this.pressed = pressed;
    }

    @Override
    public void func_230431_b_(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindTexture(TEXTURE);
        RenderSystem.disableDepthTest();
        int j = 0;
        if (this.pressed) {
            j = 16;
        }
        if (!this.func_230449_g_()) {
            RenderSystem.clearColor(0.75f, 0.75f, 0.75f, 1f);
        }

        func_238463_a_(matrix, this.field_230690_l_, this.field_230691_m_, 0, j, this.field_230688_j_, this.field_230689_k_, 256, 256);
        RenderSystem.enableDepthTest();
    }


}
