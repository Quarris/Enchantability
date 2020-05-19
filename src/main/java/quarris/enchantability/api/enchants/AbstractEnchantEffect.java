package quarris.enchantability.api.enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;

public abstract class AbstractEnchantEffect implements IEnchantEffect {

    public final PlayerEntity player;
    private final Enchantment origin;
    private final int level;

    public AbstractEnchantEffect(PlayerEntity player, Enchantment enchantment, int level) {
        this.player = player;
        this.origin = enchantment;
        this.level = level;
    }

    @Override
    public void onApplied() {

    }

    @Override
    public void onRemoved() {

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
    public String toString() {
        final StringBuilder sb = new StringBuilder(this.getClass().getName()).append("{");
        sb.append("hash=").append(Integer.toHexString(hashCode()));
        sb.append(", name=").append(this.getName());
        sb.append(", level=").append(level);
        sb.append('}');
        return sb.toString();
    }

}
