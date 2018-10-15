package quarris.enchantability.api.enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public class EnchantEffectRegistry {

    public static final Map<String, List<IEnchantEffect>> REGISTRY = new HashMap<>();

    private static void register(ResourceLocation location, IEnchantEffect effect) {
        String loc = location.toString();
        if (!REGISTRY.containsKey(loc)) {
            REGISTRY.put(loc, Collections.singletonList(effect));
        }
        else {
            REGISTRY.get(location).add(effect);
        }
    }

    public static void register(IEnchantEffect effect) {
        register(Objects.requireNonNull(effect.getEnchantment().getRegistryName()), effect);
    }

    public static List<IEnchantEffect> getEffectsFromEnchantment(Enchantment enchantment) {
        return REGISTRY.getOrDefault(Objects.requireNonNull(enchantment.getRegistryName()).toString(), Collections.emptyList());
    }
}
