package quarris.enchantability;

import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import quarris.enchantability.client.ClientProxy;
import quarris.enchantability.common.CommonProxy;
import quarris.enchantability.common.network.PacketHandler;
import quarris.enchantability.common.util.ModRef;

@Mod(ModRef.MOD_ID)
public class Enchantability {
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public static CommonProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public Enchantability() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        proxy.setup(event);
    }
}
