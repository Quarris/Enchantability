package quarris.enchantability.mod;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import quarris.enchantability.api.EnchantabilityApi;
import quarris.enchantability.api.capabilities.IPlayerEnchant;
import quarris.enchantability.mod.client.ClientProxy;
import quarris.enchantability.mod.common.CommonProxy;
import quarris.enchantability.mod.common.compat.CompatManager;
import quarris.enchantability.mod.common.enchants.Enchants;
import quarris.enchantability.mod.common.network.PacketHandler;
import quarris.enchantability.mod.common.util.ModRef;
import quarris.enchantability.mod.common.util.ModUtil;

@Mod(ModRef.ID)
public class Enchantability {

    public static CommonProxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public Enchantability() {
        EnchantabilityApi.setInternals(new Internals());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);

        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        ModConfig.init(configBuilder);
        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, configBuilder.build());
    }

    private void setup(final FMLCommonSetupEvent event) {
        CompatManager.init();
        PacketHandler.init();
        Enchants.registerEffect();
        ModUtil.registerCap(IPlayerEnchant.class);
    }

    private void setupClient(final FMLClientSetupEvent event) {
        proxy.setupClient(event);
    }
}
