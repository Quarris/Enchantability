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
        if (source.getImmediateSource() instanceof EntityArrow) {
            return amount-tier/2f;
        }
        return amount;
    }

    @Override
    public boolean onProjectileImpact(EntityPlayer player, Entity projectile, int tier) {
        if (projectile instanceof EntityArrow) {
            float speed = (float)Math.sqrt(Math.abs(projectile.motionX * projectile.motionX) + Math.abs(projectile.motionY * projectile.motionY) + Math.abs(projectile.motionZ * projectile.motionZ));
            ((EntityArrow) projectile).shoot(((EntityArrow) projectile).shootingEntity, -projectile.rotationPitch, -projectile.rotationYaw, 0, speed, 0);
            EntityArrow arrow = ((ItemArrow)Items.ARROW).createArrow(player.world, new ItemStack(Items.ARROW), (EntityLivingBase)((EntityArrow) projectile).shootingEntity);
            arrow.shoot(projectile.posX, projectile.posY, projectile.posZ, 100, 0);
            //TODO stop creating like 20 arrows at once
            player.world.spawnEntity(arrow);
            return true;
        }
        return false;
    }

    @Nonnull
    @Override
    public Enchantment getEnchantment() {
        return Enchantments.PROJECTILE_PROTECTION;
    }
}