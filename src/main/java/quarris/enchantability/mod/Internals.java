package quarris.enchantability.mod;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import quarris.enchantability.api.EnchantabilityApi;
import quarris.enchantability.api.enchants.IEnchantEffect;
import quarris.enchantability.mod.common.enchants.EnchantEffectRegistry;

import java.util.List;
import java.util.function.BiFunction;

public class Internals implements EnchantabilityApi.IInternals {

    @Override
    public void registerEnchantEffect(ResourceLocation name, Enchantment enchantment, BiFunction<Enchantment, Integer, IEnchantEffect> effect) {
        EnchantEffectRegistry.register(name, enchantment, effect);
    }

    @Override
    public List<BiFunction<Enchantment, Integer, IEnchantEffect>> getEnchantEffects(Enchantment enchantment) {
        return EnchantEffectRegistry.getEffects(enchantment);
    }

    @Override
    public BiFunction<Enchantment, Integer, IEnchantEffect> getEnchantEffects(ResourceLocation name) {
        return EnchantEffectRegistry.getEffect(name);
    }
}
