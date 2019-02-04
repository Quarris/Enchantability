package quarris.enchantability.mod.enchant.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.world.GameType;
import quarris.enchantability.api.enchant.AbstractEnchantEffect;
import quarris.enchantability.mod.capability.player.CapabilityHandler;
import quarris.enchantability.mod.capability.player.enchant.IPlayerEnchHandler;

import javax.annotation.Nonnull;

public class EnchantEffectBinding extends AbstractEnchantEffect {

    @Override
    public boolean onPlayerDeathPre(EntityPlayer player, int tier) {
        if (player.isAllowEdit() && !player.isSpectator() && !player.isCreative())
            player.setGameType(GameType.SPECTATOR);
        return false;
    }

    @Override
    public void onPlayerDeath(EntityPlayer original, EntityPlayer newPlayer, int tier) {
        original.world.removeEntity(original);
    }

    @Override
    public boolean onPlayerDeathPost(EntityPlayer player, int tier) {
        player.setGameType(GameType.SURVIVAL);
        IPlayerEnchHandler cap = player.getCapability(CapabilityHandler.PLAYER_ENCHANT_CAPABILITY, null);
        if (cap != null) {
            cap.removeEnchant(this.getEnchantment());
        }
        return true;
    }

    @Nonnull
    @Override
    public Enchantment getEnchantment() {
        return Enchantments.BINDING_CURSE;
    }
}
