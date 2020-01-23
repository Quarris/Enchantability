package quarris.enchantability.mod.client;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import quarris.enchantability.mod.client.screen.EnchScreen;
import quarris.enchantability.mod.common.CommonProxy;
import quarris.enchantability.mod.common.container.EnchContainer;

public class ClientProxy extends CommonProxy {

    @Override
    public void setup(FMLCommonSetupEvent event) {
        super.setup(event);
        ScreenManager.registerFactory(EnchContainer.TYPE, EnchScreen::new);
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
    }
}