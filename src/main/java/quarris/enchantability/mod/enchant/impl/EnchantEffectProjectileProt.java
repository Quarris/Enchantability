package quarris.enchantability.mod.enchant.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
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
            int level = Math.min(tier, 10);
            if (player.getRNG().nextInt(level) == 0)
            if (projectile instanceof EntityArrow) {
                float speed = (float)Math.sqrt(Math.abs(projectile.motionX * projectile.motionX) + Math.abs(projectile.motionY * projectile.motionY) + Math.abs(projectile.motionZ * projectile.motionZ));
                ((EntityArrow) projectile).shoot(-projectile.motionX, -projectile.motionY, -projectile.motionZ, 1, 0);
                return true;
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