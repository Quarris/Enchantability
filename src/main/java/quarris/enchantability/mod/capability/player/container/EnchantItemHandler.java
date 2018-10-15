package quarris.enchantability.mod.capability.player.container;

import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

import static sun.misc.Version.print;

public class EnchantItemHandler extends ItemStackHandler implements IEnchantItemHandler {

    public static final int ENCH_SLOTS = 4;

    public EnchantItemHandler() {
        super(ENCH_SLOTS);
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
