package quarris.enchantability.mod.common.util;

import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

public class ModRef {
    public static final String ID = "enchantability";
    public static final String NAME = "Enchantability";
    public static final String VERSION = "1.3.0";

    public static final UUID ENCHANT_UUID = UUID.fromString("0DD5A1AD-CA11-ADD5-1CED-C0FFEEEFFEC7");

    public static final Logger LOGGER = LogManager.getLogger(NAME);

    public static ResourceLocation createRes(String name) {
        return new ResourceLocation(ID, name);
    }
}
