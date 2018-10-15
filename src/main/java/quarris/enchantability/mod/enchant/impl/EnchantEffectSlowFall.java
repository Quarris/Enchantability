package quarris.enchantability.mod.enchant.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import quarris.enchantability.api.enchant.AbstractEnchantEffect;

import javax.annotation.Nonnull;

public class EnchantEffectSlowFall extends AbstractEnchantEffect {

    @Override
    public void onLivingUpdate(EntityPlayer player, int tier) {
        if (player.motionY < -0.08d) {
            player.motionY *= (1 - tier/10d);
            player.fallDistance -= tier;
        }
    }


    @Nonnull
    @Override
    public Enchantment getEnchantment() {
        return Enchantments.FEATHER_FALLING;
    }
}
