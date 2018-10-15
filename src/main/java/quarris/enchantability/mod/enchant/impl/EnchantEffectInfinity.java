package quarris.enchantability.mod.enchant.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.DimensionManager;
import quarris.enchantability.api.enchant.AbstractEnchantEffect;

import javax.annotation.Nonnull;

public class EnchantEffectInfinity extends AbstractEnchantEffect {

    @Override
    public void onLivingUpdate(EntityPlayer player, int tier) {
        if (!player.world.isRemote) {
            if (player.dimension == 1) {
                if (player.posY <= -60) {
                    player.changeDimension(0, (world, entity, yaw) -> {
                        BlockPos retunPos = player.getPosition().add(DimensionManager.getWorld(0).getSpawnPoint());
                        entity.setPosition(retunPos.getX(), 256, retunPos.getZ());
                    });
                }
            }
        }
    }

    @Nonnull
    @Override
    public Enchantment getEnchantment() {
        return Enchantments.INFINITY;
    }
}
