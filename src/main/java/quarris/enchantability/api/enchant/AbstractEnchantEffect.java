package quarris.enchantability.api.enchant;

import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

import javax.annotation.Nonnull;

public abstract class AbstractEnchantEffect implements IEnchantEffect {

    @Override
    public void onItemCrafted(EntityPlayer player, ItemStack output, int tier) {

    }

    @Override
    public void onLivingUpdate(EntityPlayer player, int tier) {

    }

    @Override
    public float onPlayerHurt(EntityPlayer player, DamageSource source, float amount, int tier) {
        return amount;
    }

    @Override
    public boolean onPlayerDeath(EntityPlayer player, int tier) {
        return false;
    }

    @Override
    public void onTick(EntityPlayer player, int tier) {
    }

    @Override
    public boolean onPlayerAttack(EntityPlayer player, Entity target, int tier) {
        return true;
    }

    @Override
    public float onPlayerDamageTaken(EntityPlayer player, DamageSource source, float amount, int tier) {
        return amount;
    }

    @Override
    public boolean onRenderPlayer(EntityPlayer player, RenderPlayer render, int tier) {
        return false;
    }

    @Override
    public void onRemove(EntityPlayer player, int tier) {

    }

    @Nonnull
    @Override
    public abstract Enchantment getEnchantment();
}
