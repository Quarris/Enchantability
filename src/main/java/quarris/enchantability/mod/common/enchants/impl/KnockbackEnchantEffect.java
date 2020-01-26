package quarris.enchantability.mod.common.enchants.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import quarris.enchantability.api.enchants.AbstractEnchantEffect;
import quarris.enchantability.mod.common.util.ModRef;

import java.util.UUID;

public class KnockbackEnchantEffect extends AbstractEnchantEffect {

    public static final ResourceLocation NAME = ModRef.createRes("far_reach");

    public KnockbackEnchantEffect(PlayerEntity player, Enchantment enchantment, int level) {
        super(player, enchantment, level);
    }

    @Override
    public void onApplied() {
        player.getAttribute(PlayerEntity.REACH_DISTANCE).applyModifier(new AttributeModifier(
                UUID.fromString("0DD5A1AD-CA11-ADD5-1CED-C0FFEEEFFEC7"),
                NAME::toString,
                this.level() * 2,
                AttributeModifier.Operation.ADDITION
        ));
    }

    @Override
    public void onRemoved() {
        player.getAttribute(PlayerEntity.REACH_DISTANCE).removeModifier(UUID.fromString("0DD5A1AD-CA11-ADD5-1CED-C0FFEEEFFEC7"));
    }

    @Override
    public Enchantment origin() {
        return this.origin;
    }

    @Override
    public int level() {
        return this.level;
    }

    @Override
    public ResourceLocation getName() {
        return NAME;
    }
}
