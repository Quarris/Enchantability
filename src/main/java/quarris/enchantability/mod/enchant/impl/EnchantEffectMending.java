package quarris.enchantability.mod.enchant.impl;

import com.google.common.base.Predicate;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import quarris.enchantability.api.EnchantabilityAPI;
import quarris.enchantability.api.enchant.AbstractEnchantEffect;
import quarris.enchantability.api.enchant.mending.MendingAction;
import quarris.enchantability.api.enchant.mending.MendingResult;
import quarris.enchantability.mod.Enchantability;
import quarris.enchantability.mod.config.ConfigEnchants;
import quarris.enchantability.mod.utils.EnchantmentUtils;

import javax.annotation.Nonnull;
import java.util.List;

public class EnchantEffectMending extends AbstractEnchantEffect {

	public static void registerFoodActions() {
		// Pumpkin Pie
		if (ConfigEnchants.mendingEffects.pumpkinPieEffect) {
			EnchantabilityAPI.getInstance().addToMendingList((ItemFood) Items.PUMPKIN_PIE,
					new MendingAction((mendingResult -> {
						EntityPlayer player = mendingResult.player;
						List<EntityEnderman> list = player.world.getEntities(EntityEnderman.class, enderman -> enderman.getAttackTarget() == player);
						for (EntityEnderman enderman : list) {
							for (EntityAITasks.EntityAITaskEntry ai : enderman.targetTasks.taskEntries) {
								// v minecraft sucks v
								if (ai.priority == 1) {
									ReflectionHelper.setPrivateValue(EntityAINearestAttackableTarget.class, (EntityAINearestAttackableTarget) ai.action, null, 4);
									ai.action.resetTask();
								}
							}
						}
					}), 25));
		}

		// Rabbit Stew
		if (ConfigEnchants.mendingEffects.rabbitStewEffect) {
			EnchantabilityAPI.getInstance().addToMendingList((ItemFood) Items.RABBIT_STEW,
					new MendingAction(
							mendingResult -> mendingResult.player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 20 * 60, mendingResult.tier + 2)),
							20));
		}

		// Cookie
		if (ConfigEnchants.mendingEffects.cookieEffect) {
			EnchantabilityAPI.getInstance().addToMendingList((ItemFood) Items.COOKIE,
					new MendingAction(mendingResult -> {
						mendingResult.player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 20 * 30, 1));
						mendingResult.player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 * 15, 0));
					}, 10));
		}

		// Mushroom Stew
		if (ConfigEnchants.mendingEffects.mushroomStewEffect) {
			EnchantabilityAPI.getInstance().addToMendingList((ItemFood) Items.MUSHROOM_STEW,
					new MendingAction(
							mendingResult -> mendingResult.player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 20 * 60, 0)),
							10));
		}

		// Spider Eye
		if (ConfigEnchants.mendingEffects.spiderEyeEffect) {
			EnchantabilityAPI.getInstance().addToMendingList((ItemFood) Items.SPIDER_EYE,
					new MendingAction(
							mendingResult -> mendingResult.player.addPotionEffect(new PotionEffect(Enchantability.SPIDER_CLIMB, 20*45, 0)),
							30));
		}
	}

	@Override
	public ItemStack onItemUseFinish(EntityPlayer player, ItemStack item, ItemStack result, int tier) {
		if (item.getItem() instanceof ItemFood) {
			ItemFood food = (ItemFood) item.getItem();
			for (MendingAction action : EnchantabilityAPI.MENDING_EFFECTS.get(food)) {
				if (action.cost <= EnchantmentUtils.getPlayerXP(player)) {
					action.result.accept(new MendingResult(item, player, tier));
					EnchantmentUtils.addPlayerXP(player, -action.cost);
				}
			}
		}
		return result;
	}

	@Nonnull
	@Override
	public Enchantment getEnchantment() {
		return Enchantments.MENDING;
	}
}
