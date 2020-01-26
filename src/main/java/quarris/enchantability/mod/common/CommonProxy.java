package quarris.enchantability.mod.common;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import quarris.enchantability.api.capabilities.IPlayerEnchant;
import quarris.enchantability.mod.common.enchants.Enchants;
import quarris.enchantability.mod.common.network.PacketHandler;
import quarris.enchantability.mod.common.util.ModUtil;

public class CommonProxy {

    public void setup(FMLCommonSetupEvent event) {
        PacketHandler.init();
        ModUtil.registerCap(IPlayerEnchant.class);
        Enchants.register();
    }
}
