package quarris.enchantability.mod.common.compat;

import net.minecraftforge.fml.ModList;

public class CompatManager {

    public static PatchouliCompat patchouli;

    public static void init() {
        if (ModList.get().isLoaded("patchouli")) {
            patchouli = new PatchouliCompat();
        }
    }

    public static void setPatchouliFlag(String flag, boolean val) {
        if (patchouli != null) {
            patchouli.setFlag(flag, val);
        }
    }

}
