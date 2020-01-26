package quarris.enchantability.mod.common.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.Event;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ModUtil {

    private static final Map<Class<? extends Event>, Function<? extends Event, PlayerEntity>> EVENT_PLAYER_GETTER = new HashMap<>();

    public static void addEventPlayerGetter(Class<? extends Event> clazz, Function<? extends Event, PlayerEntity> getter) {
        EVENT_PLAYER_GETTER.put(clazz, getter);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Event> Function<T, PlayerEntity> getPlayerFromEvent(Class<T> event) {
        for (Map.Entry<Class<? extends Event>, Function<? extends Event, PlayerEntity>> entry : EVENT_PLAYER_GETTER.entrySet()) {
            if (entry.getKey().isAssignableFrom(event)) {
                return (Function<T, PlayerEntity>)entry.getValue();
            }
        }
        return null;
    }

    public static <T> void registerCap(Class<T> capClass) {
        CapabilityManager.INSTANCE.register(capClass, new Capability.IStorage<T>() {
            public INBT writeNBT(Capability<T> capability, T instance, Direction side) { return null; }
            public void readNBT(Capability<T> capability, T instance, Direction side, INBT nbt) { }
            }, () -> null);
    }
}
