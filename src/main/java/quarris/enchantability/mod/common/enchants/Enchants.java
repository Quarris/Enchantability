package quarris.enchantability.mod.common.enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import quarris.enchantability.api.EnchantabilityApi;
import quarris.enchantability.api.IEffectComponent;
import quarris.enchantability.api.IEffectSupplier;
import quarris.enchantability.api.enchants.IEnchantEffect;
import quarris.enchantability.mod.common.enchants.impl.KnockbackEnchantEffect;
import quarris.enchantability.mod.common.enchants.impl.PunchEnchantEffect;

public class Enchants {

    public static void registerEffect() {
        // Effects
        registerEffect(KnockbackEnchantEffect.NAME, Enchantments.KNOCKBACK, KnockbackEnchantEffect::new);
        registerEffect(PunchEnchantEffect.NAME, Enchantments.PUNCH, PunchEnchantEffect::new);

        // Components
        registerComponent(PunchEnchantEffect.NAME, PlayerEvent.BreakSpeed.class, PunchEnchantEffect::handBreak);
    }

    private static void registerEffect(ResourceLocation name, Enchantment enchantment, IEffectSupplier effectSupplier) {
        EnchantabilityApi.getInstance().registerEnchantEffect(name, enchantment, effectSupplier);
    }

    private static <F extends IEnchantEffect, T extends Event> void registerComponent(ResourceLocation name, Class<T> eventClass, IEffectComponent<F, T> component) {
        EnchantabilityApi.getInstance().registerEffectComponent(name, eventClass, component);
    }
}
