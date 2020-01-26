package quarris.enchantability.mod.common.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class ModUtil {

    public static <T> void registerCap(Class<T> capClass) {
        CapabilityManager.INSTANCE.register(capClass, new Capability.IStorage<T>() {
            public INBT writeNBT(Capability<T> capability, T instance, Direction side) { return null; }
            public void readNBT(Capability<T> capability, T instance, Direction side, INBT nbt) { }
            }, () -> null);
    }

    public static ListNBT getEnchantmentNBTFromStack(ItemStack stack) {
        return stack.getTag() != null ? stack.getTag().getList("StoredEnchantments", 10) : null;
    }
}
