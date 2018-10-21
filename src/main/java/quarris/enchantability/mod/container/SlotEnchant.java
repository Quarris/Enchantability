package quarris.enchantability.mod.container;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.items.SlotItemHandler;
import quarris.enchantability.mod.capability.player.CapabilityHandler;
import quarris.enchantability.mod.capability.player.container.IEnchantItemHandler;
import quarris.enchantability.mod.capability.player.enchant.IPlayerEnchHandler;
import quarris.enchantability.mod.network.PacketHandler;
import quarris.enchantability.mod.network.PacketSendCapsToClients;

import javax.annotation.Nonnull;

public class SlotEnchant extends SlotItemHandler {

    private EntityPlayer player;
    private IEnchantItemHandler handler;

    public SlotEnchant(EntityPlayer player, IEnchantItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
        this.player = player;
        this.handler = itemHandler;
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return ((IEnchantItemHandler) getItemHandler()).isItemValid(stack);
    }

    @Override
    public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack) {
        IPlayerEnchHandler cap = player.getCapability(CapabilityHandler.PLAYER_ENCHANT_CAPABILITY, null);
        if (cap == null) return super.onTake(thePlayer, stack);
        cap.markDirty();
        NBTTagList enchants = stack.serializeNBT().getCompoundTag("tag").getTagList("StoredEnchantments", 10);
        Enchantment ench = Enchantment.getEnchantmentByID(enchants.getCompoundTagAt(0).getShort("id"));
        //cap.removeEnchant(ench);
        return super.onTake(thePlayer, stack);
    }

    @Override
    public void putStack(@Nonnull ItemStack stack) {
        IPlayerEnchHandler cap = player.getCapability(CapabilityHandler.PLAYER_ENCHANT_CAPABILITY, null);
        cap.markDirty();
        super.putStack(stack);
    }



    @Override
    public int getSlotStackLimit() {
        return 1;
    }
}
