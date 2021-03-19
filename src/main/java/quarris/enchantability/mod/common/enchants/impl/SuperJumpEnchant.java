package quarris.enchantability.mod.common.enchants.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.LogicalSide;
import quarris.enchantability.api.enchants.AbstractEnchantEffect;
import quarris.enchantability.mod.ModConfig;
import quarris.enchantability.mod.common.util.ModRef;

public class SuperJumpEnchant extends AbstractEnchantEffect {

    public static final ResourceLocation NAME = ModRef.createRes("super_jump");

    public int chargeLevel;
    public int chargeTicks;

    public SuperJumpEnchant(PlayerEntity player, Enchantment enchantment, int level) {
        super(player, enchantment, level);
    }

    public static void itsamemario(SuperJumpEnchant effect, TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            PlayerEntity player = effect.player;
            if (!player.isOnGround() || isMoving(player)) {
                effect.chargeTicks = 0;
                effect.chargeLevel = 0;
                return;
            }

            if (!player.isSneaking()) {
                if (effect.chargeLevel > 0) {
                    effect.doSuperJump();
                }
                effect.chargeTicks = 0;
                effect.chargeLevel = 0;
                return;
            }

            if (player.isOnGround()) {

                if (effect.chargeLevel < effect.level()) {
                    // Increase charge ticks
                    effect.chargeTicks = Math.min(effect.chargeTicks + 1, ModConfig.get().chargePerLevel.get() * effect.level());
                }

                int charge = effect.calcChargeLevel();
                // If we increased in charge level
                if (effect.chargeLevel != charge) {
                    effect.chargeLevel = charge;
                    // If just charged, play a sound
                    if (event.side == LogicalSide.CLIENT) {
                        player.world.playSound(player, player.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1, charge / 2f);
                    }
                }

                if (effect.chargeLevel > 0) {
                    // Spawn particles while fully charged
                    if (player.world instanceof ServerWorld) {
                        ServerWorld world = (ServerWorld) player.world;
                        world.spawnParticle(ParticleTypes.SMOKE, player.getPosX(), player.getPosY(), player.getPosZ(), 2 * charge, 0.4, 0.4, 0.4, 0.01);
                    }

                }
            }
        }
    }

    private void doSuperJump() {
        this.player.setMotion(this.player.getMotion().add(0, this.chargeLevel * ModConfig.get().chargeJumpMultiplier.get(), 0));
    }

    private int calcChargeLevel() {
        return this.chargeTicks / ModConfig.get().chargePerLevel.get();
    }

    private static boolean isMoving(PlayerEntity player) {
        return false; // TODO Somehow find a way to check if the player is moving on both client and server
    }

    @Override
    public ResourceLocation getName() {
        return NAME;
    }
}
