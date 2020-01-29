package quarris.enchantability.mod.common.content;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import quarris.enchantability.api.EnchantabilityApi;
import quarris.enchantability.api.enchants.IEnchantEffect;
import quarris.enchantability.mod.common.enchants.impl.AirWalkerEnchantEffect;

import java.util.List;
import java.util.Random;

public class AirWalkerBlock extends Block {

    public AirWalkerBlock() {
        super(Block.Properties.create(Material.BARRIER).noDrops().tickRandomly());
        this.setRegistryName("air");
    }

    public void tick(BlockState state, World world, BlockPos pos, Random random) {
        List<PlayerEntity> players = world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB(pos).expand(0, 3, 0).grow(5, 0, 5), p -> p.getCapability(EnchantabilityApi.playerEnchant).isPresent());

        boolean shouldRemove = true;
        for (PlayerEntity player : players) {
            IEnchantEffect effect = player.getCapability(EnchantabilityApi.playerEnchant).orElse(null).getEnchant(AirWalkerEnchantEffect.NAME);
            if (effect != null && player.getDistanceSq(new Vec3d(pos)) <= effect.level()) {
                shouldRemove = false;
            }
        }

        if (shouldRemove)
            world.removeBlock(pos, false);
    }

    @Override
    public int tickRate(IWorldReader worldIn) {
        return 3;
    }

    @Override
    public boolean isAir(BlockState state, IBlockReader world, BlockPos pos) {
        return true;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }
}
