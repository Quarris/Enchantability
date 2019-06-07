package quarris.enchantability.mod.enchant.impl;

import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import quarris.enchantability.api.enchant.AbstractEnchantEffect;
import quarris.enchantability.mod.config.ConfigEnchants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EnchantEffectFortune extends AbstractEnchantEffect {

    @Override
    public void onItemSmelted(EntityPlayer player, ItemStack output, int tier) {
        World world = player.world;

        if (!world.isRemote) {
            int i = (int)(tier*ConfigEnchants.modifyEnchants.fortuneMultiplier);
            while (i > 0) {
                int j = EntityXPOrb.getXPSplit(i);
                i -= j;
                if (player.getRNG().nextFloat() * 5 < tier) {
                    world.spawnEntity(new EntityXPOrb(world, player.posX+0.5d, player.posY+1, player.posZ+0.5d, j));
                }
            }
        }
    }

    @Nonnull
    @Override
    public Enchantment getEnchantment() {
        return Enchantments.FORTUNE;
    }

	@Nullable
	@Override
	public String getDescription() {
		return TextFormatting.GREEN+I18n.format("tooltip.effect.fortune");
	}
}
