package quarris.enchantability.mod.enchant.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.util.DamageSource;
import quarris.enchantability.api.enchant.AbstractEnchantEffect;

import javax.annotation.Nonnull;

public class EnchantEffectProjectileProt extends AbstractEnchantEffect {



	@Override
	public float onPlayerHurt(EntityPlayer player, DamageSource source, float amount, int tier) {
		return amount;
	}

	@Override
	public boolean onProjectileImpact(EntityPlayer player, Entity projectile, int tier) {
		if (!player.world.isRemote) {
			int level = Math.min(tier, 5);
			if (player.getRNG().nextInt(6-level) == 0) {
				if (projectile instanceof EntityArrow) {
					((EntityArrow) projectile).shoot(-projectile.motionX, -projectile.motionY, -projectile.motionZ, 1, 0);
					return true;
				}
			}
		}
		return false;
	}

	@Nonnull
	@Override
	public Enchantment getEnchantment() {
		return Enchantments.PROJECTILE_PROTECTION;
	}
}