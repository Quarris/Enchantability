package quarris.enchantability.mod.capability.player;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import quarris.enchantability.mod.capability.player.container.IEnchantItemHandler;
import quarris.enchantability.mod.capability.player.enchant.IPlayerEnchHandler;
import quarris.enchantability.mod.capability.player.enchant.PlayerEnchHandler;
import quarris.enchantability.mod.capability.player.container.EnchantItemHandler;

import javax.annotation.Nullable;


public class CapabilityHandler {

    @CapabilityInject(IEnchantItemHandler.class)
    public static Capability<IEnchantItemHandler> ENCHANT_INVENTORY_CAPABILITY;

    @CapabilityInject(IPlayerEnchHandler.class)
    public static Capability<IPlayerEnchHandler> PLAYER_ENCHANT_CAPABILITY;

    public static void register() {
        CapabilityManager.INSTANCE.register(IPlayerEnchHandler.class, new Capability.IStorage<IPlayerEnchHandler>() {

            @Override
            public NBTBase writeNBT(Capability<IPlayerEnchHandler> capability, IPlayerEnchHandler instance, EnumFacing side) {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<IPlayerEnchHandler> capability, IPlayerEnchHandler instance, EnumFacing side, NBTBase nbt) {
                NBTTagList tagList = (NBTTagList) nbt;
                for (NBTBase base : tagList) {
                    NBTTagCompound compound = (NBTTagCompound) base;
                    instance.addEnchant(Enchantment.getEnchantmentByLocation(compound.getString("enchant")), compound.getInteger("tier"));
                }
            }
        }, PlayerEnchHandler::new);

        CapabilityManager.INSTANCE.register(IEnchantItemHandler.class, new Capability.IStorage<IEnchantItemHandler>() {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<IEnchantItemHandler> capability, IEnchantItemHandler instance, EnumFacing side) {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<IEnchantItemHandler> capability, IEnchantItemHandler instance, EnumFacing side, NBTBase nbt) {
                instance.deserializeNBT((NBTTagCompound) nbt);
            }
        }, EnchantItemHandler::new);
    }
}
