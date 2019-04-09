package quarris.enchantability.mod.capability.player.container;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.IItemHandlerModifiable;
import quarris.enchantability.mod.capability.player.IModCapability;

public interface IEnchantItemHandler extends IItemHandlerModifiable, IModCapability {

    void updateEnchants();

    boolean isItemValid(ItemStack stack);

    NBTTagCompound serializeNBT();

    void deserializeNBT(NBTTagCompound nbt);
}
