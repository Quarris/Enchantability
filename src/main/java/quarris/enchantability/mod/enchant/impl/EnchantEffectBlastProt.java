package quarris.enchantability.mod.enchant.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.world.Explosion;
import quarris.enchantability.api.enchant.AbstractEnchantEffect;

import javax.annotation.Nonnull;
import java.util.List;

public class EnchantEffectBlastProt extends AbstractEnchantEffect {

    @Override
    public void onExplosionDetonate(EntityPlayer player, Explosion explosion, List<Entity> affectedEntities, int tier) {
        for (int i = explosion.getAffectedBlockPositions().size() - 1; i >= 0; i--) {
            if (player.world.rand.nextInt(5-tier) == 0) {
                explosion.getAffectedBlockPositions().remove(i);
            }
        }
    }

    @Nonnull
    @Override
    public Enchantment getEnchantment() {
        return Enchantments.BLAST_PROTECTION;
    }
}
