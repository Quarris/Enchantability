package quarris.enchantability.client;

import net.minecraft.client.gui.screen.inventory.ChestScreen;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import quarris.enchantability.common.network.OpenCloseEnderChestPacket;
import quarris.enchantability.common.util.ModRef;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ModRef.MOD_ID)
@SuppressWarnings("unused")
public class ClientEvents {

	public static boolean isEnderOpen;
	public static final ResourceLocation ENCHANT_BUTTON = ModRef.createRes("textures/gui/gui_button.png");
	public static final ImageButton ENCH_BUTTON = new ImageButton(0, 0, 18, 18, 0, 0, 9, ENCHANT_BUTTON, (b) -> System.out.println("Pressed"));

	@SubscribeEvent
	public static void addEnchantButton(GuiScreenEvent.InitGuiEvent e) {
		if (isEnderOpen && e.getGui() instanceof ChestScreen) {
			e.addWidget(ENCH_BUTTON);
		}
	}

}
