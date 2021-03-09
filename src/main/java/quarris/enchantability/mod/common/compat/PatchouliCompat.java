package quarris.enchantability.mod.common.compat;

import quarris.enchantability.mod.common.util.ModRef;
import vazkii.patchouli.api.PatchouliAPI;

public class PatchouliCompat {

    public void setFlag(String flag, boolean val) {
        PatchouliAPI.get().setConfigFlag(ModRef.createRes(flag).toString(), val);
    }

}
