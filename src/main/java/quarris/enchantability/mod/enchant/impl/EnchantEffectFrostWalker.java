package quarris.enchantability.mod.enchant.impl;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import quarris.enchantability.api.enchant.AbstractEnchantEffect;
import quarris.enchantability.mod.Enchantability;

import javax.annotation.Nonnull;

public class EnchantEffectFrostWalker extends AbstractEnchantEffect {

    @Override
    public void onTick(EntityPlayer player, int tier) {
        World world = player.world;
        if (!world.isRemote && (player.isSneaking() || (player.onGround && world.getBlockState(new BlockPos(player).down()).getBlock() == Enchantability.AIR_ICE))) {
            BlockPos pos = new BlockPos(player);
            float f = (float) Math.min(4, tier);
            BlockPos.MutableBlockPos mutPosUp = new BlockPos.MutableBlockPos(0, 0, 0);

            for (BlockPos.MutableBlockPos mutPos : BlockPos.getAllInBoxMutable(pos.add((double) (-f), -1.0D, (double) (-f)), pos.add((double) f, -1.0D, (double) f))) {
                if (mutPos.distanceSqToCenter(player.posX, player.posY, player.posZ) <= (double) (f * f)) {
                    mutPosUp.setPos(mutPos.getX(), mutPos.getY() + 1, mutPos.getZ());
                    IBlockState stateUp = world.getBlockState(mutPosUp);

                    if (stateUp.getMaterial() == Material.AIR) {
                        IBlockState state = world.getBlockState(mutPos);

                        if (state.getMaterial() == Material.AIR &&
                                state.getBlock() == Blocks.AIR &&
                                world.mayPlace(Enchantability.AIR_ICE, mutPos, false, EnumFacing.DOWN, null)) {

                            world.setBlockState(mutPos, Enchantability.AIR_ICE.getDefaultState());
                            world.scheduleUpdate(mutPos.toImmutable(), Enchantability.AIR_ICE, MathHelper.getInt(player.getRNG(), 60, 120));
                        }
                    }
                }
            }
        }
    }

    @Nonnull
    @Override
    public Enchantment getEnchantment() {
        return Enchantments.FROST_WALKER;
    }
}
