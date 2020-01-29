package quarris.enchantability.mod.common.register;

import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import quarris.enchantability.mod.common.container.EnchContainer;
import quarris.enchantability.mod.common.content.AirWalkerBlock;
import quarris.enchantability.mod.common.util.ModRef;

@Mod.EventBusSubscriber(modid = ModRef.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonRegistry {

    @SubscribeEvent
    public static void registerContainer(RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().register(
                IForgeContainerType.create(((windowId, inv, data) -> new EnchContainer(windowId, inv.player))).setRegistryName("ench_type")
        );
    }

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Block> register) {
        register.getRegistry().register(new AirWalkerBlock());
    }
}
