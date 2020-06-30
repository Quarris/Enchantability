package quarris.enchantability.mod.common.content;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import quarris.enchantability.api.EnchantabilityApi;
import quarris.enchantability.api.capabilities.IPlayerEnchant;
import quarris.enchantability.mod.common.enchants.impl.AirWalkerEnchantEffect;

import javax.annotation.Nullable;

public class AirWalkerBlock extends Block {

    public static final VoxelShape SHAPE = VoxelShapes.create(0, 0.9, 0, 1, 1, 1);

    public AirWalkerBlock() {
        super(Block.Properties.create(Material.BARRIER).noDrops().variableOpacity());
        this.setRegistryName("air");
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return AirWalkerTileEntity.TYPE.create();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null && context.getEntity() != null) {
            IPlayerEnchant cap = context.getEntity().getCapability(EnchantabilityApi.playerEnchant).orElse(null);
            if (cap == null || !cap.hasEnchant(AirWalkerEnchantEffect.NAME) || context.getEntity().getPosY() < (pos.getY() + 1)) {
                return VoxelShapes.empty();
            } else {
                return SHAPE;
            }
        }
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }
}
