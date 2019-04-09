package quarris.enchantability.api.enchant;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Implement this interface to add you own effect to the mod.
 */
public interface IEnchantEffect {

    // UNUSED
    void onLootTableFillInventory(EntityPlayer player, LootTable table, IInventory inv, Random rand, LootContext context);

    float breakSpeed(EntityPlayer player, IBlockState state, BlockPos pos, float originalSpeed, int tier);

    void onItemCrafted(EntityPlayer player, ItemStack output, int tier);

    void onItemSmelted(EntityPlayer player, ItemStack output, int tier);

    void onLivingUpdate(EntityPlayer player, int tier);

	/**
	 * Called when the player finishes using an item.
	 * @see net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Finish
	 * @param player The player with the enchant.
	 * @param result The resulting ItemStack of the item being finished.
	 * @param tier The tier of the enchant.
	 * @return The ItemStack to be set as resultant.
	 */
    ItemStack onItemUseFinish(EntityPlayer player, ItemStack item, ItemStack result, int tier);

    /**
     * @see net.minecraftforge.event.entity.living.LivingExperienceDropEvent
     * @param player The player with the enchant
     * @param dropper The entity which died to drop the experience
     * @param originalXP The original XP count when the dropper died
     * @param droppedXP The already modified XP count when the dropper died (If, for example, modified by another mod)
     * @param tier The tier of the enchant
     * @return The new experience to be dropped.
     */
    int onExperienceDrop(EntityPlayer player, EntityLivingBase dropper, int originalXP, int droppedXP, int tier);

    boolean onProjectileImpact(EntityPlayer player, Entity projectile, int tier);

    /**
     * Called at the Render Player Event.
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

    boolean onExplosionStart(EntityPlayer player, Explosion explosion, int tier);

    void onExplosionDetonate(EntityPlayer player, Explosion explosion, List<Entity> affectedEntities, int tier);

    /**
     * Called when player attacks an entity.
     * @see AttackEntityEvent for the Event used.
     * @see EntityPlayer#attackTargetEntityWithCurrentItem(Entity) for the usage of the event.
     * @param player The player which the effect is applied to.
     * @param target The entity which the player has attacked.
     * @param tier The tier of the enchantment.
     * @return true if the event should be canceled, preventing the target getting hit.
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

    /**
     *
     * @see net.minecraftforge.event.entity.living.LivingDeathEvent
     * @param player The player
     * @param tier The tier of the enchantment
     * @return true if the player should not die
     */
    boolean onPlayerDeathPre(EntityPlayer player, int tier);

    void onPlayerDeath(EntityPlayer original, EntityPlayer newPlayer, int tier);

    /**
     * @see net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent
     * @param player The player
     * @param tier The tier of enchantment
     * @return true if the enchantment should be deleted
     */
    boolean onPlayerDeathPost(EntityPlayer player, int tier);

    void onAdded(EntityPlayer player, int tier);

    void onRemoved(EntityPlayer player, int tier);

    /**
     * List of enchantments which are required for the effect to take place.
     * @return List of enchantments.
     */
    default List<Enchantment> getEnchantments() {
        return Collections.singletonList(getEnchantment());
    }

    /**
     * This can be accessed via {@link Enchantments} class.
     * @return The enchantment required for the effect.
     */
    @Nonnull
    Enchantment getEnchantment();
}
