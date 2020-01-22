package quarris.enchantability.client;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import quarris.enchantability.client.screen.EnchScreen;
import quarris.enchantability.common.CommonProxy;
import quarris.enchantability.common.container.EnchContainer;

public class ClientProxy extends CommonProxy {

    @Override
    public void setup(FMLCommonSetupEvent event) {
        super.setup(event);
        ScreenManager.registerFactory(EnchContainer.TYPE, EnchScreen::new);
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
    }
}
