package quarris.enchantability.mod.enchant.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import quarris.enchantability.api.enchant.AbstractEnchantEffect;

import javax.annotation.Nonnull;

public class EnchantEffectLooting extends AbstractEnchantEffect {

    @Override
    public int onExperienceDrop(EntityPlayer player, EntityLivingBase dropper, int originalXP, int droppedXP, int tier) {
        return (int)(droppedXP*(tier-0.5f));
    }

    @Nonnull
    @Override
    public Enchantment getEnchantment() {
        return Enchantments.LOOTING;
    }
}
