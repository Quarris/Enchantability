package quarris.enchantability.mod.common.enchants.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.LogicalSide;
import quarris.enchantability.api.enchants.AbstractEnchantEffect;
import quarris.enchantability.mod.common.util.ModRef;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class LureEnchantEffect extends AbstractEnchantEffect {

    public static final float SPAWN_ANGLE = 120;
    public static final float MIN_DISTANCE = 15;
    public static final float MAX_DISTANCE = 50;
    public static final float CHANCE = 0.05f;
    public static final int TEST_INTERVAL = 200;

    public static final ResourceLocation NAME = ModRef.createRes("lure");

    public LureEnchantEffect(PlayerEntity player, Enchantment enchantment, int level) {
        super(player, enchantment, level);
    }

    public static void spawnAnimals(LureEnchantEffect effect, TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.END) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.player;
            ServerWorld world = (ServerWorld) event.player.world;

            if (world.getGameTime() % TEST_INTERVAL == 0 && world.getRandom().nextFloat() < CHANCE) {
                Biome biome = world.getBiome(player.getPosition());

                MobSpawnInfo spawnInfo = biome.func_242433_b();
                List<MobSpawnInfo.Spawners> spawners = new ArrayList<>();
                spawners.addAll(spawnInfo.func_242559_a(EntityClassification.CREATURE));
                spawners.addAll(spawnInfo.func_242559_a(EntityClassification.WATER_CREATURE));

                List<EntityType<?>> animalTypes = spawners.stream()
                        .map(entry -> entry.field_242588_c)
                        .collect(Collectors.toList());

                if (animalTypes.isEmpty())
                    return;

                EntityType<?> type = animalTypes.get(world.getRandom().nextInt(animalTypes.size()));


                Entity animal = type.create(world);
                if (animal != null) {
                    for (int test = 0; test < 50; test++) {
                        float angle = MathHelper.nextFloat(world.getRandom(), player.rotationYawHead + 180 - SPAWN_ANGLE / 2, player.rotationYawHead + 180 + SPAWN_ANGLE / 2);
                        float distance = MathHelper.nextFloat(world.getRandom(), MIN_DISTANCE, MAX_DISTANCE);
                        BlockPos spawnPos = new BlockPos(Vector3d.fromPitchYaw(0, angle).scale(distance)).add(player.getPosition());
                        int spawnHeight = world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, spawnPos.getX(), spawnPos.getZ());
                        if (type.getClassification() == EntityClassification.WATER_CREATURE) {
                            spawnHeight = getRandomHeight(spawnHeight, world.getHeight(Heightmap.Type.OCEAN_FLOOR, spawnPos.getX(), spawnPos.getZ()), world.getRandom());
                        }
                        spawnPos = new BlockPos(spawnPos.getX(), spawnHeight, spawnPos.getZ());
                        if (world.isAreaLoaded(spawnPos, 1)) {
                            if (EntitySpawnPlacementRegistry.canSpawnEntity(type, world, SpawnReason.NATURAL, spawnPos, world.getRandom())) {
                                animal.setPositionAndRotation(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), 360 * (float) Math.random(), 0);
                                if (world.hasNoCollisions(animal)) {
                                    world.addEntity(animal);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static int getRandomHeight(int firstY, int secondY, Random random) {
        int min = Math.min(firstY, secondY);
        int max = Math.max(firstY, secondY);
        return min + random.nextInt(max - min);
    }

    @Override
    public ResourceLocation getName() {
        return NAME;
    }
}
