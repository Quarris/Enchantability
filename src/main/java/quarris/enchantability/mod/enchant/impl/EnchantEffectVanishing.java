package quarris.enchantability.mod.enchant.impl;

import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import quarris.enchantability.api.enchant.AbstractEnchantEffect;

import javax.annotation.Nonnull;

public class EnchantEffectVanishing extends AbstractEnchantEffect {

    @Override
    public void onRemove(EntityPlayer player, int tier) {

    }

    @Override
    public boolean onRenderPlayer(EntityPlayer player, RenderPlayer render, int tier) {
        //player.setInvisible(true);
        return false;
    }

    @Nonnull
    @Override
    public Enchantment getEnchantment() {
        return Enchantments.VANISHING_CURSE;
    }
}
