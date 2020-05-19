package quarris.enchantability.mod;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.Event;
import quarris.enchantability.api.EnchantabilityApi;
import quarris.enchantability.api.IEffectComponent;
import quarris.enchantability.api.IEffectSupplier;
import quarris.enchantability.api.enchants.IEnchantEffect;
import quarris.enchantability.mod.common.enchants.EnchantEffectRegistry;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@SuppressWarnings("unchecked")
public class Internals implements EnchantabilityApi.IInternals {

    @Override
    public void registerEnchantEffect(ResourceLocation name, Enchantment enchantment, IEffectSupplier effect) {
        EnchantEffectRegistry.register(name, enchantment, effect);
    }

    @Override
    public <F extends IEnchantEffect, T extends Event> void registerEffectComponent(ResourceLocation name, Class<T> eventClass, IEffectComponent<F, T> component, Function<T, Collection<PlayerEntity>> playerGetter) {
        EnchantEffectRegistry.registerComponent(name, eventClass, component, playerGetter);
    }

    @Override
    public List<IEffectSupplier> getEnchantEffects(Enchantment enchantment) {
        return EnchantEffectRegistry.getEffects(enchantment);
    }

    @Override
    public IEffectSupplier getEnchantEffect(ResourceLocation name) {
        return EnchantEffectRegistry.getEffect(name);
    }
}
