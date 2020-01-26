package quarris.enchantability.mod.common.enchants.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import quarris.enchantability.api.enchants.IEnchantEffect;
import quarris.enchantability.mod.common.util.ModRef;

public class KnockbackEnchantEffect implements IEnchantEffect {

    public static final ResourceLocation NAME = ModRef.createRes("far_reach");

    public final Enchantment origin;
    public final int level;

    public KnockbackEnchantEffect(Enchantment enchantment, int level) {
        this.origin = enchantment;
        this.level = level;
    }

    @Override
    public Enchantment origin() {
        return this.origin;
    }

    @Override
    public int level() {
        return this.level;
    }

    @Override
    public ResourceLocation getName() {
        return NAME;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("KnockbackEnchantEffect{");
        sb.append("hash=").append(Integer.toHexString(hashCode()));
        sb.append(", origin=").append(origin);
        sb.append(", level=").append(level);
        sb.append('}');
        return sb.toString();
    }
}
