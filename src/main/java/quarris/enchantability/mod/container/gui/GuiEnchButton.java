package quarris.enchantability.mod.container.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButtonImage;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import quarris.enchantability.mod.Enchantability;
import quarris.enchantability.mod.network.PacketHandler;
import quarris.enchantability.mod.network.PacketOpenEChestInventory;
import quarris.enchantability.mod.network.PacketOpenEnchantInventory;

@SideOnly(Side.CLIENT)
public class GuiEnchButton extends GuiButtonImage {

    private static final ResourceLocation BUTTON_TEX = new ResourceLocation(Enchantability.MODID, "textures/gui/gui_button.png");
    private GuiContainer parentGui;

    private final int yDiffText;

    public GuiEnchButton(int buttonID, GuiContainer parentGui, int x, int y, int yDiffText) {
        super(buttonID, x, y, 16, 16, 0, 0, yDiffText, BUTTON_TEX);
        this.yDiffText = yDiffText;
        this.parentGui = parentGui;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            mc.getTextureManager().bindTexture(BUTTON_TEX);
            GlStateManager.disableDepth();
            if (!this.hovered) {
                GlStateManager.color(0.75f, 0.75f, 0.75f);
            }
            int i = parentGui instanceof GuiEnch ? 16 : 0;
            int j = 0;

            if (this.hovered)
            {
                j += this.yDiffText;
            }

            this.drawTexturedModalRect(this.x, this.y, i, j, this.width, this.height);
            GlStateManager.enableDepth();
        }
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if (super.mousePressed(mc, mouseX, mouseY)) {
            if (!(parentGui instanceof GuiEnch)) {
                PacketHandler.INSTANCE.sendToServer(new PacketOpenEnchantInventory());
            }
            else {
                PacketHandler.INSTANCE.sendToServer(new PacketOpenEChestInventory());
            }
            return true;
        }
        return false;
    }
}
