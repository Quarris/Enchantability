package quarris.enchantability.mod.enchant.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import quarris.enchantability.api.enchant.AbstractEnchantEffect;

import javax.annotation.Nonnull;

public class EnchantEffectSilkTouch extends AbstractEnchantEffect {

    @Override
    public boolean onPlayerAttack(EntityPlayer player, Entity target, int tier) {
        if (player.getHeldItemMainhand().isEmpty()) {
            if (player.world.getBlockState(target.getPosition()).getBlock().isReplaceable(player.world, target.getPosition())) {
                player.world.setBlockState(target.getPosition(), Blocks.WEB.getDefaultState());
            }
            return true;
        }
        return false;
    }

    @Nonnull
    @Override
    public Enchantment getEnchantment() {
        return Enchantments.SILK_TOUCH;
    }
}
