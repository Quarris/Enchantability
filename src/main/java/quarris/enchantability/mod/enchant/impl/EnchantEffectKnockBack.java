package quarris.enchantability.mod.enchant.impl;

import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.util.text.TextFormatting;
import quarris.enchantability.api.enchant.AbstractEnchantEffect;
import quarris.enchantability.mod.config.ConfigEnchants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public class EnchantEffectKnockBack extends AbstractEnchantEffect {

    @Override
    public void onAdded(EntityPlayer player, int tier) {
        player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).applyModifier(new AttributeModifier(UUID.fromString("0DD5A1AD-CA11-ADD5-1CED-C0FFEEEFFEC7"), "knockback_dist", tier * (1+ConfigEnchants.modifyEnchants.knockbackModifier), 0));
    }

    @Override
    public void onRemoved(EntityPlayer player, int tier) {
        player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).removeModifier(UUID.fromString("0DD5A1AD-CA11-ADD5-1CED-C0FFEEEFFEC7"));
    }

    @Nonnull
    @Override
    public Enchantment getEnchantment() {
        return Enchantments.KNOCKBACK;
    }

	@Nullable
	@Override
	public String getDescription() {
		return TextFormatting.LIGHT_PURPLE+I18n.format("tooltip.effect.knockback");
	}
}
