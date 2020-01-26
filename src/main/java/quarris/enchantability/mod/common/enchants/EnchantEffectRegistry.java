package quarris.enchantability.mod.common.enchants;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import quarris.enchantability.api.enchants.IEnchantEffect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class EnchantEffectRegistry {

    public static final ListMultimap<Enchantment, BiFunction<Enchantment, Integer, IEnchantEffect>> BY_ENCHANTMENT = ArrayListMultimap.create();
    public static final Map<ResourceLocation, BiFunction<Enchantment, Integer, IEnchantEffect>> BY_NAME = new HashMap<>();

    public static void register(ResourceLocation name, Enchantment enchantment, BiFunction<Enchantment, Integer, IEnchantEffect> effect) {
        BY_ENCHANTMENT.put(enchantment, effect);
        BY_NAME.put(name, effect);
    }

    public static List<BiFunction<Enchantment, Integer, IEnchantEffect>> getEffects(Enchantment enchantment) {
        return BY_ENCHANTMENT.get(enchantment);
    }

    public static BiFunction<Enchantment, Integer, IEnchantEffect> getEffect(ResourceLocation name) {
        return BY_NAME.get(name);
    }
}
