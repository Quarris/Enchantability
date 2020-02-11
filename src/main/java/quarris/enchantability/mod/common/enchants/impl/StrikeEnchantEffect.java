package quarris.enchantability.mod.common.enchants.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import quarris.enchantability.api.enchants.AbstractEnchantEffect;
import quarris.enchantability.mod.common.util.ModRef;

public class StrikeEnchantEffect extends AbstractEnchantEffect {

    public static final ResourceLocation NAME = ModRef.createRes("strike");

    public StrikeEnchantEffect(PlayerEntity player, Enchantment enchantment, int level) {
        super(player, enchantment, level);
    }

    public static void strike(StrikeEnchantEffect effect, LivingHurtEvent event) {
        if (effect.player.getHeldItemMainhand().isEmpty()) {
            event.setAmount(event.getAmount() + effect.level);
        }
    }

    @Override
    public ResourceLocation getName() {
        return NAME;
    }
}
