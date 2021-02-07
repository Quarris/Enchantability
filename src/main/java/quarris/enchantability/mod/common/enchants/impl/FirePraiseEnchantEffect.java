package quarris.enchantability.mod.common.enchants.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
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
        PlayerEntity player = effect.player;
        World world = player.world;
        if (world.getLightFor(LightType.SKY, player.getPosition()) > 7 && !player.isInWater() && world.getDayTime() < 12000) {
            int regenLevel = (effect.level() + 1) / 2;
            if (effect.level() > 3) regenLevel++;
            int strengthLevel = effect.level() / 2;
            int resistanceLevel = effect.level() / 3;
            if (effect.level() > 3) resistanceLevel++;
            if (strengthLevel > 0) {
                PotionEffectHelper.applyPotionEffectAtInterval(player, Effects.STRENGTH, 20, 80, strengthLevel - 1, true);
            }
            if (regenLevel > 0) {
                PotionEffectHelper.applyPotionEffectAtInterval(player, Effects.REGENERATION, 20, 80, regenLevel - 1, true);
            }
            if (resistanceLevel > 0) {
                PotionEffectHelper.applyPotionEffectAtInterval(player, Effects.RESISTANCE, 20, 80, resistanceLevel - 1, true);
            }
        }
    }

    @Override
    public ResourceLocation getName() {
        return NAME;
    }
}
