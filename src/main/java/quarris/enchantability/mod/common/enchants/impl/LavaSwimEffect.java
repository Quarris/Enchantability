package quarris.enchantability.mod.common.enchants.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import quarris.enchantability.api.enchants.AbstractEnchantEffect;
import quarris.enchantability.mod.common.util.ModRef;

import java.lang.reflect.Field;

public class LavaSwimEffect extends AbstractEnchantEffect {

    public static final ResourceLocation NAME = ModRef.createRes("lava_swim");
    public static final Field IS_JUMPING = ObfuscationReflectionHelper.findField(LivingEntity.class, "field_70703_bu");

    public boolean isSwimmingInLava;

    public LavaSwimEffect(PlayerEntity player, Enchantment enchantment, int level) {
        super(player, enchantment, level);
    }

    public static void startSwimming(LavaSwimEffect effect, TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            PlayerEntity player = effect.player;
            effect.updateSwimmingInLava();
            if (!player.abilities.isFlying && player.isInLava() && effect.isSwimmingInLava) {
                double lookY = player.getLookVec().y;
                double moveY = lookY < -0.2D ? 0.085D : 0.06D;
                if (lookY <= 0.0D || isJumping(player) || !player.world.getBlockState(new BlockPos(player.getPosX(), player.getPosY() + 0.9D, player.getPosZ())).getFluidState().isEmpty()) {
                    Vector3d motion = player.getMotion();
                    player.setMotion(motion.add(0.0D, (lookY - motion.y) * moveY, 0.0D));
                }
                Vector3d travelVector = new Vector3d(player.moveStrafing, player.moveVertical, player.moveForward);
                ModifiableAttributeInstance gravity = player.getAttribute(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get());
                boolean isMovingDown = player.getMotion().y <= 0.0D;
                double gravValue = gravity.getValue();
                double posY = player.getPosY();
                float speed = player.isSprinting() ? 0.9F : 0.8f;
                float f6 = 0.02F;
                float f7 = (float) EnchantmentHelper.getDepthStriderModifier(player);
                if (f7 > 3.0F) {
                    f7 = 3.0F;
                }

                if (!player.isOnGround()) {
                    f7 *= 0.5F;
                }

                if (f7 > 0.0F) {
                    speed += (0.54600006F - speed) * f7 / 3.0F;
                    f6 += (player.getAIMoveSpeed() - f6) * f7 / 3.0F;
                }

                if (player.isPotionActive(Effects.DOLPHINS_GRACE)) {
                    speed = 0.96F;
                }

                f6 *= (float)player.getAttribute(net.minecraftforge.common.ForgeMod.SWIM_SPEED.get()).getValue();
                player.moveRelative(f6, travelVector);
                player.move(MoverType.SELF, player.getMotion());
                Vector3d vector3d6 = player.getMotion();
                if (player.collidedHorizontally && player.isOnLadder()) {
                    vector3d6 = new Vector3d(vector3d6.x, 0.2D, vector3d6.z);
                }

                player.setMotion(vector3d6.mul(speed, 0.8F, speed));
                Vector3d vector3d2 = player.func_233626_a_(gravValue, isMovingDown, player.getMotion());
                player.setMotion(vector3d2);
                if (player.collidedHorizontally && player.isOffsetPositionInLiquid(vector3d2.x, vector3d2.y + (double)0.6F - player.getPosY() + posY, vector3d2.z)) {
                    player.setMotion(vector3d2.x, 0.3F, vector3d2.z);
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void clearFog(LavaSwimEffect effect, EntityViewRenderEvent.RenderFogEvent event) {
        FluidState fluidState = event.getInfo().getFluidState();
        if (fluidState.isTagged(FluidTags.LAVA)) {
            RenderSystem.fogEnd(event.getFarPlaneDistance() + 10 * effect.level());
        }
    }

    public void updateSwimmingInLava() {
        boolean wasSwimmingInLava = this.isSwimmingInLava;
        boolean swimStatus;
        if (this.isSwimmingInLava) {
            swimStatus = !this.player.abilities.isFlying && this.player.isSprinting() && this.player.isInLava() && !this.player.isPassenger();
        } else {
            swimStatus = !this.player.abilities.isFlying && this.player.isSprinting() && this.player.isInLava() && this.player.areEyesInFluid(FluidTags.LAVA) && !this.player.isPassenger();
        }
        this.isSwimmingInLava = swimStatus;
        this.player.setSwimming(swimStatus);
        if (wasSwimmingInLava && !this.isSwimmingInLava) {
            this.player.setForcedPose(null);
        } else if (!wasSwimmingInLava && this.isSwimmingInLava) {
            this.player.setForcedPose(Pose.SWIMMING);
        }
    }

    private static boolean isJumping(PlayerEntity player) {
        try {
            return IS_JUMPING.getBoolean(player);
        } catch (Exception e) {
            throw new RuntimeException("Error accessing field isJumping");
        }
    }

    @Override
    public ResourceLocation getName() {
        return NAME;
    }
}
