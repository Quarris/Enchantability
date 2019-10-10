package quarris.enchantability.common.events;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkDirection;
import quarris.enchantability.common.network.OpenCloseEnderChestPacket;
import quarris.enchantability.common.network.PacketHandler;
import quarris.enchantability.common.util.ModRef;

@Mod.EventBusSubscriber(modid = ModRef.MOD_ID)
public class CommonEvents {

    @SubscribeEvent
    public static void openEnderChestContainer(PlayerContainerEvent.Open e) {
        if (e.getContainer() instanceof ChestContainer) {
            ChestContainer cont = (ChestContainer) e.getContainer();
            if (cont.getLowerChestInventory() instanceof EnderChestInventory) {
                PacketHandler.INSTANCE.sendTo(new OpenCloseEnderChestPacket(true), ((ServerPlayerEntity)e.getPlayer()).connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
            }
        }
    }

    @SubscribeEvent
    public static void closeEnderChestContainer(PlayerContainerEvent.Close e) {
        if (e.getContainer() instanceof ChestContainer) {
            ChestContainer cont = (ChestContainer) e.getContainer();
            if (cont.getLowerChestInventory() instanceof EnderChestInventory) {
                PacketHandler.INSTANCE.sendTo(new OpenCloseEnderChestPacket(false), ((ServerPlayerEntity)e.getPlayer()).connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
            }
        }
    }


}
