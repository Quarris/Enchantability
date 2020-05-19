package quarris.enchantability.mod.common.enchants.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Foods;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import quarris.enchantability.api.EnchantabilityApi;
import quarris.enchantability.api.enchants.AbstractEnchantEffect;
import quarris.enchantability.mod.common.util.ModRef;

public class GluttonyEnchantEffect extends AbstractEnchantEffect {

	public static final ResourceLocation NAME = ModRef.createRes("gluttony");

	public GluttonyEnchantEffect(PlayerEntity player, Enchantment enchantment, int level) {
		super(player, enchantment, level);
	}

	public static void consume(GluttonyEnchantEffect effect, LivingEntityUseItemEvent.Finish event) {
		if (event.getItem().isFood()) {
			Food food = event.getItem().getItem().getFood();
			EnchantabilityApi.GLUTTONY_FOODS.get(food).stream()
					.forEach(action -> action.accept(effect, event.getItem()));
		}
	}

	public static void addTooltips(GluttonyEnchantEffect effect, ItemTooltipEvent event) {
        for (Food food : EnchantabilityApi.GLUTTONY_FOODS.keySet()) {
            if (event.getItemStack().isFood() && event.getItemStack().getItem().getFood() == food) {
                event.getToolTip().add(new TranslationTextComponent("mending.tooltip"));
            }
        }
    }

	@Override
	public ResourceLocation getName() {
		return NAME;
	}

    public static class GluttonyFoods {

        public static void initMendingFoods() {
            EnchantabilityApi.addToMendingList(Foods.RABBIT_STEW, GluttonyFoods::rabbitStew);
            EnchantabilityApi.addToMendingList(Foods.COOKIE, GluttonyFoods::cookie);
        }

        private static void rabbitStew(GluttonyEnchantEffect effect, ItemStack item) {
            effect.player.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 80, 1));
        }

        private static void cookie(GluttonyEnchantEffect effect, ItemStack item) {
            effect.player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 20 * 20, 0));
            effect.player.addPotionEffect(new EffectInstance(Effects.SPEED, 20 * 15, 1));
        }

    }
}
