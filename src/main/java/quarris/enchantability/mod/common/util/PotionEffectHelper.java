package quarris.enchantability.mod.common.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;

public class PotionEffectHelper {

    public static void applyPotionEffectAtInterval(PlayerEntity player, Effect potion, int minTicks, int maxTicks, int tier, boolean showParticles) {
        EffectInstance effect = player.getActivePotionEffect(potion);
        if (effect == null || effect.getDuration() < minTicks) {
            player.addPotionEffect(new EffectInstance(potion, maxTicks, tier, false, showParticles));
        }
    }

}
