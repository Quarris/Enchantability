package quarris.enchantability.mod.common.enchants.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.LogicalSide;
import quarris.enchantability.api.enchants.AbstractEnchantEffect;
import quarris.enchantability.mod.common.util.ModRef;

import java.util.List;
import java.util.stream.Collectors;

public class LureEnchantEffect extends AbstractEnchantEffect {

    public static final ResourceLocation NAME = ModRef.createRes("lure");

    public LureEnchantEffect(PlayerEntity player, Enchantment enchantment, int level) {
        super(player, enchantment, level);
    }

    public static void spawnAnimals(LureEnchantEffect effect, TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.END) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.player;
            ServerWorld world = (ServerWorld) event.player.world;
            if (world.getGameTime() % 50 == 0 && world.getRandom().nextFloat() < 0.9f) {
                Biome biome = world.getBiome(player.func_233580_cy_());

                List<Biome.SpawnListEntry> spawns = biome.getSpawns(EntityClassification.CREATURE);

                List<EntityType<?>> animalTypes = spawns.stream()
                        .map(entry -> entry.entityType)
                        .collect(Collectors.toList());

                animalTypes.forEach(type -> System.out.println((type.getName())));

                if (animalTypes.isEmpty())
                    return;

                EntityType<?> type = animalTypes.get(world.getRandom().nextInt(animalTypes.size()));

                Entity animal = type.create(world);
                if (animal != null) {
                    animal.setPosition(player.getPosX(), player.getPosY(), player.getPosZ());
                    world.addEntity(animal);
                }
            }
        }
    }

    @Override
    public ResourceLocation getName() {
        return NAME;
    }
}
