package quarris.enchantability.mod.common.enchants.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.LogicalSide;
import quarris.enchantability.api.enchants.AbstractEnchantEffect;
import quarris.enchantability.mod.ModConfig;
import quarris.enchantability.mod.common.util.ModRef;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdrenalineEnchantEffect extends AbstractEnchantEffect {

    public static final ResourceLocation NAME = ModRef.createRes("adrenaline");

    public int cooldown;

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

        if (effect.cooldown > 0) {
            effect.cooldown--;
            if (effect.cooldown == 0) {
                effect.player.sendStatusMessage(new TranslationTextComponent("enchantability:adrenaline.enchant.ready"), true);
            }
            return;
        }

        long time = event.player.world.getGameTime();

        // Remove outdated damages from the map
        List<Long> sorted = effect.damageDealt.keySet().stream().sorted().collect(Collectors.toList());
        for (long tick : sorted) {
            if (time >= tick + ModConfig.get().adrenalineDamageDealtTime.get()) {
                effect.damageDealt.remove(tick);
                continue;
            }
            break;
        }

        sorted = effect.damageTaken.keySet().stream().sorted().collect(Collectors.toList());
        for (long tick : sorted) {
            if (time >= tick + ModConfig.get().adrenalineDamageTakenTime.get()) {
                effect.damageTaken.remove(tick);
                continue;
            }
            break;
        }

        boolean activated = false;

        if (sum(effect.damageTaken) > ModConfig.get().adrenalineDamageTakenThreshold.get()) {
            damageTakenBoost(effect.level(), effect.player);
            activated = true;
        }

        if (sum(effect.damageDealt) > ModConfig.get().adrenalineDamageDealtThreshold.get()) {
            damageDealtBoost(effect.level(), effect.player);
            activated = true;
        }

        if (activated) {
            effect.player.sendStatusMessage(new TranslationTextComponent("enchantability:adrenaline.enchant.activated"), true);
            effect.cooldown = durationFromLevel(effect.level) + ModConfig.get().adrenalineCooldown.get();
            effect.damageTaken.clear();
            effect.damageDealt.clear();
        }
    }

    public static void onDamageTaken(AdrenalineEnchantEffect effect, LivingDamageEvent event) {
        if (effect.cooldown > 0 || event.getAmount() < 0.001)
            return;
        effect.damageTaken.put(event.getEntityLiving().world.getGameTime(), event.getAmount());
    }

    private static void damageTakenBoost(int level, PlayerEntity player) {
        player.addPotionEffect(new EffectInstance(Effects.REGENERATION, durationFromLevel(level)/4, level-1, false, false));
        player.addPotionEffect(new EffectInstance(Effects.SPEED, durationFromLevel(level), level-1, false, false));
        player.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, durationFromLevel(level), level-1, false, false));
    }

    public static void onDamageDealt(AdrenalineEnchantEffect effect, LivingAttackEvent event) {
        if (effect.cooldown > 0 || event.getAmount() < 0.001)
            return;
        effect.damageDealt.put(event.getEntityLiving().world.getGameTime(), event.getAmount());
    }

    private static void damageDealtBoost(int level, PlayerEntity player) {
        player.addPotionEffect(new EffectInstance(Effects.STRENGTH, durationFromLevel(level), level-1, false, false));
        player.addPotionEffect(new EffectInstance(Effects.RESISTANCE, durationFromLevel(level), level-1, false, false));
    }

    private static int durationFromLevel(int level) {
        if (level == 1) {
            return ModConfig.get().adrenalineDuration1.get();
        } else if (level == 2) {
            return ModConfig.get().adrenalineDuration2.get();
        } else {
            return ModConfig.get().adrenalineDuration3.get();
        }
    }

    private static float sum(Map<Long, Float> map) {
        float sum = 0;
        for (float val : map.values()) {
            sum += val;
        }
        return sum;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = super.serializeNBT();
        nbt.putInt("Cooldown", this.cooldown);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        super.deserializeNBT(nbt);
        this.cooldown = nbt.getInt("Cooldown");
    }

    @Override
    public ResourceLocation getName() {
        return NAME;
    }
}
