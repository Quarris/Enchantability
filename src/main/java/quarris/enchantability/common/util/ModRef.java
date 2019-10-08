package quarris.enchantability.common.util;

import net.minecraft.util.ResourceLocation;

public class ModRef {
    public static final String MOD_ID = "enchantability";
    public static final String MOD_NAME = "Enchantability";
    public static final String VERSION = "1.2.0";

    public static ResourceLocation createRes(String name) {
        return new ResourceLocation(MOD_ID, name);
    }
}
