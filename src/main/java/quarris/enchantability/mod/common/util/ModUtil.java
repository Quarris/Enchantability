package quarris.enchantability.mod.common.util;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;

import java.util.Random;

public class ModUtil {

    public static final Random RANDOM = new Random();

    public static <T> void registerCap(Class<T> capClass) {
        CapabilityManager.INSTANCE.register(capClass, new Capability.IStorage<T>() {
            public INBT writeNBT(Capability<T> capability, T instance, Direction side) { return null; }
            public void readNBT(Capability<T> capability, T instance, Direction side, INBT nbt) { }
            }, () -> null);
    }
}
