package quarris.enchantability.mod.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import quarris.enchantability.api.EnchantabilityApi;
import quarris.enchantability.mod.ModConfig;
import quarris.enchantability.mod.client.screen.EnchScreen;
import quarris.enchantability.mod.common.CommonProxy;
import quarris.enchantability.mod.common.container.EnchContainer;
import quarris.enchantability.mod.common.enchants.impl.DexterityEnchantEffect;
import quarris.enchantability.mod.common.enchants.impl.GluttonyEnchantEffect;
import quarris.enchantability.mod.common.enchants.impl.LavaSwimEffect;

import java.util.Collections;

public class ClientProxy extends CommonProxy {

    @Override
    public void setupClient(FMLClientSetupEvent event) {
        ScreenManager.registerFactory(EnchContainer.TYPE, EnchScreen::new);
    }

    @Override
    public void registerClientComponents() {
        if (ModConfig.get().enableDexterity.get()) {
            EnchantabilityApi.registerEffectComponent(DexterityEnchantEffect.NAME, ItemTooltipEvent.class, DexterityEnchantEffect::addTooltips, e -> {
                if (e.getPlayer() != null) {
                    return Collections.singleton(e.getPlayer());
                }
                return Collections.emptyList();
            });
        }

        if (ModConfig.get().enableGluttony.get()) {
            EnchantabilityApi.registerEffectComponent(GluttonyEnchantEffect.NAME, ItemTooltipEvent.class, GluttonyEnchantEffect::addTooltips, e -> {
                if (e.getPlayer() != null) {
                    return Collections.singleton(e.getPlayer());
                }
                return Collections.emptyList();
            });
        }

        if (ModConfig.get().enableLavaSwim.get()) {
            EnchantabilityApi.registerEffectComponent(LavaSwimEffect.NAME, EntityViewRenderEvent.RenderFogEvent.class, LavaSwimEffect::clearFog, e -> Collections.singleton(Minecraft.getInstance().player));
        }
    }
}
