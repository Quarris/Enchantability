package quarris.enchantability.mod;

import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import quarris.enchantability.api.EnchantabilityApi;
import quarris.enchantability.api.capabilities.IPlayerEnchant;
import quarris.enchantability.mod.common.enchants.Enchants;
import quarris.enchantability.mod.common.network.PacketHandler;
import quarris.enchantability.mod.common.util.ModRef;
import quarris.enchantability.mod.common.util.ModUtil;
import quarris.enchantability.mod.proxy.ClientProxy;
import quarris.enchantability.mod.proxy.IProxy;
import quarris.enchantability.mod.proxy.ServerProxy;

@Mod(ModRef.MOD_ID)
public class Enchantability {
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public Enchantability() {
        EnchantabilityApi.setInstance(new Internals());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
    }

    private void setup(final FMLCommonSetupEvent event) {
        PacketHandler.init();
        ModUtil.registerCap(IPlayerEnchant.class);
        Enchants.register();
    }

    private void setupClient(final FMLClientSetupEvent event) {
        proxy.setupClient(event);
    }
}
