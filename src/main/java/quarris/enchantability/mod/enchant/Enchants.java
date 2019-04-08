package quarris.enchantability.mod.enchant;

import quarris.enchantability.api.enchant.EnchantEffectRegistry;
import quarris.enchantability.api.enchant.IEnchantEffect;
import quarris.enchantability.mod.config.ConfigEnchants;
import quarris.enchantability.mod.enchant.impl.*;

public class Enchants {

    public static void init() {
        if (ConfigEnchants.initEnchants.fireAspect)             register(new EnchantEffectFireAspect());
        //if (ConfigEnchants.initEnchants.vanishing)              register(new EnchantEffectVanishing());
        if (ConfigEnchants.initEnchants.featherFalling)         register(new EnchantEffectSlowFall());
        if (ConfigEnchants.initEnchants.infinity)               register(new EnchantEffectInfinity());
        if (ConfigEnchants.initEnchants.efficiency)             register(new EnchantEffectEfficiency());
        if (ConfigEnchants.initEnchants.punch)                  register(new EnchantEffectPunch());
        if (ConfigEnchants.initEnchants.silktouch)              register(new EnchantEffectSilkTouch());
        if (ConfigEnchants.initEnchants.knockback)              register(new EnchantEffectKnockBack());
        if (ConfigEnchants.initEnchants.blastProtection)        register(new EnchantEffectBlastProt());
        if (ConfigEnchants.initEnchants.projectileProtection)   register(new EnchantEffectProjectileProt());
        if (ConfigEnchants.initEnchants.frostWalker)            register(new EnchantEffectFrostWalker());
        if (ConfigEnchants.initEnchants.smite)                  register(new EnchantEffectSmite());
        //if (ConfigEnchants.initEnchants.binding)                register(new EnchantEffectBinding());
        if (ConfigEnchants.initEnchants.looting)                register(new EnchantEffectLooting());
        if (ConfigEnchants.initEnchants.fortune)                register(new EnchantEffectFortune());
        if (ConfigEnchants.initEnchants.mending)				register(new EnchantEffectMending());

    }

    private static void register(IEnchantEffect effect) {
        EnchantEffectRegistry.register(effect);
    }
}
