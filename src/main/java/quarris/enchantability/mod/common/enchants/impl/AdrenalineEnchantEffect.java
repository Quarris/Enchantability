package quarris.enchantability.mod.common.enchants.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.LogicalSide;
import quarris.enchantability.api.enchants.AbstractEnchantEffect;
import quarris.enchantability.mod.common.util.ModRef;

import java.util.HashMap;
import java.util.Map;

public class AdrenalineEnchantEffect extends AbstractEnchantEffect {

    public static final ResourceLocation NAME = ModRef.createRes("adrenaline");

    public static final long RANGE = 20 * 5;

    public final Map<Long, Float> damageTaken;
    public final Map<Long, Float> damageDealt;

    public AdrenalineEnchantEffect(PlayerEntity player, Enchantment enchantment, int level) {
        super(player, enchantment, level);

        this.damageTaken = new HashMap<>();
        this.damageDealt = new HashMap<>();
    }

    public static void tick(AdrenalineEnchantEffect effect, TickEvent.PlayerTickEvent event) {
        if (!(event.phase == TickEvent.Phase.END && event.side == LogicalSide.SERVER))
            return;

        long time = event.player.world.getGameTime();

        //removeOutdated(effect.damageTaken, time);
        removeOutdated(effect.damageDealt, time);
    }

    public static void onDamageTaken(AdrenalineEnchantEffect effect, LivingDamageEvent event) {
        effect.damageTaken.put(event.getEntityLiving().world.getGameTime(), event.getAmount());
    }

    public static void onDamageDealt(AdrenalineEnchantEffect effect, LivingAttackEvent event) {
        System.out.println("Dealing Damage");
        effect.damageDealt.put(event.getEntityLiving().world.getGameTime(), event.getAmount());
    }

    private static void removeOutdated(Map<Long, Float> map, long time) {
        for (long tick : map.keySet()) {
            System.out.println(tick);
        }
    }

    @Override
    public ResourceLocation getName() {
        return NAME;
    }
}
