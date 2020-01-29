package quarris.enchantability.mod.common.enchants.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
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
            float chance = effect.level / 5f;
            if (chance >= ModUtil.RANDOM.nextFloat()) {
                Entity projectile = event.getEntity();
                System.out.println(projectile);
                Vec3d motion = projectile.getMotion();
                shoot(projectile, -motion.x, -motion.y, -motion.z, (float) motion.length() / 2f, 0);
                ServerWorld world = (ServerWorld) effect.player.world;
                world.getChunkProvider().chunkManager.getTrackingPlayers(new ChunkPos(projectile.getPosition()), false)
                        .forEach(player -> player.connection.sendPacket(new SEntityVelocityPacket(projectile)));
                event.setCanceled(true);
            }
        }
    }

    private static void shoot(Entity entity, double x, double y, double z, float velocity, float inaccuracy) {
        Vec3d vec3d = (new Vec3d(x, y, z)).normalize().add(entity.world.rand.nextGaussian() * (double) 0.0075F * (double) inaccuracy, entity.world.rand.nextGaussian() * (double) 0.0075F * (double) inaccuracy, entity.world.rand.nextGaussian() * (double) 0.0075F * (double) inaccuracy).scale((double) velocity);
        entity.setMotion(vec3d);
        float f = MathHelper.sqrt(vec3d.x * vec3d.x + vec3d.z * vec3d.z);
        entity.rotationYaw = (float) (MathHelper.atan2(vec3d.x, vec3d.z) * (double) (180F / (float) Math.PI));
        entity.rotationPitch = (float) (MathHelper.atan2(vec3d.y, (double) f) * (double) (180F / (float) Math.PI));
        entity.prevRotationYaw = entity.rotationYaw;
        entity.prevRotationPitch = entity.rotationPitch;
        /* This works but there isn't a packet to update acceleration to the client
        if (entity instanceof DamagingProjectileEntity) {
            DamagingProjectileEntity projectile = (DamagingProjectileEntity) entity;
            projectile.accelerationX *= -1;
            projectile.accelerationY *= -1;
            projectile.accelerationZ *= -1;
        }
         */
    }

    @Override
    public ResourceLocation getName() {
        return NAME;
    }
}
