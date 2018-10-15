package quarris.enchantability.mod.capability.player.enchant;

import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import quarris.enchantability.mod.capability.player.CapabilityHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerEnchProvider implements ICapabilitySerializable<NBTTagList> {

    protected IPlayerEnchHandler playerEnch;

    public PlayerEnchProvider(IPlayerEnchHandler playerEnch) {
        this.playerEnch = playerEnch;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityHandler.PLAYER_ENCHANT_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityHandler.PLAYER_ENCHANT_CAPABILITY) return (T) playerEnch;
        return null;
    }

    @Override
    public NBTTagList serializeNBT() {
        return this.playerEnch.serializeNBT();
    }

    @Override
    public void deserializeNBT(NBTTagList nbt) {
        this.playerEnch.deserializeNBT(nbt);
    }
}
