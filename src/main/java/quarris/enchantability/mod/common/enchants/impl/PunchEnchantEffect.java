package quarris.enchantability.mod.common.enchants.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerEvent;
import quarris.enchantability.api.enchants.AbstractEnchantEffect;
import quarris.enchantability.mod.common.util.ModRef;

public class PunchEnchantEffect extends AbstractEnchantEffect {

    public static final ResourceLocation NAME = ModRef.createRes("hand_break");

    public PunchEnchantEffect(PlayerEntity player, Enchantment enchantment, int level) {
        super(player, enchantment, level);
    }

    public static void handBreak(PunchEnchantEffect effect, PlayerEvent.BreakSpeed event) {
        if (effect.player.getHeldItemMainhand().isEmpty()) {
            event.setNewSpeed(event.getOriginalSpeed() + effect.level * 2);
        }
    }

    @Override
    public ResourceLocation getName() {
        return NAME;
    }
}
