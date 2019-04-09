package quarris.enchantability.mod.event;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;
import quarris.enchantability.api.enchant.EnchantEffectRegistry;
import quarris.enchantability.api.enchant.IEnchantEffect;
import quarris.enchantability.mod.capability.player.CapabilityHandler;
import quarris.enchantability.mod.capability.player.enchant.IPlayerEnchHandler;

import java.util.ArrayList;
import java.util.List;

public class EnchantEffectEventHandler {

    @SubscribeEvent
    public void handleEffectBreakSpeed(BreakSpeed e) {
        EntityPlayer player = e.getEntityPlayer();
        IPlayerEnchHandler cap = player.getCapability(CapabilityHandler.PLAYER_ENCHANT_CAPABILITY, null);
        for (Pair<Enchantment, Integer> pair : cap.getEnchants()) {
            List<IEnchantEffect> effects = EnchantEffectRegistry.getEffectsFromEnchantment(pair.getLeft());
            for (IEnchantEffect effect : effects) {
                float out = effect.breakSpeed(player, e.getState(), e.getPos(), e.getOriginalSpeed(), pair.getRight());
                if (out != e.getOriginalSpeed()) {
                    if (out < 0) {
                        e.setCanceled(true);
                    }
                    else {
                        e.setNewSpeed(out);
                        break;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void handleEffectOnItemUseFinish(LivingEntityUseItemEvent.Finish e) {
    	if (e.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)e.getEntityLiving();
			IPlayerEnchHandler cap = player.getCapability(CapabilityHandler.PLAYER_ENCHANT_CAPABILITY, null);
			for (Pair<Enchantment, Integer> pair : cap.getEnchants()) {
				List<IEnchantEffect> effects = EnchantEffectRegistry.getEffectsFromEnchantment(pair.getLeft());
				for (IEnchantEffect effect : effects) {
					e.setResultStack(effect.onItemUseFinish(player, e.getItem(), e.getResultStack(), pair.getRight()));
				}
			}
		}
    }

    @SubscribeEvent
    public void handleEffectOnExplosionStart(ExplosionEvent.Start e) {
        for (EntityPlayer player : e.getExplosion().getPlayerKnockbackMap().keySet()) {
            IPlayerEnchHandler cap = player.getCapability(CapabilityHandler.PLAYER_ENCHANT_CAPABILITY, null);
            boolean shouldCancel = false;
            for (Pair<Enchantment, Integer> pair : cap.getEnchants()) {
                List<IEnchantEffect> effects = EnchantEffectRegistry.getEffectsFromEnchantment(pair.getLeft());
                for (IEnchantEffect effect : effects) {
                    if (effect.onExplosionStart(player, e.getExplosion(), pair.getRight())) {
                        shouldCancel = true;
                    }
                }
            }
            if (shouldCancel) e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void handleEffectOnProjectileImpact(ProjectileImpactEvent e) {
        if (e.getRayTraceResult().entityHit instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)e.getRayTraceResult().entityHit;
            IPlayerEnchHandler cap = player.getCapability(CapabilityHandler.PLAYER_ENCHANT_CAPABILITY, null);
            boolean shouldCancel = false;
            for (Pair<Enchantment, Integer> pair : cap.getEnchants()) {
                List<IEnchantEffect> effects = EnchantEffectRegistry.getEffectsFromEnchantment(pair.getLeft());
                for (IEnchantEffect effect : effects) {
                    if (effect.onProjectileImpact(player, e.getEntity(), pair.getRight())) {
                        shouldCancel = true;
                    }
                }
            }
            if (shouldCancel) e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void handleEffectOnExplosionDetonate(ExplosionEvent.Detonate e) {
        for (Entity entity : e.getAffectedEntities()) {
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;
                IPlayerEnchHandler cap = player.getCapability(CapabilityHandler.PLAYER_ENCHANT_CAPABILITY, null);
                for (Pair<Enchantment, Integer> pair : cap.getEnchants()) {
                    List<IEnchantEffect> effects = EnchantEffectRegistry.getEffectsFromEnchantment(pair.getLeft());
                    for (IEnchantEffect effect : effects) {
                        effect.onExplosionDetonate(player, e.getExplosion(), e.getAffectedEntities(), pair.getRight());
                    }
                }

            }
        }
    }

    @SubscribeEvent
    public void handleEffectOnPlayerDamageTaken(LivingDamageEvent e) {
        if (e.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) e.getEntityLiving();
            IPlayerEnchHandler cap = player.getCapability(CapabilityHandler.PLAYER_ENCHANT_CAPABILITY, null);
            for (Pair<Enchantment, Integer> pair : cap.getEnchants()) {
                List<IEnchantEffect> effects = EnchantEffectRegistry.getEffectsFromEnchantment(pair.getLeft());
                for (IEnchantEffect effect : effects) {
                    float amount = effect.onPlayerDamageTaken(player, e.getSource(), e.getAmount(), pair.getRight());
                    if (amount <= 0) {
                        e.setCanceled(true);
                        return;
                    }
                    e.setAmount(amount);
                }
            }
        }
    }

    @SubscribeEvent
    public void handleEffectOnPlayerHurt(LivingHurtEvent e) {
        if (e.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) e.getEntityLiving();
            IPlayerEnchHandler cap = player.getCapability(CapabilityHandler.PLAYER_ENCHANT_CAPABILITY, null);
            for (Pair<Enchantment, Integer> pair : cap.getEnchants()) {
                List<IEnchantEffect> effects = EnchantEffectRegistry.getEffectsFromEnchantment(pair.getLeft());
                for (IEnchantEffect effect : effects) {
                    float amount = effect.onPlayerHurt(player, e.getSource(), e.getAmount(), pair.getRight());
                    if (amount <= 0) {
                        e.setCanceled(true);
                        return;
                    }
                    e.setAmount(amount);
                }
            }
        }
    }

    @SubscribeEvent
    public void handleEffectOnPlayerAttack(AttackEntityEvent e) {
        EntityPlayer player = e.getEntityPlayer();
        IPlayerEnchHandler cap = player.getCapability(CapabilityHandler.PLAYER_ENCHANT_CAPABILITY, null);
        for (Pair<Enchantment, Integer> pair : cap.getEnchants()) {
            List<IEnchantEffect> effects = EnchantEffectRegistry.getEffectsFromEnchantment(pair.getLeft());
            for (IEnchantEffect effect : effects) {
                e.setCanceled(effect.onPlayerAttack(player, e.getTarget(), pair.getRight()));
            }
        }
    }

    @SubscribeEvent
    public void handleEffectOnPlayerDeathPre(LivingDeathEvent e) {
        if (e.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) e.getEntityLiving();
            IPlayerEnchHandler cap = player.getCapability(CapabilityHandler.PLAYER_ENCHANT_CAPABILITY, null);
            for (Pair<Enchantment, Integer> pair : cap.getEnchants()) {
                List<IEnchantEffect> effects = EnchantEffectRegistry.getEffectsFromEnchantment(pair.getLeft());
                for (IEnchantEffect effect : effects) {
                    e.setCanceled(effect.onPlayerDeathPre(player, pair.getRight()));
                }
            }
        }
    }

    public static void handleEffectOnPlayerDeath(Clone e) {
        if (e.isWasDeath()) {
            EntityPlayer player = e.getEntityPlayer();
            IPlayerEnchHandler cap = player.getCapability(CapabilityHandler.PLAYER_ENCHANT_CAPABILITY, null);
            if (cap != null) {
                for (Pair<Enchantment, Integer> pair : cap.getEnchants()) {
                    List<IEnchantEffect> effects = EnchantEffectRegistry.getEffectsFromEnchantment(pair.getLeft());
                    for (IEnchantEffect effect : effects) {
                        effect.onPlayerDeath(e.getOriginal(), player, pair.getRight());
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void handleEffectOnPlayerDeathPost(PlayerEvent.PlayerRespawnEvent e) {
        List<Enchantment> toDelete = new ArrayList<>();
        if (!e.isEndConquered()) {
            EntityPlayer player = e.player;
            IPlayerEnchHandler cap = player.getCapability(CapabilityHandler.PLAYER_ENCHANT_CAPABILITY, null);
            if (cap != null) {
                for (Pair<Enchantment, Integer> pair : cap.getEnchants()) {
                    List<IEnchantEffect> effects = EnchantEffectRegistry.getEffectsFromEnchantment(pair.getLeft());
                    for (IEnchantEffect effect : effects) {
                        if (effect.onPlayerDeathPost(player, pair.getRight())) {
                            toDelete.add(pair.getLeft());
                        }
                    }
                }
                for (Enchantment ench : toDelete) {
                    cap.removeEnchant(ench);
                }
            }
        }
    }

    @SubscribeEvent
    public void handleEffectOnItemCrafted(PlayerEvent.ItemCraftedEvent e) {
        EntityPlayer player = e.player;
        IPlayerEnchHandler cap = player.getCapability(CapabilityHandler.PLAYER_ENCHANT_CAPABILITY, null);
        for (Pair<Enchantment, Integer> pair : cap.getEnchants()) {
            List<IEnchantEffect> effects = EnchantEffectRegistry.getEffectsFromEnchantment(pair.getLeft());
            for (IEnchantEffect effect : effects) {
                effect.onItemCrafted(player, e.crafting, pair.getRight());
            }
        }
    }

    @SubscribeEvent
    public void handleEffectOnItemSmelted(PlayerEvent.ItemSmeltedEvent e) {
        EntityPlayer player = e.player;
        IPlayerEnchHandler cap = player.getCapability(CapabilityHandler.PLAYER_ENCHANT_CAPABILITY, null);
        for (Pair<Enchantment, Integer> pair : cap.getEnchants()) {
            List<IEnchantEffect> effects = EnchantEffectRegistry.getEffectsFromEnchantment(pair.getLeft());
            for (IEnchantEffect effect : effects) {
                effect.onItemSmelted(player, e.smelting, pair.getRight());
            }
        }
    }

    @SubscribeEvent
    public void handleEffectOnExperienceDrop(LivingExperienceDropEvent e) {
        EntityPlayer player = e.getAttackingPlayer();
        if (player != null) {
            EntityLivingBase dropper = e.getEntityLiving();
            int originalXP = e.getOriginalExperience();
            IPlayerEnchHandler cap = player.getCapability(CapabilityHandler.PLAYER_ENCHANT_CAPABILITY, null);
            for (Pair<Enchantment, Integer> pair : cap.getEnchants()) {
                List<IEnchantEffect> effects = EnchantEffectRegistry.getEffectsFromEnchantment(pair.getLeft());
                for (IEnchantEffect effect : effects) {
                    int droppedXP = e.getDroppedExperience();
                    e.setDroppedExperience(effect.onExperienceDrop(player, dropper, originalXP, droppedXP, pair.getRight()));
                }
            }
        }
    }

    @SubscribeEvent
    public void handleEffectOnLivingUpdate(LivingEvent.LivingUpdateEvent e) {
        if (e.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) e.getEntityLiving();
            IPlayerEnchHandler cap = player.getCapability(CapabilityHandler.PLAYER_ENCHANT_CAPABILITY, null);
            for (Pair<Enchantment, Integer> pair : cap.getEnchants()) {
                List<IEnchantEffect> effects = EnchantEffectRegistry.getEffectsFromEnchantment(pair.getLeft());
                for (IEnchantEffect effect : effects) {
                    effect.onLivingUpdate(player, pair.getRight());
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void handleEffectOnPlayerRender(RenderPlayerEvent.Pre e) {
        IPlayerEnchHandler cap = e.getEntityPlayer().getCapability(CapabilityHandler.PLAYER_ENCHANT_CAPABILITY, null);
        boolean shouldCancel = false;
        for (Pair<Enchantment, Integer> pair : cap.getEnchants()) {
            List<IEnchantEffect> effects = EnchantEffectRegistry.getEffectsFromEnchantment(pair.getLeft());
            for (IEnchantEffect effect : effects) {
                if (effect.onRenderPlayer(e.getEntityPlayer(), e.getRenderer(), pair.getRight())) {
                    shouldCancel = true;
                }
            }
        }
        if (shouldCancel) e.setCanceled(true);
    }

    @SubscribeEvent
    public void handleEffectOnTick(TickEvent.PlayerTickEvent e) {
        if (e.side.isServer() && e.phase == TickEvent.Phase.END) {
            IPlayerEnchHandler cap = e.player.getCapability(CapabilityHandler.PLAYER_ENCHANT_CAPABILITY, null);
            for (Pair<Enchantment, Integer> pair : cap.getEnchants()) {
                List<IEnchantEffect> effects = EnchantEffectRegistry.getEffectsFromEnchantment(pair.getLeft());
                for (IEnchantEffect effect : effects) {
                    effect.onTick(e.player, pair.getRight());
                }
            }
        }
    }
}
