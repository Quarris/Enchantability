package quarris.enchantability.mod.common.enchants.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerEvent;
import quarris.enchantability.api.enchants.AbstractEnchantEffect;
import quarris.enchantability.mod.ModConfig;
import quarris.enchantability.mod.common.util.ModRef;

public class MetalFistEnchantEffect extends AbstractEnchantEffect {

    public static final ResourceLocation NAME = ModRef.createRes("metal_fist");

    public MetalFistEnchantEffect(PlayerEntity player, Enchantment enchantment, int level) {
        super(player, enchantment, level);
    }

    public static void harvestCheck(MetalFistEnchantEffect effect, PlayerEvent.HarvestCheck event) {
        if (effect.player.getHeldItemMainhand().isEmpty()) {
            if (effect.level() >= 5) {
                event.setCanHarvest(true);
            } else if (effect.level() >= event.getTargetBlock().getHarvestLevel() + 1) { // effect.level starts from 1
                event.setCanHarvest(true);
            }
        }
    }

    public static void breakSpeed(MetalFistEnchantEffect effect, PlayerEvent.BreakSpeed event) {
        if (effect.player.getHeldItemMainhand().isEmpty() && effect.level() >= 5) {
            event.setNewSpeed(event.getNewSpeed() * ModConfig.get().speedMultiplier.get().floatValue());
        }
    }

    @Override
    public ResourceLocation getName() {
        return NAME;
    }
}
