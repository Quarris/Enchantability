package quarris.enchantability.mod.common.enchants.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
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

	@Override
	public ResourceLocation getName() {
		return NAME;
	}
}
