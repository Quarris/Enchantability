package quarris.enchantability.mod.common.enchants.impl;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import quarris.enchantability.api.enchants.AbstractEnchantEffect;
import quarris.enchantability.mod.common.util.ModRef;

public class AirWalkerEnchantEffect extends AbstractEnchantEffect {

    public static final ResourceLocation NAME = ModRef.createRes("air_walker");

    public AirWalkerEnchantEffect(PlayerEntity player, Enchantment enchantment, int level) {
        super(player, enchantment, level);
    }

    public static void airWalk(AirWalkerEnchantEffect effect, TickEvent.PlayerTickEvent event) {
        PlayerEntity player = effect.player;
        if (!player.world.isRemote() && event.phase == TickEvent.Phase.END) {
            World world = player.world;
            if (player.isShiftKeyDown() || (player.onGround && world.getBlockState(new BlockPos(player).down()).getBlock() == ModRef.AIR)) {
                BlockPos pos = new BlockPos(player);
                float f = (float) Math.min(4, effect.level);
                BlockPos.Mutable mutPosUp = new BlockPos.Mutable(0, 0, 0);

                for (BlockPos mutPos : BlockPos.getAllInBoxMutable(pos.add((double) (-f), -1.0D, (double) (-f)), pos.add((double) f, -1.0D, (double) f))) {
                    if (mutPos.distanceSq(player.getPosX(), player.getPosY(), player.getPosZ(), true) <= (double) (f * f)) {
                        mutPosUp.setPos(mutPos.getX(), mutPos.getY() + 1, mutPos.getZ());
                        BlockState stateUp = world.getBlockState(mutPosUp);

                        if (stateUp.getMaterial() == Material.AIR) {
                            BlockState state = world.getBlockState(mutPos);

                            if (state.getMaterial() == Material.AIR) {
                                world.setBlockState(mutPos, ModRef.AIR.getDefaultState());
                                ((ServerWorld)world).getPendingBlockTicks()
                                        .scheduleTick(mutPos.toImmutable(), ModRef.AIR, MathHelper.nextInt(player.getRNG(), 60, 120));
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public ResourceLocation getName() {
        return NAME;
    }
}
