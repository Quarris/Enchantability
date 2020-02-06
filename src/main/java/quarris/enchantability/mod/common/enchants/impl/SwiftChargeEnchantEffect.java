package quarris.enchantability.mod.common.enchants.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import quarris.enchantability.api.enchants.AbstractEnchantEffect;
import quarris.enchantability.mod.common.util.ModRef;

public class SwiftChargeEnchantEffect extends AbstractEnchantEffect {

    public static final ResourceLocation NAME = ModRef.createRes("swift_charge");

    public SwiftChargeEnchantEffect(PlayerEntity player, Enchantment enchantment, int level) {
        super(player, enchantment, level);
    }

    @Override
    public void onApplied() {
        player.getAttribute(SharedMonsterAttributes.ATTACK_SPEED).applyModifier(new AttributeModifier(
                ModRef.ENCHANT_UUID, NAME::toString, this.level/2f, AttributeModifier.Operation.ADDITION
        ));
    }

    @Override
    public void onRemoved() {
        player.getAttribute(SharedMonsterAttributes.ATTACK_SPEED).removeModifier(ModRef.ENCHANT_UUID);
    }

    public static void itemUse(SwiftChargeEnchantEffect effect, LivingEntityUseItemEvent.Start event) {
        ItemStack item = event.getItem();
        if (item.isFood() || item.getItem() instanceof PotionItem) {
            event.setDuration(event.getDuration() / (effect.level + 1));
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (this.player.getAttribute(SharedMonsterAttributes.ATTACK_SPEED).getModifier(ModRef.ENCHANT_UUID) == null) {
            this.onApplied();
        }
    }

    @Override
    public ResourceLocation getName() {
        return NAME;
    }
}
