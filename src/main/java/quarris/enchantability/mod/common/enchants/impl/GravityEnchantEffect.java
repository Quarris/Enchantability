package quarris.enchantability.mod.common.enchants.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import quarris.enchantability.api.enchants.AbstractEnchantEffect;
import quarris.enchantability.mod.common.util.ModRef;

public class GravityEnchantEffect extends AbstractEnchantEffect {

    private static final float[] DISTANCE_REDUCTIONS = new float[] {0.1387018f, 0.333524f, 0.9101194f, 2.079199f};

    public static final ResourceLocation NAME = ModRef.createRes("gravity");

    public GravityEnchantEffect(PlayerEntity player, Enchantment enchantment, int level) {
        super(player, enchantment, level);
    }

    public static void reduceFallDamage(GravityEnchantEffect effect, LivingFallEvent event) {
        event.setDistance(event.getDistance() - DISTANCE_REDUCTIONS[effect.level()-1]);
    }

    @Override
    public void onApplied() {
        this.player.getAttribute(PlayerEntity.ENTITY_GRAVITY).applyModifier(new AttributeModifier(
                ModRef.ENCHANT_UUID,
                NAME::toString,
                -this.level() * 0.0145f,
                AttributeModifier.Operation.ADDITION
        ));
    }

    @Override
    public void onRemoved() {
        this.player.getAttribute(PlayerEntity.ENTITY_GRAVITY).removeModifier(ModRef.ENCHANT_UUID);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (this.player.getAttribute(PlayerEntity.ENTITY_GRAVITY).getModifier(ModRef.ENCHANT_UUID) == null) {
            this.onApplied();
        }
    }

    @Override
    public ResourceLocation getName() {
        return NAME;
    }
}
