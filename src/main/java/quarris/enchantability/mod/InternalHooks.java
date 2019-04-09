package quarris.enchantability.mod;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import quarris.enchantability.api.EnchantabilityAPI;
import quarris.enchantability.api.enchant.EnchantEffectRegistry;
import quarris.enchantability.api.enchant.IEnchantEffect;
import quarris.enchantability.api.enchant.mending.MendingAction;
import quarris.enchantability.api.enchant.mending.MendingResult;

import java.util.function.Consumer;

public class InternalHooks implements EnchantabilityAPI.IInternals {

	@Override
	public void addEnchantEffect(IEnchantEffect effect) {
		EnchantEffectRegistry.register(effect);
	}

	@Override
	public void addToEfficiencyList(Object... things) {
		for (Object thing : things) {
			if (thing instanceof ItemStack) {
				EnchantabilityAPI.EFFICIENCY_ITEMSTACKS.add((ItemStack)thing);
			}
			else if (thing instanceof String) {
				EnchantabilityAPI.EFFICIENCY_OREDICT.add((String)thing);
			}
			else {
				throw new IllegalArgumentException("Use ItemStack or String for adding to the Efficiency list. Added "+thing.toString());
			}
		}
	}

	@Override
	public void addToMendingList(ItemFood food, MendingAction action) {
		EnchantabilityAPI.MENDING_EFFECTS.put(food, action);
	}
}
