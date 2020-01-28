package quarris.enchantability.mod;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModConfig {

    public ForgeConfigSpec.BooleanValue enableFarReach;
    public ForgeConfigSpec.BooleanValue enableFastBreak;
    public ForgeConfigSpec.BooleanValue enableGravity;
    public ForgeConfigSpec.BooleanValue enableDeflection;
    public ForgeConfigSpec.BooleanValue enableVoid;
    public ForgeConfigSpec.BooleanValue enableSmite;
    public ForgeConfigSpec.BooleanValue enableBlastResist;
    public ForgeConfigSpec.BooleanValue enableFirePraise;

    private static ModConfig instance;
    public static ModConfig get() {
        return instance;
    }

    public static void init(ForgeConfigSpec.Builder builder) {
        instance = new ModConfig(builder);
    }

    public ModConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("Enabled Features").push("features");
        enableFarReach = builder.define("enableFarReach", true);
        enableFastBreak = builder.define("enableFastBreak", true);
        enableGravity = builder.define("enableGravity", true);
        enableDeflection = builder.define("enableDeflection", true);
        enableVoid = builder.define("enableVoid", true);
        enableSmite = builder.define("enableSmite", true);
        enableBlastResist = builder.define("enableBlastResist", true);
        enableFirePraise = builder.define("enableFirePraise", true);
        builder.pop();
    }

    public void reload() {

    }
}
