package quarris.enchantability.mod.common.enchants.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import quarris.enchantability.api.enchants.AbstractEnchantEffect;
import quarris.enchantability.mod.common.util.ModRef;

public class LoyaltyEnchantEffect extends AbstractEnchantEffect {

    public static final ResourceLocation NAME = ModRef.createRes("loyalty");

    public LoyaltyEnchantEffect(PlayerEntity player, Enchantment enchantment, int level) {
        super(player, enchantment, level);
    }

    public static void revivePet(LoyaltyEnchantEffect effect, LivingDeathEvent event) {
        
    }

    @Override
    public ResourceLocation getName() {
        return NAME;
    }
}
