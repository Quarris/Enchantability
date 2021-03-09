package quarris.enchantability.mod.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ChestScreen;
import net.minecraft.client.util.InputMappings;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import quarris.enchantability.mod.ModConfig;
import quarris.enchantability.mod.client.screen.EnchButton;
import quarris.enchantability.mod.common.enchants.EnchantEffectRegistry;
import quarris.enchantability.mod.common.util.ModRef;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
			event.addWidget(new EnchButton(screen.getGuiLeft() + ModConfig.get().buttonXOffset.get(), screen.getGuiTop() + ModConfig.get().buttonYOffset.get(), false));
		}
	}

    @SubscribeEvent
    public static void addEnchantTooltips(ItemTooltipEvent event) {
        if (!(event.getItemStack().getItem() instanceof EnchantedBookItem))
            return;

        ItemStack stack = event.getItemStack();
        PlayerEntity player = event.getPlayer();
        List<ITextComponent> tooltips = new ArrayList<>();
        Set<Enchantment> enchantments = EnchantmentHelper.getEnchantments(stack).keySet();

        for (Enchantment enchantment : enchantments) {
            tooltips.addAll(EnchantEffectRegistry.BY_ENCHANTMENT.get(enchantment).stream()
                    .map(EnchantEffectRegistry.DESCRIPTIONS::get)
                    .collect(Collectors.toList()));
        }

        if (tooltips.isEmpty()) {
            event.getToolTip().add(new TranslationTextComponent("enchant.desc.empty").mergeStyle(TextFormatting.GRAY));
            return;
        }

        if (player == null) {
            event.getToolTip().add(new StringTextComponent("Enchantability:"));
            for (ITextComponent tooltip : tooltips) {
                IFormattableTextComponent text = new StringTextComponent(" - ");
                text.append(tooltip);
                event.getToolTip().add(text);
            }
        } else {
            if (InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_KEY_LEFT_SHIFT)) {
                event.getToolTip().add(new StringTextComponent("Enchantability:").mergeStyle(TextFormatting.GOLD));
                for (ITextComponent tooltip : tooltips) {
                    IFormattableTextComponent text = new StringTextComponent(" - ").mergeStyle(TextFormatting.BLUE);
                    text.append(tooltip);
                    event.getToolTip().add(text);
                }
            } else {
                event.getToolTip().add(new TranslationTextComponent("enchant.desc.short").mergeStyle(TextFormatting.GRAY));
            }
        }
    }
}
