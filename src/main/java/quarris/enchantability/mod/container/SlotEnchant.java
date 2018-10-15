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

    public SlotEnchant(EntityPlayer player, IEnchantItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
        this.player = player;
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return ((IEnchantItemHandler) getItemHandler()).isItemValid(stack);
    }

    @Override
    public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack) {
        IPlayerEnchHandler cap = player.getCapability(CapabilityHandler.PLAYER_ENCHANT_CAPABILITY, null);
        if (cap == null) return super.onTake(thePlayer, stack);
        NBTTagList enchants = stack.serializeNBT().getCompoundTag("tag").getTagList("StoredEnchantments", 10);
        Enchantment ench = Enchantment.getEnchantmentByID(enchants.getCompoundTagAt(0).getShort("id"));
        cap.removeEnchant(ench);
        updateEnchants();
        return super.onTake(thePlayer, stack);
    }

    @Override
    public void putStack(@Nonnull ItemStack stack) {
        super.putStack(stack);
        updateEnchants();
    }

    private void updateEnchants() {
        for (int slot = 0; slot < getItemHandler().getSlots(); slot++) {
            ItemStack stack = getItemHandler().getStackInSlot(slot);
            NBTTagList enchants = stack.serializeNBT().getCompoundTag("tag").getTagList("StoredEnchantments", 10);
            if (!stack.isEmpty() && enchants.tagCount() == 1) {
                IPlayerEnchHandler cap = player.getCapability(CapabilityHandler.PLAYER_ENCHANT_CAPABILITY, null);
                if (cap == null) return;
                Enchantment ench = Enchantment.getEnchantmentByID(enchants.getCompoundTagAt(0).getShort("id"));
                int level = enchants.getCompoundTagAt(0).getShort("lvl");
                if (level > 0 && cap.hasEnchant(ench) < level); {
                    cap.addEnchant(ench, level);
                    PacketHandler.INSTANCE.sendToAll(new PacketSendCapsToClients(player));
                }
            }
        }
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }
}
