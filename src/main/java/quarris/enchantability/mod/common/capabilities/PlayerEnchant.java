package quarris.enchantability.mod.common.capabilities;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import quarris.enchantability.api.EnchantabilityApi;
import quarris.enchantability.api.IEffectSupplier;
import quarris.enchantability.api.capabilities.IPlayerEnchant;
import quarris.enchantability.api.enchants.IEnchantEffect;
import quarris.enchantability.mod.common.enchants.EnchantEffectRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerEnchant extends ItemStackHandler implements IPlayerEnchant {

    public static final int ENCHANT_SLOT_SIZE = 4;
    public static final int EXTENDED_ENCHANT_SLOT_SIZE = ENCHANT_SLOT_SIZE + 1;
    private final List<IEnchantEffect> effects;
    public PlayerEntity player;
    public boolean isExtended;
    private boolean dirty;

    public PlayerEnchant(PlayerEntity player) {
        super(ENCHANT_SLOT_SIZE);
        this.effects = new ArrayList<>();
        this.player = player;
        this.isExtended = false;
    }

    @Override
    public List<IEnchantEffect> getEnchants() {
        return this.effects;
    }

    @Override
    public boolean hasEnchant(ResourceLocation name) {
        return this.getEnchants().stream().anyMatch(effect -> name.equals(effect.getName()));
    }

    @Override
    public IEnchantEffect getEnchant(ResourceLocation name) {
        return this.getEnchants().stream().filter(effect -> name.equals(effect.getName())).findFirst().orElse(null);
    }

    @Override
    public PlayerEntity getPlayer() {
        return this.player;
    }

    @Override
    public boolean isDirty() {
        return this.dirty;
    }

    @Override
    public void markDirty(boolean dirty) {
        this.dirty = dirty;
    }

    @Override
    public ItemStack setExtended(boolean extended) {
        if (this.isExtended == extended)
            return ItemStack.EMPTY;

        this.markDirty(true);

        this.isExtended = extended;
        NonNullList<ItemStack> current = this.stacks;
        if (extended) {
            this.setSize(EXTENDED_ENCHANT_SLOT_SIZE);
        } else {
            this.setSize(ENCHANT_SLOT_SIZE);
        }

        int currSize = Math.min(current.size(), this.stacks.size());
        for (int i = 0; i < currSize; i++) {
            this.setStackInSlot(i, current.get(i));
        }

        return extended ? ItemStack.EMPTY : current.get(4);
    }

    @Override
    public boolean isExtended() {
        return this.isExtended;
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        if (stack.isEmpty() || !(stack.getItem() instanceof EnchantedBookItem)) {
            return false;
        }
        ListNBT enchants = EnchantedBookItem.getEnchantments(stack);
        return enchants.size() >= 1;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == EnchantabilityApi.playerEnchant ? LazyOptional.of(() -> (T) this) : LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = super.serializeNBT();
        this.serializeEffects(nbt);
        nbt.putBoolean("Extended", this.isExtended);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.deserializeEffects(nbt);
        this.isExtended = nbt.getBoolean("Extended");
        super.deserializeNBT(nbt);
    }

    @Override
    public CompoundNBT serializeEffects(CompoundNBT nbt) {
        ListNBT list = new ListNBT();
        for (IEnchantEffect effect : this.getEnchants()) {
            list.add(effect.serializeNBT());
        }
        nbt.put("Effects", list);
        return nbt;
    }

    @Override
    public void deserializeEffects(CompoundNBT nbt) {
        this.getEnchants().clear();
        List<CompoundNBT> tags = nbt.getList("Effects", Constants.NBT.TAG_COMPOUND).stream().map(tag -> (CompoundNBT) tag).collect(Collectors.toList());
        for (CompoundNBT tag : tags) {
            ResourceLocation name = new ResourceLocation(tag.getString("Name"));
            int level = tag.getInt("Level");
            Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(tag.getString("Enchantment")));
            IEffectSupplier supplier = EnchantEffectRegistry.getEffect(name);
            if (supplier != null) {
                IEnchantEffect effect = supplier.create(this.getPlayer(), enchantment, level);
                effect.deserializeNBT(tag);
                this.getEnchants().add(effect);
            }
        }
    }
}
