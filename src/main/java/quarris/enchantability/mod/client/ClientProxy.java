package quarris.enchantability.mod.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import quarris.enchantability.mod.client.screen.EnchScreen;
import quarris.enchantability.mod.common.CommonProxy;
import quarris.enchantability.mod.common.container.EnchContainer;
import quarris.enchantability.mod.common.util.ModUtil;

public class ClientProxy extends CommonProxy {

    @Override
    public void setupClient(FMLClientSetupEvent event) {
        ScreenManager.registerFactory(EnchContainer.TYPE, EnchScreen::new);
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
    }

    @Override
    public void populateEventPlayerGetters() {
        super.populateEventPlayerGetters();
        addClientPlayerGetter(RenderWorldLastEvent.class);
        addClientPlayerGetter(TickEvent.ClientTickEvent.class);
        addClientPlayerGetter(RenderPlayerEvent.class);
    }

    private static <T extends Event> void addClientPlayerGetter(Class<T> event) {
        ModUtil.addEventPlayerGetter(event, (e -> Minecraft.getInstance().player));
    }
}
