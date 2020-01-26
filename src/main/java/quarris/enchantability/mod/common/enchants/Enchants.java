package quarris.enchantability.mod.common.enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.util.ResourceLocation;
import quarris.enchantability.api.EnchantabilityApi;
import quarris.enchantability.api.IEffectSupplier;
import quarris.enchantability.mod.common.enchants.impl.KnockbackEnchantEffect;

public class Enchants {

    public static void register() {
        register(KnockbackEnchantEffect.NAME, Enchantments.KNOCKBACK, KnockbackEnchantEffect::new);
    }

    private static void register(ResourceLocation name, Enchantment enchantment, IEffectSupplier effectSupplier) {
        EnchantabilityApi.getInstance().registerEnchantEffect(name, enchantment, effectSupplier);
    }
}
