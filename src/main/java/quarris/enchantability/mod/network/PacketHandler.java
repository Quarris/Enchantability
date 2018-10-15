package quarris.enchantability.mod.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import quarris.enchantability.mod.Enchantability;

public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Enchantability.MODID);
    private static int id = 0;

    public static void init() {
        INSTANCE.registerMessage(PacketOpenEnchantInventory.class, PacketOpenEnchantInventory.class, id++, Side.SERVER);
        INSTANCE.registerMessage(PacketOpenEChestInventory.class, PacketOpenEChestInventory.class, id++, Side.SERVER);
        INSTANCE.registerMessage(PacketSendCapsToClients.class, PacketSendCapsToClients.class, id++, Side.CLIENT);
    }
}
