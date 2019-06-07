package quarris.enchantability.mod.enchant.impl;

import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextFormatting;
import quarris.enchantability.api.enchant.AbstractEnchantEffect;
import quarris.enchantability.mod.config.ConfigEnchants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EnchantEffectSmite extends AbstractEnchantEffect {

    @Override
    public boolean onPlayerAttack(EntityPlayer player, Entity target, int tier) {
        if (!player.world.isRemote) {
            int level = Math.min(5, tier);
            if (!ConfigEnchants.modifyEnchants.useSwordTiers) level = 5;
            Item[] swords = {Items.WOODEN_SWORD, Items.STONE_SWORD, Items.IRON_SWORD, Items.GOLDEN_SWORD, Items.DIAMOND_SWORD};
            boolean hasSword = false;
            for (int i = level-1; i >= 0; i--) {
                if (player.getHeldItemMainhand().getItem() == swords[i]) {
                    hasSword = true;
                    break;
                }
            }
            if (hasSword && player.swingProgressInt == 0) {
                float rand = player.getRNG().nextFloat() * ConfigEnchants.modifyEnchants.smiteChance;
                if (rand < tier) {
                    EntityLightningBolt e = new EntityLightningBolt(player.world, target.posX, target.posY, target.posZ, false);
                    player.world.addWeatherEffect(e);
                }
            }
        }
        return false;
    }

    @Nonnull
    @Override
    public Enchantment getEnchantment() {
        return Enchantments.SMITE;
    }

	@Nullable
	@Override
	public String getDescription() {
		return TextFormatting.YELLOW+I18n.format("tooltip.effect.smite");
	}

}
