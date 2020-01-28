package quarris.enchantability.api.enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

public interface IEnchantEffect extends INBTSerializable<CompoundNBT> {

    void onApplied();

    void onRemoved();

    Enchantment origin();

    int level();

    @Override
    default CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("Enchantment", this.origin().getRegistryName().toString());
        nbt.putInt("Level", this.level());
        nbt.putString("Name", this.getName().toString());
        return nbt;
    }

    @Override
    default void deserializeNBT(CompoundNBT nbt) {

    }

    ResourceLocation getName();
}
