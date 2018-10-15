package quarris.enchantability.mod.enchant.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.Vec3d;
import quarris.enchantability.api.PotionEffectHelper;
import quarris.enchantability.api.enchant.AbstractEnchantEffect;

import javax.annotation.Nonnull;

public class EnchantEffectFireAspect extends AbstractEnchantEffect {


    @Override
    public void onTick(EntityPlayer player, int tier) {
        // Code modified from MonkMod by RWTema
        double celestialAngle = player.world.getCelestialAngle(0) * Math.PI * 2;
        double sunHeight = Math.cos(celestialAngle);
        Vec3d vec3d1 = player.getLook(1.0F);
        Vec3d sunDir = new Vec3d(-Math.sin(celestialAngle), sunHeight, 0);
        if (sunHeight >= 0 && sunDir.dotProduct(vec3d1) > 0.996) {
            PotionEffectHelper.applyPotionEffectAtInterval(player, MobEffects.REGENERATION, 20, 80, tier, true);
        }
        else if (sunHeight < 0) {
            PotionEffectHelper.applyPotionEffectAtInterval(player, MobEffects.WEAKNESS, 20, 80, tier, true);
        }
    }

    @Nonnull
    @Override
    public Enchantment getEnchantment() {
        return Enchantments.FIRE_ASPECT;
    }
}
