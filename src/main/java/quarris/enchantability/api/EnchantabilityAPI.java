package quarris.enchantability.api;

import net.minecraft.item.ItemStack;
import quarris.enchantability.api.enchant.AbstractEnchantEffect;
import quarris.enchantability.api.enchant.EnchantEffectRegistry;
import quarris.enchantability.api.enchant.IEnchantEffect;

import java.util.ArrayList;
import java.util.List;

public final class EnchantabilityAPI {

    public static final List<ItemStack> EFFICIENCY_ITEMSTACKS = new ArrayList<>();
    public static final List<String> EFFICIENCY_OREDICT = new ArrayList<>();

    /**
     * Adds an {@link IEnchantEffect} to the registry.
     * You can implement your own {@link IEnchantEffect} or extend {@link AbstractEnchantEffect} and override the needed methods.
     *
     * @param effect The effect itself.
     */
    public static void addEnchantEffect(IEnchantEffect effect) {
        EnchantEffectRegistry.register(effect);
    }

    /**
     * Add to the Efficiency list for use in the Efficiency Enchant.
     * @param things Array of objects which are ItemStack for Items and String for Oredicts
     */
    public static void addToEfficiencyList(Object... things) {
        for (Object thing : things) {
            if (thing instanceof ItemStack) {
                EFFICIENCY_ITEMSTACKS.add((ItemStack)thing);
            }
            else if (thing instanceof String) {
                EFFICIENCY_OREDICT.add((String)thing);
            }
            else {
                throw new IllegalArgumentException("Use ItemStack or String for adding to the Efficiency list. Added "+thing.toString());
            }
        }
    }

}
