package quarris.enchantability.mod.capability.player.container;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.items.IItemHandlerModifiable;
import quarris.enchantability.mod.capability.player.CapabilityHandler;
import quarris.enchantability.mod.capability.player.IModCapability;
import quarris.enchantability.mod.capability.player.enchant.IPlayerEnchHandler;
import quarris.enchantability.mod.network.PacketHandler;
import quarris.enchantability.mod.network.PacketSendCapsToClients;

public interface IEnchantItemHandler extends IItemHandlerModifiable, IModCapability {

    void updateEnchants();

    boolean isItemValid(ItemStack stack);

    NBTTagCompound serializeNBT();

    void deserializeNBT(NBTTagCompound nbt);
}
