package quarris.enchantability.mod.common;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import quarris.enchantability.mod.common.network.PacketHandler;

public class CommonProxy {

    public void setup(FMLCommonSetupEvent event) {
        PacketHandler.init();
    }
}
