package quarris.enchantability.mod.client.screen;

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
    public void renderButton(int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindTexture(TEXTURE);
        RenderSystem.disableDepthTest();
        int j = 0;
        if (this.pressed) {
            j = 16;
        }
        if (!this.isHovered()) {
            RenderSystem.color3f(0.75f, 0.75f, 0.75f);
        }

        blit(this.x, this.y, 0, j, this.width, this.height, 256, 256);
        RenderSystem.enableDepthTest();
    }


}
