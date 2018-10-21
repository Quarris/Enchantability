package quarris.enchantability.mod.enchant.impl;

import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import quarris.enchantability.api.PotionEffectHelper;
import quarris.enchantability.api.enchant.AbstractEnchantEffect;
import quarris.enchantability.mod.network.PacketHandler;
import quarris.enchantability.mod.network.PacketSendCapsToClients;

import javax.annotation.Nonnull;

public class EnchantEffectVanishing extends AbstractEnchantEffect {

    @Override
    public void onRemoved(EntityPlayer player, int tier) {
        player.setInvisible(false);
    }

    @Override
    public boolean onRenderPlayer(EntityPlayer player, RenderPlayer render, int tier) {
        player.setInvisible(true);
        return true;
    }

    @Override
    public void onTick(EntityPlayer player, int tier) {
    }

    @Nonnull
    @Override
    public Enchantment getEnchantment() {
        return Enchantments.VANISHING_CURSE;
    }
}
