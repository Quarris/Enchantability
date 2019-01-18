package quarris.enchantability.mod.enchant.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import quarris.enchantability.api.EnchantabilityAPI;
import quarris.enchantability.api.enchant.AbstractEnchantEffect;

import javax.annotation.Nonnull;

public class EnchantEffectEfficiency extends AbstractEnchantEffect {

    @Override
    public void onItemCrafted(EntityPlayer player, ItemStack output, int tier) {
        if (!player.world.isRemote) {
            if (player.world.rand.nextInt(9-tier) == 0) {
                for (ItemStack is : EnchantabilityAPI.EFFICIENCY_ITEMSTACKS) {
                    if (OreDictionary.itemMatches(is, output, false)) {
                        ItemStack stack = output.copy();
                        stack.setCount(1);
                        EntityItem ei = new EntityItem(player.world, player.posX, player.posY, player.posZ, stack);
                        ei.setNoPickupDelay();
                        player.world.spawnEntity(ei);
                        if (player.world.rand.nextInt(6-tier) == 0) {
                            player.addExperience(tier);
                        }
                        return;
                    }
                }
                for (int id : OreDictionary.getOreIDs(output)) {
                    String name = OreDictionary.getOreName(id);
                    for (String s : EnchantabilityAPI.EFFICIENCY_OREDICT) {
                        if (name.equals(s)) {
                            ItemStack stack = output.copy();
                            stack.setCount(1);
                            EntityItem ei = new EntityItem(player.world, player.posX, player.posY, player.posZ, stack);
                            ei.setNoPickupDelay();
                            player.world.spawnEntity(ei);
                            if (player.world.rand.nextInt(6-tier) == 0) {
                                player.addExperience(tier);
                            }
                            return;
                        }
                    }
                }
            }
        }
    }

    @Nonnull
    @Override
    public Enchantment getEnchantment() {
        return Enchantments.EFFICIENCY;
    }
}
