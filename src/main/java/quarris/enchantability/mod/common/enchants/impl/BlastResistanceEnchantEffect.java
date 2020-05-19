package quarris.enchantability.mod.common.enchants.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.world.ExplosionEvent;
import quarris.enchantability.api.enchants.AbstractEnchantEffect;
import quarris.enchantability.mod.common.util.ModRef;
import quarris.enchantability.mod.common.util.ModUtil;

public class BlastResistanceEnchantEffect extends AbstractEnchantEffect {

    public static final ResourceLocation NAME = ModRef.createRes("blast_resistance");

    public BlastResistanceEnchantEffect(PlayerEntity player, Enchantment enchantment, int level) {
        super(player, enchantment, level);
    }

    public static void resistBlast(BlastResistanceEnchantEffect effect, ExplosionEvent.Detonate event) {
        PlayerEntity player = effect.player;
        if (!player.world.isRemote()) {
            float chance = 0.25f * effect.level();
            for (int i = event.getAffectedBlocks().size() - 1; i >= 0; i--) {
                if (chance >= ModUtil.RANDOM.nextFloat()) {
                    event.getAffectedBlocks().remove(i);
                }
            }
        }
    }

    @Override
    public ResourceLocation getName() {
        return NAME;
    }
}
