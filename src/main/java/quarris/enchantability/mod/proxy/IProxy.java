package quarris.enchantability.mod.proxy;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public interface IProxy {

    default void setupClient(FMLClientSetupEvent event) {}
}

