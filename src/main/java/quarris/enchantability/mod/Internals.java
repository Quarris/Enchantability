package quarris.enchantability.mod;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.Event;
import quarris.enchantability.api.EnchantabilityApi;
import quarris.enchantability.api.IEffectComponent;
import quarris.enchantability.api.IEffectSupplier;
import quarris.enchantability.api.enchants.IEnchantEffect;
import quarris.enchantability.mod.common.enchants.EnchantEffectRegistry;
import quarris.enchantability.mod.common.enchants.impl.GluttonyEnchantEffect;
import quarris.enchantability.mod.common.util.ModRef;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

@SuppressWarnings("unchecked")
public class Internals implements EnchantabilityApi.IInternals {

    @Override
    public void registerEnchantEffect(ResourceLocation name, Enchantment enchantment, IEffectSupplier effect) {
        EnchantEffectRegistry.register(name, enchantment, effect);
    }

    @Override
    public <F extends IEnchantEffect, T extends Event> void registerEffectComponent(ResourceLocation name, Class<T> eventClass, IEffectComponent<F, T> component, Function<T, Collection<PlayerEntity>> playerGetter) {
        EnchantEffectRegistry.registerComponent(name, eventClass, component, playerGetter);
    }

    @Override
    public List<IEffectSupplier> getEnchantEffects(Enchantment enchantment) {
        return EnchantEffectRegistry.getEffects(enchantment);
    }

    @Override
    public IEffectSupplier getEnchantEffect(ResourceLocation name) {
        return EnchantEffectRegistry.getEffect(name);
    }

    @Override
    public void addToDexterityList(Object... things) {
        for (Object obj : things) {
            if (obj instanceof Item) {
                EnchantabilityApi.DEXTERITY_ITEMSTACKS.add(new ItemStack((Item) obj));
            } else if (obj instanceof Tag<?>) {
                EnchantabilityApi.DEXTERITY_TAGS.add((Tag<Item>) obj);
            } else {
                ModRef.LOGGER.error("Tried to add an object to the dexterity list of wrong type");
            }
        }
    }

    @Override
    public void addToMendingList(Food food, BiConsumer<GluttonyEnchantEffect, ItemStack> action) {
        EnchantabilityApi.GLUTTONY_FOODS.put(food, action);
    }
}
