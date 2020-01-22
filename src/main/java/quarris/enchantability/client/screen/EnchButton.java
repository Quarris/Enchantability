package quarris.enchantability.client.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;
import quarris.enchantability.common.network.ClickEnchButtonPacket;
import quarris.enchantability.common.network.PacketHandler;
import quarris.enchantability.common.util.ModRef;

public class EnchButton extends ImageButton {

    public static ResourceLocation TEXTURE = ModRef.createRes("textures/gui/gui_button.png");

    private boolean pressed;

    public EnchButton(int x, int y, boolean pressed) {
        super(x, y, 16, 16, 0, 0, 0, TEXTURE, (button) -> {
            // TODO Send packet to open the Enchant Screen
            ((EnchButton)button).pressed = !((EnchButton)button).pressed;
            PacketHandler.INSTANCE.sendToServer(new ClickEnchButtonPacket(true));
        });
        this.pressed = pressed;
    }

    @Override
    public void renderButton(int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindTexture(TEXTURE);
        GlStateManager.disableDepthTest();
        int j = 0;
        if (this.pressed) {
            j = 16;
        }
        if (!this.isHovered()) {
            GlStateManager.color3f(0.75f, 0.75f, 0.75f);
        }

        blit(this.x, this.y, 0, j, this.width, this.height, 256, 256);
        GlStateManager.enableDepthTest();
    }


}
