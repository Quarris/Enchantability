package quarris.enchantability.mod.enchant.impl;

import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.DimensionManager;
import quarris.enchantability.api.enchant.AbstractEnchantEffect;
import quarris.enchantability.mod.config.ConfigEnchants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EnchantEffectInfinity extends AbstractEnchantEffect {

    @Override
    public void onLivingUpdate(EntityPlayer player, int tier) {
        if (!player.world.isRemote) {
            if (player.dimension == 1) {
                if (player.posY <= -60) {
                    player.changeDimension(0, (world, entity, yaw) -> {
                        BlockPos returnPos = player.getPosition();
                        if (ConfigEnchants.modifyEnchants.offsetEndPositionFromWorldSpawn) {
                            returnPos = returnPos.add(DimensionManager.getWorld(0).getSpawnPoint());
                        }
                        entity.setPosition(returnPos.getX(), 256, returnPos.getZ());
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

	@Nullable
	@Override
	public String getDescription() {
		return TextFormatting.DARK_GRAY+I18n.format("tooltip.effect.infinity");
	}
}
