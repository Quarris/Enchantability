package quarris.enchantability.mod.common.enchants.impl;

import net.minecraft.item.Foods;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import quarris.enchantability.api.EnchantabilityApi;

public class GluttonyFoods {

	public static void initMendingFoods() {
		EnchantabilityApi.IInternals internals = EnchantabilityApi.getInstance();
        internals.addToMendingList(Foods.RABBIT_STEW, GluttonyFoods::rabbitStew);
        internals.addToMendingList(Foods.COOKIE, GluttonyFoods::cookie);
	}

	private static void rabbitStew(GluttonyEnchantEffect effect, ItemStack item) {
		effect.player.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 80, 1));
	}

	private static void cookie(GluttonyEnchantEffect effect, ItemStack item) {
        effect.player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 20 * 20, 0));
        effect.player.addPotionEffect(new EffectInstance(Effects.SPEED, 20 * 15, 1));
    }

}
