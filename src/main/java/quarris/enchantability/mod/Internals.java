package quarris.enchantability.mod;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import quarris.enchantability.api.EnchantabilityApi;
import quarris.enchantability.api.IEffectSupplier;
import quarris.enchantability.mod.common.enchants.EnchantEffectRegistry;

import java.util.List;

public class Internals implements EnchantabilityApi.IInternals {

    @Override
    public void registerEnchantEffect(ResourceLocation name, Enchantment enchantment, IEffectSupplier effect) {
        EnchantEffectRegistry.register(name, enchantment, effect);
    }

    @Override
    public List<IEffectSupplier> getEnchantEffects(Enchantment enchantment) {
        return EnchantEffectRegistry.getEffects(enchantment);
    }

    @Override
    public IEffectSupplier getEnchantEffects(ResourceLocation name) {
        return EnchantEffectRegistry.getEffect(name);
    }
}
