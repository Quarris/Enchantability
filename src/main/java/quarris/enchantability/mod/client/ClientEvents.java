package quarris.enchantability.mod.client;

import net.minecraft.client.gui.screen.inventory.ChestScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import quarris.enchantability.mod.client.screen.EnchButton;
import quarris.enchantability.mod.common.util.ModRef;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ModRef.ID)
@SuppressWarnings("unused")
public class ClientEvents {

	public static boolean isEnderOpen;
    public static double clickMouseX;
    public static double clickMouseY;

	@SubscribeEvent
	public static void addEnchantButton(GuiScreenEvent.InitGuiEvent.Post event) {
		if (isEnderOpen && event.getGui() instanceof ChestScreen) {
		    ChestScreen screen = (ChestScreen) event.getGui();
			event.addWidget(new EnchButton(screen.getGuiLeft() - 18, screen.getGuiTop() + 143, false));
		}
	}
}
