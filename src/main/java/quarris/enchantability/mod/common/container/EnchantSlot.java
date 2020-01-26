package quarris.enchantability.mod.common.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import quarris.enchantability.api.EnchantabilityApi;
import quarris.enchantability.api.capabilities.IPlayerEnchant;

import javax.annotation.Nonnull;

public class EnchantSlot extends SlotItemHandler {
    private PlayerEntity player;
    private IPlayerEnchant enchant;

    public EnchantSlot(PlayerEntity player, IPlayerEnchant enchant, int index, int xPosition, int yPosition) {
        super(enchant, index, xPosition, yPosition);
        this.player = player;
        this.enchant = enchant;
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return this.getItemHandler().isItemValid(this.slotNumber, stack);
    }

    @Override
    public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
        this.player.getCapability(EnchantabilityApi.playerEnchant).ifPresent(cap -> cap.markDirty(true));
        return super.onTake(thePlayer, stack);
    }

    @Override
    public void putStack(@Nonnull ItemStack stack) {
        this.player.getCapability(EnchantabilityApi.playerEnchant).ifPresent(cap -> cap.markDirty(true));
        super.putStack(stack);
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }
}
