package quarris.enchantability.mod.enchant.impl;

import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.util.text.TextFormatting;
import quarris.enchantability.api.enchant.AbstractEnchantEffect;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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

	@Nullable
	@Override
	public String getDescription() {
		return TextFormatting.GREEN+I18n.format("tooltip.effect.looting");
	}

}
