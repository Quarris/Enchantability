package quarris.enchantability.mod.proxy;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import quarris.enchantability.mod.client.ClientEvents;
import quarris.enchantability.mod.client.screen.EnchScreen;
import quarris.enchantability.mod.common.container.EnchContainer;

public class ClientProxy implements IProxy {

    @Override
    public void setupClient(FMLClientSetupEvent event) {
        ScreenManager.registerFactory(EnchContainer.TYPE, EnchScreen::new);
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
    }
}
