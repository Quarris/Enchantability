package quarris.enchantability.mod.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.glfw.GLFW;
import quarris.enchantability.mod.ModConfig;
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
        this.addButton(new EnchButton(this.getGuiLeft() + ModConfig.get().buttonXOffset.get() + 25, this.getGuiTop() + ModConfig.get().buttonYOffset.get(), true));
        GLFW.glfwSetCursorPos(Minecraft.getInstance().getMainWindow().getHandle(), ClientEvents.clickMouseX, ClientEvents.clickMouseY);
    }

    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrix);
        super.render(matrix, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrix, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrix, float partialTicks, int mouseX, int mouseY) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindTexture(this.container.enchant.isExtended() ? EXTENDED_TEXTURE : TEXTURE);

        this.blit(matrix, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrix, int mouseX, int mouseY) {
        // Draw Container Names
        this.font.func_243248_b(matrix, this.title, 33.0F, 6.0F, 4210752);
        this.font.func_243248_b(matrix, this.playerInventory.getDisplayName(), 33.0F, (float)(this.ySize - 96 + 3), 4210752);
    }
}
