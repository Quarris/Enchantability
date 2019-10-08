package quarris.enchantability.client;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import quarris.enchantability.common.util.ModRef;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("unused")
public class ClientEvents {

    private BlockState lastClickedState;
    private static final ResourceLocation ENCHANT_BUTTON = ModRef.createRes("textures/gui/gui_button.png");

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickBlock e) {
        this.lastClickedState = e.getEntity().world.getBlockState(e.getPos());
    }

    @SubscribeEvent
    public void addButtonToEnderChest(GuiScreenEvent.InitGuiEvent e) {
        if (this.lastClickedState != null && this.lastClickedState.getBlock() == Blocks.ENDER_CHEST) {
            e.addWidget(new ImageButton(0, 0, 18, 18, 0, 0, 9, ENCHANT_BUTTON, (b) -> System.out.println("Pressed")));
        }
    }
}
