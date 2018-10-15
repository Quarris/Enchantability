package quarris.enchantability.api.enchant;

import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

import javax.annotation.Nonnull;

/**
 * Implement this interface to add you own effect to the mod.
 */
public interface IEnchantEffect {

    void onItemCrafted(EntityPlayer player, ItemStack output, int tier);

    void onLivingUpdate(EntityPlayer player, int tier);

    /**
     * Called
     * @param player The player which the effect is applied to.
     * @param render The {@link RenderPlayer} class which renders the player.
     * @param tier The tier of the enchantment.
     * @return true if the player should not be rendered.
     */
    boolean onRenderPlayer(EntityPlayer player, RenderPlayer render, int tier);

    /**
     * Called at the end of every server tick for the player which has the effect.
     * @param tier The tier of the enchantment.
     * @param player The player which the effect is applied to.
     */
    void onTick(EntityPlayer player, int tier);


    /**
     * Called when player attacks an entity.
     * @see AttackEntityEvent for the Event used.
     * @see EntityPlayer#attackTargetEntityWithCurrentItem(Entity) for the usage of the event.
     * @param player The player which the effect is applied to.
     * @param target The entity which the player has attacked.
     * @param tier The tier of the enchantment.
     */
    boolean onPlayerAttack(EntityPlayer player, Entity target, int tier);

    /**
     * Called when player is about to take damage. Armor and potion effects are already applied (including armor durability taking damage).
     * @see IEnchantEffect#onPlayerHurt(EntityPlayer, DamageSource, float, int) for an alternative.
     * @see LivingDamageEvent for the Event used.
     * @see EntityLivingBase#damageEntity(DamageSource, float) for the usage of the event.
     * @param player The player which the effect is applied to.
     * @param source
     * @param amount
     * @param tier The tier of the enchantment.
     */
    float onPlayerDamageTaken(EntityPlayer player, DamageSource source, float amount, int tier);

    /**
     * Called when player is about to be hurt. Applied before any armor or potion effects take place.
     * @see IEnchantEffect#onPlayerDamageTaken(EntityPlayer, DamageSource, float, int) for an alternative.
     * @see LivingHurtEvent for the Event used.
     * @see EntityLivingBase#damageEntity(DamageSource, float) for the entity usage of the event.
     * @param player TThe player which the effect is applied to.
     * @param source The {@link DamageSource} for the damage taken. Use {@link DamageSource#getTrueSource()} to access the the entity that dealt the damage (null if it was not dealt by an entity).
     * @param amount The amount of damage applied to the player.
     * @param tier The tier of the enchantment.
     * @return The new amount of damage to apply to the player. Returning <= 0 will cancel the attack.
     */
    float onPlayerHurt(EntityPlayer player, DamageSource source, float amount, int tier);

    boolean onPlayerDeath(EntityPlayer player, int tier);

    void onRemove(EntityPlayer player, int tier);

    /**
     * This can be accessed via {@link Enchantments} class.
     * @return The enchantment required for the effect.
     */
    @Nonnull
    Enchantment getEnchantment();
}
