package quarris.enchantability.mod.common.enchants.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import quarris.enchantability.api.enchants.AbstractEnchantEffect;
import quarris.enchantability.mod.common.util.ModRef;
import quarris.enchantability.mod.common.util.ModUtil;

public class DeflectionEnchantEffect extends AbstractEnchantEffect {

    public static final ResourceLocation NAME = ModRef.createRes("deflection");

    public DeflectionEnchantEffect(PlayerEntity player, Enchantment enchantment, int level) {
        super(player, enchantment, level);
    }

    public static void deflect(DeflectionEnchantEffect effect, ProjectileImpactEvent event) {
        if (!effect.player.world.isRemote()) {
            float chance = effect.level/5f;
            if (chance >= ModUtil.RANDOM.nextFloat()) {
                Entity projectile = event.getEntity();
                if (projectile instanceof IProjectile) {
                    Vec3d motion = projectile.getMotion();
                    ((IProjectile) projectile).shoot(-motion.x, -motion.y, -motion.z, (float)motion.length()/2f, 0);
                    ServerWorld world = (ServerWorld) effect.player.world;
                    world.getChunkProvider().chunkManager.getTrackingPlayers(new ChunkPos(projectile.getPosition()), false)
                            .forEach(player -> player.connection.sendPacket(new SEntityVelocityPacket(projectile)));
                }
                event.setCanceled(true);
            }
        }
    }

    @Override
    public ResourceLocation getName() {
        return NAME;
    }
}
