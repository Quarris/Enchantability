package quarris.enchantability.client.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import quarris.enchantability.common.container.EnchContainer;
import quarris.enchantability.common.util.ModRef;

public class EnchScreen extends DisplayEffectsScreen<EnchContainer> {

    private static final ResourceLocation TEXTURE = ModRef.createRes("textures/gui/gui_ender_chest.png");

    public EnchScreen(EnchContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected void init() {
        super.init();
        this.xSize = 201;
        this.ySize = 167;
        this.guiLeft -= 25;
        this.guiTop -= 1;
        this.addButton(new EnchButton(this.getGuiLeft() + 7, this.getGuiTop() + 147, true));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindTexture(TEXTURE);
        this.renderBackground();

        this.blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }
}
