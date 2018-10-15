package quarris.enchantability.mod.enchant;

import quarris.enchantability.api.enchant.EnchantEffectRegistry;
import quarris.enchantability.api.enchant.IEnchantEffect;
import quarris.enchantability.mod.enchant.impl.*;

public class Enchants {

    public static void init() {
        register(new EnchantEffectFireAspect());
        register(new EnchantEffectVanishing());
        register(new EnchantEffectSlowFall());
        register(new EnchantEffectInfinity());
        register(new EnchantEffectEfficiency());
    }

    private static void register(IEnchantEffect effect) {
        EnchantEffectRegistry.register(effect);
    }
}
