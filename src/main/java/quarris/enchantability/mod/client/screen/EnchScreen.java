package quarris.enchantability.mod.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
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
    protected void func_231160_c_() {
        super.func_231160_c_();
        this.xSize = 201;
        this.ySize = 167;
        this.guiLeft -= 25;
        this.guiTop -= 1;
        this.func_230480_a_(new EnchButton(this.getGuiLeft() + 7, this.getGuiTop() + 143, true));
        GLFW.glfwSetCursorPos(Minecraft.getInstance().getMainWindow().getHandle(), ClientEvents.clickMouseX, ClientEvents.clickMouseY);
    }

    @Override
    public void func_230430_a_(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        this.func_230446_a_(matrix);
        super.func_230430_a_(matrix, mouseX, mouseY, partialTicks);
        this.func_230459_a_(matrix, mouseX, mouseY);
    }

    @Override
    protected void func_230450_a_(MatrixStack matrix, float partialTicks, int mouseX, int mouseY) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindTexture(this.container.enchant.isExtended() ? EXTENDED_TEXTURE : TEXTURE);

        this.func_238474_b_(matrix, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void func_230451_b_(MatrixStack matrix, int mouseX, int mouseY) {
        this.field_230712_o_.func_243248_b(matrix, this.field_230704_d_, 33.0F, 6.0F, 4210752);
        this.field_230712_o_.func_243248_b(matrix, this.playerInventory.getDisplayName(), 33.0F, (float)(this.ySize - 96 + 3), 4210752);
    }
}
