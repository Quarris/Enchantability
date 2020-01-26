package quarris.enchantability.mod;

import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import quarris.enchantability.api.EnchantabilityApi;
import quarris.enchantability.api.capabilities.IPlayerEnchant;
import quarris.enchantability.mod.common.enchants.Enchants;
import quarris.enchantability.mod.common.network.PacketHandler;
import quarris.enchantability.mod.common.util.ModRef;
import quarris.enchantability.mod.common.util.ModUtil;
import quarris.enchantability.mod.proxy.ClientProxy;
import quarris.enchantability.mod.proxy.IProxy;
import quarris.enchantability.mod.proxy.ServerProxy;

@SuppressWarnings("unused")
@Mod(ModRef.ID)
public class Enchantability {

    public static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public Enchantability() {
        EnchantabilityApi.setInstance(new Internals());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
    }

    private void setup(final FMLCommonSetupEvent event) {
        PacketHandler.init();
        ModUtil.registerCap(IPlayerEnchant.class);
        Enchants.registerEffect();
    }

    private void setupClient(final FMLClientSetupEvent event) {
        proxy.setupClient(event);
    }
}
