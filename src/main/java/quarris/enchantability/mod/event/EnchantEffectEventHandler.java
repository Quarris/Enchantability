package quarris.enchantability.mod.event;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.Sys;
import quarris.enchantability.api.enchant.EnchantEffectRegistry;
import quarris.enchantability.api.enchant.IEnchantEffect;
import quarris.enchantability.mod.capability.player.CapabilityHandler;
import quarris.enchantability.mod.capability.player.enchant.IPlayerEnchHandler;

import java.util.List;
import java.util.Random;

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
        System.out.println();
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
    public void handleEffectOnPlayerDeath(PlayerEvent.PlayerRespawnEvent e) {
        if (!e.isEndConquered()) {
            EntityPlayer player = e.player;
            IPlayerEnchHandler cap = player.getCapability(CapabilityHandler.PLAYER_ENCHANT_CAPABILITY, null);
            for (Pair<Enchantment, Integer> pair : cap.getEnchants()) {
                boolean shouldRemove = false;
                List<IEnchantEffect> effects = EnchantEffectRegistry.getEffectsFromEnchantment(pair.getLeft());
                for (IEnchantEffect effect : effects) {
                    if (effect.onPlayerDeath(player, pair.getRight())) {
                        shouldRemove = true;
                    }
                }
                if (shouldRemove) {
                    //cap.removeEnchant(pair.getLeft());
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
        e.setCanceled(shouldCancel);
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
