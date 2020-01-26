package quarris.enchantability.mod.common.enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.util.ResourceLocation;
import quarris.enchantability.api.EnchantabilityApi;
import quarris.enchantability.api.enchants.IEnchantEffect;
import quarris.enchantability.mod.common.enchants.impl.KnockbackEnchantEffect;

import java.util.function.BiFunction;

public class Enchants {

    public static void register() {
        register(KnockbackEnchantEffect.NAME, Enchantments.KNOCKBACK, KnockbackEnchantEffect::new);
    }

    private static void register(ResourceLocation name, Enchantment enchantment, BiFunction<Enchantment, Integer, IEnchantEffect> effect) {
        EnchantabilityApi.getInstance().registerEnchantEffect(name, enchantment, effect);
    }
}
