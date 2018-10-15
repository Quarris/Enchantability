package quarris.enchantability.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class PotionEffectHelper {

    /**
     * Applies a potion effect at certain intervals. Prevents the effects to be constantly updates which allows Regen to work and can fix Night Vision blinking.
     * @param player The player to apply the effect to.
     * @param potion The potion effect to apply. Can be accessed from {@link MobEffects}.
     * @param minTicks The tick duration at which to reapply the effect.
     * @param maxTicks The duration to reapply the effect for.
     * @param tier The tier of the effect.
     * @param showParticles Whether to show the particles.
     */
    public static void applyPotionEffectAtInterval(EntityPlayer player, Potion potion, int minTicks, int maxTicks, int tier, boolean showParticles) {
        PotionEffect effect = player.getActivePotionEffect(potion);
        if (effect == null || effect.getDuration() < minTicks) {
            player.addPotionEffect(new PotionEffect(potion, maxTicks, tier, false, showParticles));
        }
    }
}
