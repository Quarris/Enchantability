package quarris.enchantability.mod.container.gui;

import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import quarris.enchantability.mod.Enchantability;
import quarris.enchantability.mod.container.ContainerEnderEnch;

@SideOnly(Side.CLIENT)
public class GuiEnch extends InventoryEffectRenderer {

    private static final ResourceLocation ECHEST_GUI_TEXTURE = new ResourceLocation(Enchantability.MODID, "textures/gui/gui_ender_chest.png");
    private final InventoryPlayer playerInv;
    private final InventoryEnderChest enderInv;

    private float oldMouseX;
    private float oldMouseY;

    public GuiEnch(EntityPlayer player) {
        super(new ContainerEnderEnch(player.inventory, player.getInventoryEnderChest(), player));
        this.playerInv = player.inventory;
        this.enderInv = player.getInventoryEnderChest();
    }

    @Override
    public void initGui() {
        super.initGui();
        this.xSize = 201;
        this.ySize = 167;
        this.guiLeft -= 25;
        this.guiTop -= 1;
        addButton(new GuiEnchButton(837259834, this, getGuiLeft() + 7, getGuiTop() + 143, 0));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
        this.oldMouseX = (float) mouseX;
        this.oldMouseY = (float) mouseY;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.drawDefaultBackground();
        this.mc.getTextureManager().bindTexture(ECHEST_GUI_TEXTURE);
        drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRenderer.drawString(this.enderInv.getDisplayName().getUnformattedText(), 8 + 25, 6, 4210752);
        this.fontRenderer.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8 + 25, this.ySize - 96 + 2 + 1, 4210752);
    }
}
