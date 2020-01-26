package quarris.enchantability.api.capabilities;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.IItemHandlerModifiable;
import quarris.enchantability.api.EnchantabilityApi;
import quarris.enchantability.api.enchants.IEnchantEffect;

import java.util.*;
import java.util.function.BiFunction;

public interface IPlayerEnchant extends IItemHandlerModifiable, ICapabilitySerializable<CompoundNBT> {

    List<IEnchantEffect> getEnchants();

    PlayerEntity getPlayer();

    default Map<Enchantment, Integer> getStoredEnchantments() {
        Map<Enchantment, Integer> storedEnchantments = new HashMap<>();
        for (int slot = 0; slot < this.getSlots(); slot++) {
            ItemStack stack = this.getStackInSlot(slot);
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
            for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                Integer level = storedEnchantments.get(entry.getKey());
                if (level == null || entry.getValue() > level) {
                    storedEnchantments.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return storedEnchantments;
    }

    default boolean isEffectIn(IEnchantEffect effect, Map<Enchantment, Integer> collection) {
        for (Map.Entry<Enchantment, Integer> entry : collection.entrySet()) {
            if (effect.origin() == entry.getKey() && effect.level() == entry.getValue())
                return true;
        }
        return false;
    }

    default void updateEffects() {
        if (this.getPlayer().world.isRemote) return;
        Map<Enchantment, Integer> storedEnchantments = this.getStoredEnchantments();
        Set<Enchantment> currentEnchantments = new HashSet<>();
        List<IEnchantEffect> enchants = this.getEnchants();
        for (int i = enchants.size() - 1; i >= 0; i--) {
            IEnchantEffect effect = enchants.get(i);
            if (!this.isEffectIn(effect, storedEnchantments)) {
                enchants.remove(i);
            } else {
                currentEnchantments.add(effect.origin());
            }
        }

        for (Map.Entry<Enchantment, Integer> entry : storedEnchantments.entrySet()) {
            Enchantment enchantment = entry.getKey();
            if (!currentEnchantments.contains(enchantment)) {
                List<BiFunction<Enchantment, Integer, IEnchantEffect>> effects = EnchantabilityApi.getInstance().getEnchantEffects(enchantment);
                for (BiFunction<Enchantment, Integer, IEnchantEffect> effect : effects) {
                    enchants.add(effect.apply(enchantment, entry.getValue()));
                }
            }
        }
    }

    boolean isDirty();

    void markDirty(boolean dirty);

    CompoundNBT serializeEffects(CompoundNBT nbt);

    void deserializeEffects(CompoundNBT nbt);

}
