package quarris.enchantability.mod.capability.player.container;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.items.ItemStackHandler;
import quarris.enchantability.mod.capability.player.CapabilityHandler;
import quarris.enchantability.mod.capability.player.enchant.IPlayerEnchHandler;
import quarris.enchantability.mod.network.PacketHandler;
import quarris.enchantability.mod.network.PacketSendCapsToClients;

import javax.annotation.Nonnull;

import static sun.misc.Version.print;

public class EnchantItemHandler extends ItemStackHandler implements IEnchantItemHandler {

    public static final int ENCH_SLOTS = 4;
    private EntityPlayer player;

    public EnchantItemHandler() {
        this(null);
    }

    public EnchantItemHandler(EntityPlayer player) {
        super(ENCH_SLOTS);
        this.player = player;
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    @Override
    public void updateEnchants() {
        for (int slot = 0; slot < getSlots(); slot++) {
            ItemStack stack = getStackInSlot(slot);
            NBTTagList enchants = stack.serializeNBT().getCompoundTag("tag").getTagList("StoredEnchantments", 10);
            if (!stack.isEmpty() && enchants.tagCount() == 1) {
                IPlayerEnchHandler cap = player.getCapability(CapabilityHandler.PLAYER_ENCHANT_CAPABILITY, null);
                if (cap == null) return;
                Enchantment ench = Enchantment.getEnchantmentByID(enchants.getCompoundTagAt(0).getShort("id"));
                int level = enchants.getCompoundTagAt(0).getShort("lvl");
                if (level > 0 && cap.hasEnchant(ench) < level) {
                    cap.addEnchant(ench, level);
                    PacketHandler.INSTANCE.sendToAll(new PacketSendCapsToClients(player));
                }
            }
        }
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if (stack == null || stack.isEmpty() || !(stack.getItem() instanceof ItemEnchantedBook)) {
            return false;
        }
        NBTTagList enchants = stack.serializeNBT().getCompoundTag("tag").getTagList("StoredEnchantments", 10);
        return enchants.tagCount() == 1;
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
        if (this.isItemValid(stack)) {
            super.setStackInSlot(slot, stack);
        }
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (!this.isItemValid(stack)) return stack;
        return super.insertItem(slot, stack, simulate);
    }

    @Override
    public int getSlots() {
        return ENCH_SLOTS;
    }
}
