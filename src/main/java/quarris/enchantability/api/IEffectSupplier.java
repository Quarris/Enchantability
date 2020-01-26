package quarris.enchantability.api;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import quarris.enchantability.api.enchants.IEnchantEffect;

public interface IEffectSupplier {

    IEnchantEffect create(PlayerEntity entity, Enchantment enchantment, int level);
}
