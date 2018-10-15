package quarris.enchantability.mod.capability.player.container;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import quarris.enchantability.mod.capability.player.CapabilityHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EnchantItemProvider implements ICapabilitySerializable<NBTTagCompound> {

    protected IEnchantItemHandler itemHandler;

    public EnchantItemProvider(IEnchantItemHandler itemHandler) {
        this.itemHandler = itemHandler;
    }
    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityHandler.ENCHANT_INVENTORY_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityHandler.ENCHANT_INVENTORY_CAPABILITY ? (T) itemHandler : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return itemHandler.serializeNBT();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        itemHandler.deserializeNBT(nbt);
    }
}
