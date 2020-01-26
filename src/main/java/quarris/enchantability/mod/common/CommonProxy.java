package quarris.enchantability.mod.common;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import quarris.enchantability.mod.common.util.ModUtil;

public class CommonProxy {

    public void setupClient(FMLClientSetupEvent event) {

    }

    public void populateEventPlayerGetters() {
        ModUtil.addEventPlayerGetter(TickEvent.PlayerTickEvent.class, (event -> ((TickEvent.PlayerTickEvent)event).player));
        ModUtil.addEventPlayerGetter(PlayerEvent.class, (event -> ((PlayerEvent)event).getPlayer()));
    }
}
