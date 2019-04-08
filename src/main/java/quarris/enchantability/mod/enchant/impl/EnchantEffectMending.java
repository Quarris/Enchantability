package quarris.enchantability.mod.enchant.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import quarris.enchantability.api.EnchantabilityAPI;
import quarris.enchantability.api.enchant.AbstractEnchantEffect;
import quarris.enchantability.api.enchant.mending.MendingAction;
import quarris.enchantability.api.enchant.mending.MendingResult;
import quarris.enchantability.mod.utils.EnchantmentUtils;

import javax.annotation.Nonnull;

public class EnchantEffectMending extends AbstractEnchantEffect {

	public static void registerFoodActions() {
		EnchantabilityAPI.getInstance().addToMendingList((ItemFood)Items.COOKED_BEEF, new MendingAction((mendingResult -> {
			PotionEffect[] effects = mendingResult.player.getActivePotionEffects().toArray(new PotionEffect[0]);
			for (int i = 0; i < effects.length; i++) {
				PotionEffect effect = effects[i];
				if (effect.getPotion().isBadEffect()) {
					mendingResult.player.removePotionEffect(effect.getPotion());
				}
			}
		}), 10));
	}

	@Override
	public ItemStack onItemUseFinish(EntityPlayer player, ItemStack result, int tier) {
		if (result.getItem() instanceof ItemFood) {
			ItemFood food = (ItemFood) result.getItem();

			for (MendingAction action : EnchantabilityAPI.MENDING_EFFECTS.get(food)) {
				if (action.cost <= EnchantmentUtils.getPlayerXP(player)) {
					action.result.accept(new MendingResult(result, player, tier));
					EnchantmentUtils.addPlayerXP(player, -action.cost);
				}
			}
		}
		return result;
	}

	@Nonnull
	@Override
	public Enchantment getEnchantment() {
		return Enchantments.MENDING;
	}
}
