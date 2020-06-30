package quarris.enchantability.api;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.eventbus.api.Event;
import quarris.enchantability.api.capabilities.IPlayerEnchant;
import quarris.enchantability.api.enchants.IEnchantEffect;
import quarris.enchantability.mod.common.enchants.impl.GluttonyEnchantEffect;
import quarris.enchantability.mod.common.util.ModRef;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * This class contains all the calls that you may want to use from Enchantability compatibiltiy for your mod.
 * Note that many of the calls require the Enchantability's constructor to be called, so make sure that if you use it the mod has been initialized.
 */
public class EnchantabilityApi {
    private static IInternals internals = null;

    public static final List<ItemStack> DEXTERITY_ITEMSTACKS = new ArrayList<>();
    public static final List<ITag<Item>> DEXTERITY_TAGS = new ArrayList<>();
    public static final Multimap<Food, BiConsumer<GluttonyEnchantEffect, ItemStack>> GLUTTONY_FOODS = HashMultimap.create();

    @CapabilityInject(IPlayerEnchant.class)
    public static Capability<IPlayerEnchant> playerEnchant;

    /**
     * Add to the Efficiency list for use in the Efficiency Enchant.
     * @param things Array of objects which are ItemStack for Items and Tags< Item >
     */
    public static void addToDexterityList(Object... things) {
        for (Object obj : things) {
            if (obj instanceof Item) {
                EnchantabilityApi.DEXTERITY_ITEMSTACKS.add(new ItemStack((Item) obj));
            } else if (obj instanceof Tag) {
                EnchantabilityApi.DEXTERITY_TAGS.add((ITag<Item>) obj);
            } else {
                ModRef.LOGGER.error("Tried to add an object to the dexterity list of wrong type");
            }
        }
    }

    /**
     * You can add your own foods with a special effect to the Mending Enchant food list.
     * Note this is initialized in {@link net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent}
     * @param food The ItemFood
     * @param action The action to take when the food is eaten, the item stack is provided so the meta can be accessed.
     */
    public static void addToMendingList(Food food, BiConsumer<GluttonyEnchantEffect, ItemStack> action) {
        EnchantabilityApi.GLUTTONY_FOODS.put(food, action);
    }

    public static void registerEnchantEffect(ResourceLocation name, Enchantment enchantment, IEffectSupplier effectSupplier) {
        internals.registerEnchantEffect(name, enchantment, effectSupplier);
    }

    public static <F extends IEnchantEffect, T extends Event> void registerEffectComponent(
            ResourceLocation name,
            Class<T> eventClass,
            IEffectComponent<F, T> component,
            Function<T, Collection<PlayerEntity>> playerGetter
    ) {
        internals.registerEffectComponent(name, eventClass, component, playerGetter);
    }

    public static List<IEffectSupplier> getEnchantEffects(Enchantment enchantment) {
        return internals.getEnchantEffects(enchantment);
    }

    public static IEffectSupplier getEnchantEffect(ResourceLocation name) {
        return internals.getEnchantEffect(name);
    }

    /**
     * @Deprecated Use the calls from this API class directly
     * This will be removed in the future
     */
    @Deprecated
    public static IInternals getInternals() {
        if (internals == null) {
            throw new NullPointerException("EnchantabilityAPI: The instance was accessed before it was initialised.");
        }
        return internals;
    }

    /**
     * Internal use only. Do not use.
     */
    public static void setInternals(IInternals inst) {
        if (internals == null) {
            internals = inst;
        }
    }



    public interface IInternals {

        void registerEnchantEffect(ResourceLocation name, Enchantment enchantment, IEffectSupplier effectSupplier);

        <F extends IEnchantEffect, T extends Event> void registerEffectComponent(ResourceLocation name, Class<T> eventClass, IEffectComponent<F, T> component, Function<T, Collection<PlayerEntity>> playerGetter);

        List<IEffectSupplier> getEnchantEffects(Enchantment enchantment);

        IEffectSupplier getEnchantEffect(ResourceLocation name);
    }
}
