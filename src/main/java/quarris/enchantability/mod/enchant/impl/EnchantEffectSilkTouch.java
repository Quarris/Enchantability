package quarris.enchantability.mod.enchant.impl;

import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.util.text.TextFormatting;
import quarris.enchantability.api.enchant.AbstractEnchantEffect;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EnchantEffectSilkTouch extends AbstractEnchantEffect {

    @Override
    public boolean onPlayerAttack(EntityPlayer player, Entity target, int tier) {
        if (target instanceof IAnimals && !(target instanceof IMob)) {
            return true;
        }
        return false;
    }

    @Nonnull
    @Override
    public Enchantment getEnchantment() {
        return Enchantments.SILK_TOUCH;
    }

	@Nullable
	@Override
	public String getDescription() {
		return TextFormatting.WHITE+I18n.format("tooltip.effect.silktouch");
	}

}
