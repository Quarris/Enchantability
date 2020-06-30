package quarris.enchantability.mod.common.enchants.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.event.TickEvent;
import quarris.enchantability.api.enchants.AbstractEnchantEffect;
import quarris.enchantability.mod.common.util.ModRef;
import quarris.enchantability.mod.common.util.PotionEffectHelper;

public class FirePraiseEnchantEffect extends AbstractEnchantEffect {

    public static final ResourceLocation NAME = ModRef.createRes("fire_praise");

    public FirePraiseEnchantEffect(PlayerEntity player, Enchantment enchantment, int level) {
        super(player, enchantment, level);
    }

    public static void praiseTheSun(FirePraiseEnchantEffect effect, TickEvent.PlayerTickEvent event) {
        // Code modified from MonkMod by RWTema
        PlayerEntity player = effect.player;
        double celestialAngle = player.world.getCelestialAngle(0) * Math.PI * 2;
        double sunHeight = Math.cos(celestialAngle);
        Vector3d vec3d1 = player.getLook(1.0F);
        Vector3d sunDir = new Vector3d(-Math.sin(celestialAngle), sunHeight, 0);
        if (player.world.canBlockSeeSky(player.func_233580_cy_())) {
            if (sunHeight >= 0 && sunDir.dotProduct(vec3d1) > 0.996) {
                PotionEffectHelper.applyPotionEffectAtInterval(player, Effects.REGENERATION, 20, 80, effect.level()-1, true);
            }
            else if (sunHeight < 0) {
                PotionEffectHelper.applyPotionEffectAtInterval(player, Effects.WEAKNESS, 20, 80, 0, true);
            }
        }
    }

    @Override
    public ResourceLocation getName() {
        return NAME;
    }
}
