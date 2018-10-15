package quarris.enchantability.mod.enchant.impl;

import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.util.math.BlockPos;
import quarris.enchantability.api.enchant.AbstractEnchantEffect;

import javax.annotation.Nonnull;

public class EnchantEffectPunch extends AbstractEnchantEffect {

    @Override
    public float breakSpeed(EntityPlayer player, IBlockState state, BlockPos pos, float originalSpeed, int tier) {

        if (player.getHeldItemMainhand().isEmpty()) {
            return originalSpeed + tier;
        }
        return originalSpeed;
    }

    @Nonnull
    @Override
    public Enchantment getEnchantment() {
        return Enchantments.PUNCH;
    }
}
