package quarris.enchantability.mod.common.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.ObjectHolder;
import quarris.enchantability.api.EnchantabilityApi;
import quarris.enchantability.api.capabilities.IPlayerEnchant;
import quarris.enchantability.mod.common.util.ModRef;

public class EnchContainer extends Container {

    @ObjectHolder(ModRef.MOD_ID + ":ench_type")
    public static ContainerType<EnchContainer> TYPE;

    public final IPlayerEnchant enchant;
    private final PlayerEntity player;
    public final PlayerInventory playerInv;
    public final EnderChestInventory enderInv;

    public EnchContainer(int id, PlayerEntity player) {
        super(TYPE, id);

        this.player = player;
        this.playerInv = player.inventory;
        this.enderInv = player.getInventoryEnderChest();
        this.enchant = player.getCapability(EnchantabilityApi.playerEnchant).orElse(null);

        // Enchant Inv Slots
        for (int slot = 0; slot < this.enchant.getSlots(); slot++) {
            this.addSlot(new EnchantSlot(player, this.enchant, slot, 8, 18 + slot * 18));
        }

        // Ender Inv Slots
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(enderInv, col + row * 9, 33 + col * 18, 18 + row * 18));
            }
        }

        // Player Inv Slots
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInv, 9 + row * 9 + col, 33 + col * 18, 85 + row * 18));
            }
        }

        // Hotbar slots
        for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++) {
            this.addSlot(new Slot(playerInv, hotbarSlot, 33 + hotbarSlot * 18, 143));
        }
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            int containerSlots = inventorySlots.size() - player.inventory.mainInventory.size();
            if (index < containerSlots) {
                if (!this.mergeItemStack(itemstack1, containerSlots, inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }


    @Override
    public boolean canInteractWith(PlayerEntity player) {
        return true;
    }
}
