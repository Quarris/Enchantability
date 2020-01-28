package quarris.enchantability.mod.common.enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import quarris.enchantability.api.EnchantabilityApi;
import quarris.enchantability.api.IEffectComponent;
import quarris.enchantability.api.IEffectSupplier;
import quarris.enchantability.api.enchants.IEnchantEffect;
import quarris.enchantability.mod.ModConfig;
import quarris.enchantability.mod.common.enchants.impl.DeflectionEnchantEffect;
import quarris.enchantability.mod.common.enchants.impl.GravityEnchantEffect;
import quarris.enchantability.mod.common.enchants.impl.KnockbackEnchantEffect;
import quarris.enchantability.mod.common.enchants.impl.PunchEnchantEffect;

import java.util.function.Function;

public class Enchants {

    public static void registerEffect() {
        ModConfig config = ModConfig.get();

        // Effects
        if (config.enableFarReach.get())    registerEffect(KnockbackEnchantEffect.NAME, Enchantments.KNOCKBACK, KnockbackEnchantEffect::new);
        if (config.enableFastBreak.get())   registerEffect(PunchEnchantEffect.NAME, Enchantments.PUNCH, PunchEnchantEffect::new);
        if (config.enableGravity.get())     registerEffect(GravityEnchantEffect.NAME, Enchantments.FEATHER_FALLING, GravityEnchantEffect::new);
        if (config.enableDeflection.get())  registerEffect(DeflectionEnchantEffect.NAME, Enchantments.PROJECTILE_PROTECTION, DeflectionEnchantEffect::new);

        // Components
        if (config.enableFastBreak.get())
            registerComponent(PunchEnchantEffect.NAME, PlayerEvent.BreakSpeed.class, PunchEnchantEffect::handBreak, PlayerEvent::getPlayer);
        if (config.enableDeflection.get())
            registerComponent(DeflectionEnchantEffect.NAME, ProjectileImpactEvent.class, DeflectionEnchantEffect::deflect, e -> {
                if (e.getRayTraceResult() instanceof EntityRayTraceResult) {
                    if (((EntityRayTraceResult) e.getRayTraceResult()).getEntity() instanceof PlayerEntity) {
                        return (PlayerEntity)((EntityRayTraceResult) e.getRayTraceResult()).getEntity();
                    }
                }
                return null;
            });
    }

    private static void registerEffect(ResourceLocation name, Enchantment enchantment, IEffectSupplier effectSupplier) {
        EnchantabilityApi.getInstance().registerEnchantEffect(name, enchantment, effectSupplier);
    }

    private static <F extends IEnchantEffect, T extends Event> void registerComponent(ResourceLocation name, Class<T> eventClass, IEffectComponent<F, T> component, Function<T, PlayerEntity> playerGetter) {
        EnchantabilityApi.getInstance().registerEffectComponent(name, eventClass, component, playerGetter);
    }
}
