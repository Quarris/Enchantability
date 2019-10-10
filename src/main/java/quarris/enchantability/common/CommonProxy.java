package quarris.enchantability.common;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import quarris.enchantability.common.network.PacketHandler;

public class CommonProxy {

    public void setup(FMLCommonSetupEvent event) {
        PacketHandler.init();
    }
}
