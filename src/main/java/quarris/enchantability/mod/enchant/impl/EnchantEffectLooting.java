package quarris.enchantability.mod.enchant.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import quarris.enchantability.api.enchant.AbstractEnchantEffect;

import javax.annotation.Nonnull;
import java.util.Random;

public class EnchantEffectLooting extends AbstractEnchantEffect {

    @Nonnull
    @Override
    public Enchantment getEnchantment() {
        return Enchantments.LOOTING;
    }
}
