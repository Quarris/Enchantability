package quarris.enchantability.mod.client.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.glfw.GLFW;
import quarris.enchantability.mod.client.ClientEvents;
import quarris.enchantability.mod.common.container.EnchContainer;
import quarris.enchantability.mod.common.util.ModRef;

public class EnchScreen extends DisplayEffectsScreen<EnchContainer> {

    private static final ResourceLocation TEXTURE = ModRef.createRes("textures/gui/gui_ender_chest.png");
    private static final ResourceLocation EXTENDED_TEXTURE = ModRef.createRes("textures/gui/gui_ender_chest_large.png");

    public EnchScreen(EnchContainer container, PlayerInventory inv, ITextComponent titleIn) {
        super(container, inv, titleIn);
    }

    @Override
    protected void init() {
        super.init();
        this.xSize = 201;
        this.ySize = 167;
        this.guiLeft -= 25;
        this.guiTop -= 1;
        this.addButton(new EnchButton(this.getGuiLeft() + 7, this.getGuiTop() + 143, true));
        GLFW.glfwSetCursorPos(Minecraft.getInstance().getMainWindow().getHandle(), ClientEvents.clickMouseX, ClientEvents.clickMouseY);
    }

    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        super.render(p_render_1_, p_render_2_, p_render_3_);
        this.renderHoveredToolTip(p_render_1_, p_render_2_);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindTexture(this.container.enchant.isExtended() ? EXTENDED_TEXTURE : TEXTURE);

        this.blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.font.drawString(this.title.getFormattedText(), 33.0F, 6.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 33.0F, (float)(this.ySize - 96 + 3), 4210752);
    }
}
