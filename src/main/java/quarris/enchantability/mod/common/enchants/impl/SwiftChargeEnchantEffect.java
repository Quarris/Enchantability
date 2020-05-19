package quarris.enchantability.mod.common.enchants.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import quarris.enchantability.api.enchants.AbstractEnchantEffect;
import quarris.enchantability.mod.common.util.ModRef;

import java.lang.reflect.Field;

@SuppressWarnings("ConstantConditions")
public class SwiftChargeEnchantEffect extends AbstractEnchantEffect {

    public static final ResourceLocation NAME = ModRef.createRes("swift_charge");
    private static final Field PORTAL_COUNTER = ObfuscationReflectionHelper.findField(Entity.class, "field_82153_h");
    private static final Field SLEEP_TIMER = ObfuscationReflectionHelper.findField(PlayerEntity.class, "field_71076_b");

    public SwiftChargeEnchantEffect(PlayerEntity player, Enchantment enchantment, int level) {
        super(player, enchantment, level);
    }

    @Override
    public void onApplied() {
        player.getAttribute(SharedMonsterAttributes.ATTACK_SPEED).applyModifier(new AttributeModifier(
                ModRef.ENCHANT_UUID, NAME::toString, this.level()/2f, AttributeModifier.Operation.ADDITION
        ));
    }

    @Override
    public void onRemoved() {
        player.getAttribute(SharedMonsterAttributes.ATTACK_SPEED).removeModifier(ModRef.ENCHANT_UUID);
    }

    public static void itemUse(SwiftChargeEnchantEffect effect, LivingEntityUseItemEvent.Start event) {
        ItemStack item = event.getItem();
        if (item.isFood() || item.getItem() instanceof PotionItem) {
            event.setDuration(event.getDuration() / (effect.level() + 1));
        }
    }

    public static void transition(SwiftChargeEnchantEffect effect, TickEvent.PlayerTickEvent event) {
        PlayerEntity player = effect.player;
        boolean inPortal = ObfuscationReflectionHelper.getPrivateValue(Entity.class, player, "field_71087_bX");
        if (inPortal) {
            try {
                int portalTicks = PORTAL_COUNTER.getInt(player);
                PORTAL_COUNTER.setInt(player, portalTicks+effect.level());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

        if (player.isSleeping()) {
            try {
                int sleepTicks = SLEEP_TIMER.getInt(player);
                SLEEP_TIMER.setInt(player, sleepTicks+effect.level());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
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
