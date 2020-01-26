package quarris.enchantability.mod.common.events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkDirection;
import quarris.enchantability.api.EnchantabilityApi;
import quarris.enchantability.mod.common.capabilities.PlayerEnchant;
import quarris.enchantability.mod.common.network.EnderChestInteractPacket;
import quarris.enchantability.mod.common.network.PacketHandler;
import quarris.enchantability.mod.common.network.SyncClientPacket;
import quarris.enchantability.mod.common.util.ModRef;

@Mod.EventBusSubscriber(modid = ModRef.MOD_ID)
public class CommonEvents {

    @SubscribeEvent
    public static void attachCaps(AttachCapabilitiesEvent<Entity> e) {
        if (e.getObject() instanceof PlayerEntity) {
            e.addCapability(ModRef.createRes("enchant"), new PlayerEnchant((PlayerEntity) e.getObject()));
        }
    }

    @SubscribeEvent
    public static void openEnderChestContainer(PlayerContainerEvent.Open e) {
        if (e.getContainer() instanceof ChestContainer) {
            ChestContainer cont = (ChestContainer) e.getContainer();
            if (cont.getLowerChestInventory() instanceof EnderChestInventory) {
                PacketHandler.INSTANCE.sendTo(new EnderChestInteractPacket(true), ((ServerPlayerEntity)e.getPlayer()).connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
            }
        }
    }

    @SubscribeEvent
    public static void closeEnderChestContainer(PlayerContainerEvent.Close e) {
        if (e.getContainer() instanceof ChestContainer) {
            ChestContainer cont = (ChestContainer) e.getContainer();
            if (cont.getLowerChestInventory() instanceof EnderChestInventory) {
                PacketHandler.INSTANCE.sendTo(new EnderChestInteractPacket(false), ((ServerPlayerEntity)e.getPlayer()).connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
            }
        }
    }

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent e) {
        if (e.phase == TickEvent.Phase.END && e.side == LogicalSide.SERVER) {
            ServerPlayerEntity player = (ServerPlayerEntity) e.player;
            player.getCapability(EnchantabilityApi.playerEnchant).ifPresent(cap -> {
                if (cap.isDirty()) {
                    cap.updateEffects();
                    cap.markDirty(false);
                    PacketHandler.INSTANCE.sendTo(new SyncClientPacket(cap.serializeEffects(new CompoundNBT())), player.connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
                }
            });
        }
        //e.player.getCapability(EnchantabilityApi.playerEnchant).ifPresent(cap -> System.out.println(cap.getEnchants()));

    }
}
