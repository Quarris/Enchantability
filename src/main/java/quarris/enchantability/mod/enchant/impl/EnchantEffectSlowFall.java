package quarris.enchantability.mod.enchant.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import quarris.enchantability.api.enchant.AbstractEnchantEffect;

import javax.annotation.Nonnull;

public class EnchantEffectSlowFall extends AbstractEnchantEffect {

    @Override
    public void onLivingUpdate(EntityPlayer player, int tier) {
        int level = Math.min(9, tier);
        if (!player.isSneaking() && player.motionY < -0.08d) {
            player.motionY *= (1 - level/15d);
            if (player.fallDistance > 0) player.fallDistance += player.motionY;
        }
    }


    @Nonnull
    @Override
    public Enchantment getEnchantment() {
        return Enchantments.FEATHER_FALLING;
    }
}
