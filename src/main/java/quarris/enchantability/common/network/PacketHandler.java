package quarris.enchantability.common.network;

import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import quarris.enchantability.common.util.ModRef;

public class PacketHandler {

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            ModRef.createRes("main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void init() {
        int id = 0;
        INSTANCE.registerMessage(id++, OpenEnderChestPacket.class, OpenEnderChestPacket::encode, OpenEnderChestPacket::decode, OpenEnderChestPacket::handle);
    }
}
