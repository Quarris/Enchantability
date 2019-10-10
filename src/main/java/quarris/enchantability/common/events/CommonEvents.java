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
import quarris.enchantability.common.network.OpenEnderChestPacket;
import quarris.enchantability.common.network.PacketHandler;
import quarris.enchantability.common.util.ModRef;

@Mod.EventBusSubscriber(modid = ModRef.MOD_ID)
public class CommonEvents {

    @SubscribeEvent
    public static void rightClickBlock(PlayerInteractEvent.RightClickBlock e) {
        e.getPlayer().openContainer(new SimpleNamedContainerProvider((p_220114_1_, p_220114_2_, p_220114_3_) -> {
            return ChestContainer.createGeneric9X3(p_220114_1_, p_220114_2_, e.getPlayer().getInventoryEnderChest());
        }, new TranslationTextComponent("container.enderchest")));
    }

    @SubscribeEvent
    public static void openEnderChestContainer(PlayerContainerEvent.Open e) {
        if (e.getContainer() instanceof ChestContainer) {
            ChestContainer cont = (ChestContainer) e.getContainer();
            if (cont.getLowerChestInventory() instanceof EnderChestInventory) {
                PacketHandler.INSTANCE.sendTo(new OpenEnderChestPacket(), ((ServerPlayerEntity)e.getPlayer()).connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
            }
        }
    }


}
