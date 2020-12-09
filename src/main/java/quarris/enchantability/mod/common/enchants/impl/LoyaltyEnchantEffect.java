package quarris.enchantability.mod.common.enchants.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.LogicalSide;
import quarris.enchantability.api.enchants.AbstractEnchantEffect;
import quarris.enchantability.mod.ModConfig;
import quarris.enchantability.mod.common.util.ModRef;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

public class LoyaltyEnchantEffect extends AbstractEnchantEffect {

    public static final ResourceLocation NAME = ModRef.createRes("loyalty");

    private final Set<PetReviver> revivers = new HashSet<>();

    public LoyaltyEnchantEffect(PlayerEntity player, Enchantment enchantment, int level) {
        super(player, enchantment, level);
    }

    public static void petDied(LoyaltyEnchantEffect effect, LivingDeathEvent event) {
        if (event.getEntityLiving() instanceof TameableEntity && ((TameableEntity) event.getEntityLiving()).isOwner(effect.player)) {
            if (!effect.player.world.isRemote) {
                int level = Math.min(effect.level(), Enchantments.LOYALTY.getMaxLevel());
                effect.revivers.add(new PetReviver(effect.player.world.getGameTime() + ModConfig.get().loyaltyReviveTimes.get().get(level-1), (TameableEntity) event.getEntityLiving()));
                event.getEntityLiving().remove(true);
            }
            event.setCanceled(true);
        }
    }

    public static void revivePet(LoyaltyEnchantEffect effect, TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.side == LogicalSide.SERVER) {
            for (Iterator<PetReviver> iterator = effect.revivers.iterator(); iterator.hasNext(); ) {
                PetReviver reviver = iterator.next();
                if (reviver.canRevive(effect.player.world)) {
                    reviver.revivePet();
                    iterator.remove();
                }
            }
        }
    }

    @Override
    public ResourceLocation getName() {
        return NAME;
    }

    private static class PetReviver {
        public long reviveTime;
        public final TameableEntity pet;

        public PetReviver(long reviveTime, TameableEntity pet) {
            this.reviveTime = reviveTime;
            this.pet = pet;
        }

        public boolean canRevive(World world) {
            return world.getGameTime() >= this.reviveTime;
        }

        // ~~I'm not really sure what this means~~
        // I kinda know how this works, but I got it from Ellpecks Pet Reviver haha
        public void revivePet() {
            // get the overworld, and the overworld's spawn point, by default
            ServerWorld spawnWorld = this.pet.getServer().func_241755_D_();
            Vector3d spawn = Vector3d.copyCenteredHorizontally(spawnWorld.func_241135_u_());

            // check if the owner is online, and respawn at the bed if they are
            ServerPlayerEntity player = this.pet.getServer().getPlayerList().getPlayerByUUID(this.pet.getOwnerId());
            if (player != null) {
                BlockPos pos = player.func_241140_K_(); // Player Respawn Pos
                if (pos != null) {
                    float f = player.func_242109_L(); // Look Angle
                    // Get the players respawn world.
                    ServerWorld respawnWorld = player.getServer().getWorld(player.func_241141_L_());
                    if (respawnWorld != null) {
                        // Find player respawn (bed/anchor)
                        Optional<Vector3d> respawn = PlayerEntity.func_242374_a(respawnWorld, pos, f, false, true);
                        if (respawn.isPresent()) {
                            // Set the spawn world to be at the position of the players respawn
                            spawnWorld = respawnWorld;
                            spawn = respawn.get();
                        }
                    }
                }
            }

            TameableEntity spawnedPet = this.pet;
            // If the pet is in a different dimension than the respawn.
            // Create a new pet.
            if (this.pet.world != spawnWorld) {
                ((ServerWorld) this.pet.world).removeEntity(this.pet, true);
                spawnedPet = (TameableEntity) this.pet.getType().create(spawnWorld);
            }
            // respawn (a copy of) the pet
            spawnedPet.copyDataFromOld(this.pet);
            spawnedPet.setMotion(0, 0, 0);
            spawnedPet.setLocationAndAngles(spawn.x, spawn.y, spawn.z, this.pet.rotationYaw, this.pet.rotationPitch);
            while (!spawnWorld.hasNoCollisions(spawnedPet)) {
                spawnedPet.setPosition(spawnedPet.getPosX(), spawnedPet.getPosY() + 1, spawnedPet.getPosZ());
            }
            spawnedPet.setHealth(spawnedPet.getMaxHealth());
            spawnedPet.getNavigator().clearPath();
            // sit down (on the server side!)
            spawnedPet.func_233687_w_(true);
            spawnedPet.setJumping(false);
            spawnedPet.setAttackTarget(null);
            if (this.pet.world != spawnWorld) {
                this.pet.remove(false);
            } else {
                spawnedPet.revive();
            }
            spawnWorld.addEntity(spawnedPet);
        }
    }
}
