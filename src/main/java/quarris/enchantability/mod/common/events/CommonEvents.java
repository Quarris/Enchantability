package quarris.enchantability.mod.common.events;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.CombatEntry;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.network.NetworkDirection;
import org.apache.commons.lang3.tuple.Pair;
import quarris.enchantability.api.EnchantabilityApi;
import quarris.enchantability.api.capabilities.IPlayerEnchant;
import quarris.enchantability.mod.ModConfig;
import quarris.enchantability.mod.common.capabilities.PlayerEnchant;
import quarris.enchantability.mod.common.content.WitherHeartItem;
import quarris.enchantability.mod.common.network.EnderChestInteractPacket;
import quarris.enchantability.mod.common.network.PacketHandler;
import quarris.enchantability.mod.common.network.SyncClientPacket;
import quarris.enchantability.mod.common.util.ModRef;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = ModRef.ID)
public class CommonEvents {

    @SubscribeEvent
    public static void registerCommands(FMLServerStartingEvent event) {
        LiteralCommandNode<CommandSource> root = event.getCommandDispatcher().register(Commands.literal("ench")
                .requires(source -> source.hasPermissionLevel(2))
                .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.literal("extended")
                                .executes(source -> {
                                    ServerPlayerEntity player = EntityArgument.getPlayer(source, "player");
                                    player.getCapability(EnchantabilityApi.playerEnchant).ifPresent(cap -> source.getSource().sendFeedback(new StringTextComponent(player.getDisplayName().getFormattedText() + " has extended slots: " + cap.isExtended()), false));
                                    return 1;
                                })
                                .then(Commands.argument("extended", BoolArgumentType.bool())
                                        .executes(source -> {
                                            boolean set = BoolArgumentType.getBool(source, "extended");
                                            ServerPlayerEntity player = EntityArgument.getPlayer(source, "player");
                                            player.getCapability(EnchantabilityApi.playerEnchant).ifPresent(cap -> cap.setExtended(set));
                                            source.getSource().sendFeedback(new StringTextComponent("Set extended enchant inventory for " + player.getDisplayName().getFormattedText() + " to " + set), false);
                                            return 1;
                                        })))));

        event.getCommandDispatcher().register(Commands.literal("enchantability").requires(source -> source.hasPermissionLevel(2)).redirect(root));
    }

    @SubscribeEvent
    public static void attachCaps(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            event.addCapability(ModRef.createRes("enchant"), new PlayerEnchant((PlayerEntity) event.getObject()));
        }
    }

    // TODO put into same method
    @SubscribeEvent
    public static void openEnderChestContainer(PlayerContainerEvent.Open event) {
        if (event.getContainer() instanceof ChestContainer) {
            ChestContainer cont = (ChestContainer) event.getContainer();
            if (cont.getLowerChestInventory() instanceof EnderChestInventory) {
                PacketHandler.INSTANCE.sendTo(new EnderChestInteractPacket(true), ((ServerPlayerEntity) event.getPlayer()).connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
            }
        }
    }

    @SubscribeEvent
    public static void closeEnderChestContainer(PlayerContainerEvent.Close event) {
        if (event.getContainer() instanceof ChestContainer) {
            ChestContainer cont = (ChestContainer) event.getContainer();
            if (cont.getLowerChestInventory() instanceof EnderChestInventory) {
                PacketHandler.INSTANCE.sendTo(new EnderChestInteractPacket(false), ((ServerPlayerEntity) event.getPlayer()).connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
            }
        }
    }

    @SubscribeEvent
    public static void reloadTags(TagsUpdatedEvent event) {
        ModConfig.get().reloadTags();
    }

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.side == LogicalSide.SERVER) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.player;
            player.getCapability(EnchantabilityApi.playerEnchant).ifPresent(cap -> {
                if (cap.isDirty()) {
                    cap.updateEffects();
                    cap.markDirty(false);
                    PacketHandler.INSTANCE.sendTo(new SyncClientPacket(cap.serializeNBT()), player.connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
                }
            });
        }
    }

    @SubscribeEvent
    public static void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        event.getPlayer().getCapability(EnchantabilityApi.playerEnchant).ifPresent(cap ->
                PacketHandler.INSTANCE.sendTo(
                        new SyncClientPacket(cap.serializeNBT()),
                        ((ServerPlayerEntity) event.getPlayer()).connection.getNetworkManager(),
                        NetworkDirection.PLAY_TO_CLIENT)
        );
    }

    @SubscribeEvent
    public static void cloneCapabilities(PlayerEvent.Clone e) {
        try {
            IPlayerEnchant original = e.getOriginal().getCapability(EnchantabilityApi.playerEnchant).orElse(null);
            CompoundNBT nbt = original.serializeNBT();
            IPlayerEnchant clone = e.getPlayer().getCapability(EnchantabilityApi.playerEnchant).orElse(null);
            clone.deserializeNBT(nbt);
        } catch (Exception exp) {
            ModRef.LOGGER.warn("Failed to clone player " + e.getOriginal().getName(), exp);
        }
    }

    @SubscribeEvent
    public static void spawnWitherHeart(LivingDeathEvent event) {
        World world = event.getEntityLiving().world;
        if (world.isRemote())
            return;

        if (event.getEntityLiving().getType() == EntityType.WITHER) {
            WitherEntity wither = (WitherEntity) event.getEntityLiving();

            List<CombatEntry> combatants = ObfuscationReflectionHelper.getPrivateValue(CombatTracker.class, wither.getCombatTracker(), "field_94556_a");

            if (combatants == null)
                return;

            Set<Pair<UUID, String>> fighters = new HashSet<>();

            for (CombatEntry entry : combatants) {
                if (entry.isLivingDamageSrc() && entry.getDamageSrc().getTrueSource() instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) entry.getDamageSrc().getTrueSource();
                    if (player.getCapability(EnchantabilityApi.playerEnchant).orElse(null).isExtended())
                        continue;

                    fighters.add(Pair.of(player.getUniqueID(), player.getDisplayName().getFormattedText()));
                }
            }

            for (Pair<UUID, String> fighter : fighters) {
                ItemStack witherHeart = new ItemStack(WitherHeartItem.WITHER_HEART);
                witherHeart.getOrCreateTag().putUniqueId("Owner", fighter.getLeft());
                witherHeart.getTag().putString("OwnerName", fighter.getRight());
                ItemEntity entity = new ItemEntity(world, wither.getPosXRandom(1), wither.getPosYRandom(), wither.getPosZRandom(1), witherHeart);
                world.addEntity(entity);
            }
        }
    }

    @SubscribeEvent
    public static void blockWitherHeartPickup(EntityItemPickupEvent event) {
        if (event.getPlayer().world.isRemote)
            return;

        ItemStack item = event.getItem().getItem();

        if (item.getItem() != WitherHeartItem.WITHER_HEART)
            return;

        if (item.getTag() == null || event.getPlayer().getUniqueID().equals(item.getTag().getUniqueId("Owner")))
            return;

        event.setCanceled(true);
    }


}
