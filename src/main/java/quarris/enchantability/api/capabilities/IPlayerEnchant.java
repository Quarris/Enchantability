package quarris.enchantability.api.capabilities;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.IItemHandlerModifiable;
import quarris.enchantability.api.EnchantabilityApi;
import quarris.enchantability.api.IEffectSupplier;
import quarris.enchantability.api.enchants.IEnchantEffect;

import java.util.*;

public interface IPlayerEnchant extends IItemHandlerModifiable, ICapabilitySerializable<CompoundNBT> {

    List<IEnchantEffect> getEnchants();

    PlayerEntity getPlayer();

    boolean hasEnchant(ResourceLocation name);

    IEnchantEffect getEnchant(ResourceLocation name);

    // Returns the stack which is deleted when its set extended = false.
    // This is so that you can drop the item or add it to the inventory or whatever
    // I personally won't be adding a way to set this to false, but you might
    ItemStack setExtended(boolean extended);

    boolean isExtended();

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
                effect.onRemoved();
                enchants.remove(i);
            } else {
                currentEnchantments.add(effect.origin());
            }
        }

        for (Map.Entry<Enchantment, Integer> entry : storedEnchantments.entrySet()) {
            Enchantment enchantment = entry.getKey();
            if (!currentEnchantments.contains(enchantment)) {
                List<IEffectSupplier> effects = EnchantabilityApi.getEnchantEffects(enchantment);
                for (IEffectSupplier effectSupplier : effects) {
                    IEnchantEffect effect = effectSupplier.create(this.getPlayer(), enchantment, entry.getValue());
                    effect.onApplied();
                    enchants.add(effect);
                }
            }
        }
    }

    boolean isDirty();

    void markDirty(boolean dirty);

    CompoundNBT serializeEffects(CompoundNBT nbt);

    void deserializeEffects(CompoundNBT nbt);

}
