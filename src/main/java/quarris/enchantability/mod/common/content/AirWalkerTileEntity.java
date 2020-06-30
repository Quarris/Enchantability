package quarris.enchantability.mod.common.content;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.registries.ObjectHolder;
import quarris.enchantability.api.EnchantabilityApi;
import quarris.enchantability.api.enchants.IEnchantEffect;
import quarris.enchantability.mod.common.enchants.impl.AirWalkerEnchantEffect;
import quarris.enchantability.mod.common.util.ModRef;

import java.util.List;

public class AirWalkerTileEntity extends TileEntity implements ITickableTileEntity {

    @ObjectHolder(ModRef.ID + ":air")
    public static TileEntityType<AirWalkerTileEntity> TYPE;

    public AirWalkerTileEntity() {
        super(TYPE);
    }

    @Override
    public void tick() {
        if (this.world == null || this.world.isRemote() || this.world.getGameTime() % 10 != 0) return;

        List<PlayerEntity> players = world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB(pos).expand(0, 3, 0).grow(5, 0, 5), p -> p.getCapability(EnchantabilityApi.playerEnchant).isPresent());

        boolean shouldRemove = true;
        for (PlayerEntity player : players) {
            IEnchantEffect effect = player.getCapability(EnchantabilityApi.playerEnchant).orElse(null).getEnchant(AirWalkerEnchantEffect.NAME);
            if (effect != null) {
                float range = effect.level() + 1.5f;
                if (player.getDistanceSq(new Vector3d(pos.getX(), pos.getY(), pos.getZ())) <= (range * range)) {
                    shouldRemove = false;
                }
            }
        }

        if (shouldRemove)
            world.removeBlock(pos, false);
    }
}
