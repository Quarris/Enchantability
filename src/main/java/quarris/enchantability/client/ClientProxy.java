package quarris.enchantability.client;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import quarris.enchantability.common.CommonProxy;

public class ClientProxy extends CommonProxy {

    @Override
    public void setup(FMLCommonSetupEvent event) {
        super.setup(event);

        MinecraftForge.EVENT_BUS.register(new ClientEvents());
    }
}
