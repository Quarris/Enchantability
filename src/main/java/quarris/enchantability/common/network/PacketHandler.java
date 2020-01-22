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
        INSTANCE.registerMessage(0, EnderChestInteractPacket.class, EnderChestInteractPacket::encode, EnderChestInteractPacket::decode, EnderChestInteractPacket::handle);
        INSTANCE.registerMessage(1, ClickEnchButtonPacket.class, ClickEnchButtonPacket::encode, ClickEnchButtonPacket::decode, ClickEnchButtonPacket::handle);
    }
}
