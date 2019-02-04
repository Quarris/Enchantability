package quarris.enchantability.mod.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import quarris.enchantability.mod.capability.player.CapabilityHandler;
import quarris.enchantability.mod.capability.player.enchant.IPlayerEnchHandler;

import java.util.List;

public class TileAirIce extends TileEntity implements ITickable {
    
    @Override
    public void update() {
        if (!world.isRemote) {
            IBlockState state = world.getBlockState(pos);
            AxisAlignedBB aabb = state.getBoundingBox(world, pos).offset(pos.getX(), pos.getY()+1, pos.getZ()).grow(2, 0, 2);

            List<EntityPlayer> playerList = world.getEntitiesWithinAABB(EntityPlayer.class, aabb, (entity) -> {
                IPlayerEnchHandler cap = entity.getCapability(CapabilityHandler.PLAYER_ENCHANT_CAPABILITY, null);
                if (cap != null) {
                    int tier = cap.hasEnchant(Enchantments.FROST_WALKER);
                    if (tier > 0) {
                        int level = Math.min(4, tier+1);
                        if (entity.getDistanceSqToCenter(pos) <= level * level) {
                            return true;
                        }
                    }
                }
                return false;
            });

            if (playerList.isEmpty()) {
                world.setBlockToAir(pos);
            }
        }
    }
    
    
}
