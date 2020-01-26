package quarris.enchantability.api;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.eventbus.api.Event;
import quarris.enchantability.api.capabilities.IPlayerEnchant;
import quarris.enchantability.api.enchants.IEnchantEffect;

import java.util.ArrayList;
import java.util.List;

public class EnchantabilityApi {
    private static IInternals instance = null;

    public static final List<ItemStack> EFFICIENCY_ITEMSTACKS = new ArrayList<>();
    public static final List<String> EFFICIENCY_OREDICT = new ArrayList<>();
    //public static final Multimap<Food, MendingAction> MENDING_EFFECTS = HashMultimap.create();

    /**
     * Use this to interact with the API. Note this is initialised in {@link net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent}
     * so make sure you use it after that or you set my mod as a dependency before yours.
     * @return the instance of {@link IInternals} used for the api.
     */
    public static IInternals getInstance() {
        if (instance == null) {
            throw new NullPointerException("EnchantabilityAPI: The instance was accessed before it was initialised.");
        }
        return instance;
    }

    @CapabilityInject(IPlayerEnchant.class)
    public static Capability<IPlayerEnchant> playerEnchant;

    /**
     * Internal use only. Do not use.
     */
    public static void setInstance(IInternals inst) {
        if (instance == null) {
            instance = inst;
        }
    }
    /**
     * @see #getInstance()
     */
    public interface IInternals {

        /**
         * Adds an {@link IEnchantEffect} to the registry.
         * You can implement your own {@link IEnchantEffect} or extend {@link AbstractEnchantEffect} and override the needed methods (recommended).
         *
         * @param effect The effect itself.
         */
        void registerEnchantEffect(ResourceLocation name, Enchantment enchantment, IEffectSupplier effectSupplier);

        <F extends IEnchantEffect, T extends Event> void registerEffectComponent(ResourceLocation name, Class<T> eventClass, IEffectComponent<F, T> component);

        List<IEffectSupplier> getEnchantEffects(Enchantment enchantment);

        IEffectSupplier getEnchantEffect(ResourceLocation name);

        /**
         * You can add your own foods with a special effect to the Mending Enchant food list.
         * Note this is initialized in {@link net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent}
         * @param food The ItemFood
         * @param action The action to take when the food is eaten, the item stack is provided so the meta can be accessed.
         */
        //void addToMendingList(Food food, MendingAction action);

        /**
         * Add to the Efficiency list for use in the Efficiency Enchant.
         * @param things Array of objects which are ItemStack for Items and String for Oredicts
         */
        //void addToEfficiencyList(Object... things);


    }
}
