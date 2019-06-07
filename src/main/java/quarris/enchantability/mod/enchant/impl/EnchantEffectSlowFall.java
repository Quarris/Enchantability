package quarris.enchantability.mod.enchant.impl;

import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.util.text.TextFormatting;
import quarris.enchantability.api.enchant.AbstractEnchantEffect;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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

	@Nullable
	@Override
	public String getDescription() {
		return TextFormatting.GRAY+I18n.format("tooltip.effect.slowfall");
	}

}
