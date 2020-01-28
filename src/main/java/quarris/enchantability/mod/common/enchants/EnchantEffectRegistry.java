package quarris.enchantability.mod.common.enchants;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Table;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import quarris.enchantability.api.EnchantabilityApi;
import quarris.enchantability.api.IEffectComponent;
import quarris.enchantability.api.IEffectSupplier;
import quarris.enchantability.api.enchants.IEnchantEffect;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class EnchantEffectRegistry {

    public static final ListMultimap<Enchantment, IEffectSupplier> BY_ENCHANTMENT = ArrayListMultimap.create();
    public static final Map<ResourceLocation, IEffectSupplier> BY_NAME = new HashMap<>();
    public static final Table<ResourceLocation, Class<? extends Event>, IEffectComponent<? extends IEnchantEffect, ? extends Event>> COMPONENTS = HashBasedTable.create();

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

    public static <F extends IEnchantEffect, T extends Event> void registerComponent(ResourceLocation name, Class<T> eventClass, IEffectComponent<F, T> component, Function<T, Collection<PlayerEntity>> playerGetter) {
        COMPONENTS.put(name, eventClass, component);

        MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, false, eventClass, new Consumer<T>() {
            private Function<T, Collection<PlayerEntity>> getPlayer = playerGetter;

            @SuppressWarnings("unchecked")
            @Override
            public void accept(T event) {
                Collection<PlayerEntity> players = getPlayer.apply(event);
                if (players != null) {

                    players.stream().filter(Objects::nonNull).forEach(player -> {
                            player.getCapability(EnchantabilityApi.playerEnchant).ifPresent(cap -> {
                                if (event instanceof ExplosionEvent.Detonate)
                                    System.out.println(cap.getEnchants());
                                for (IEnchantEffect effect : cap.getEnchants()) {
                                    if (effect.getName().equals(name)) {
                                        for (Map.Entry<Class<? extends Event>, IEffectComponent<? extends IEnchantEffect, ? extends Event>> entry : COMPONENTS.row(name).entrySet()) {
                                            if (entry.getKey().isAssignableFrom(event.getClass())) {
                                                IEffectComponent<F, T> comp = (IEffectComponent<F, T>) COMPONENTS.get(effect.getName(), entry.getKey());
                                                if (comp != null) {
                                                    comp.run((F) effect, event);
                                                }
                                            }
                                        }
                                    }
                                }
                            });}
                    );
                }
            }
        });
    }
}
