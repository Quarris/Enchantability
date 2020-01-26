package quarris.enchantability.mod.common.enchants;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import quarris.enchantability.api.IEffectSupplier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnchantEffectRegistry {

    public static final ListMultimap<Enchantment, IEffectSupplier> BY_ENCHANTMENT = ArrayListMultimap.create();
    public static final Map<ResourceLocation, IEffectSupplier> BY_NAME = new HashMap<>();

    public static void register(ResourceLocation name, Enchantment enchantment, IEffectSupplier effect) {
        BY_ENCHANTMENT.put(enchantment, effect);
        BY_NAME.put(name, effect);
    }

    public static List<IEffectSupplier> getEffects(Enchantment enchantment) {
        return BY_ENCHANTMENT.get(enchantment);
    }

    public static IEffectSupplier getEffect(ResourceLocation name) {
        return BY_NAME.get(name);
    }
}
