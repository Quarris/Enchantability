package quarris.enchantability.mod.common.enchants.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.LogicalSide;
import quarris.enchantability.api.enchants.AbstractEnchantEffect;
import quarris.enchantability.mod.ModConfig;
import quarris.enchantability.mod.common.util.ModRef;

import java.util.ArrayList;
import java.util.List;

public class HeatEnchantEffect extends AbstractEnchantEffect {

    public static final ResourceLocation NAME = ModRef.createRes("heat");

    public HeatEnchantEffect(PlayerEntity player, Enchantment enchantment, int level) {
        super(player, enchantment, level);
    }

    public static void heat(HeatEnchantEffect effect, TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START) {
            PlayerEntity player = effect.player;
            ServerWorld world = (ServerWorld) player.world;

            ModConfig config = ModConfig.get();
            int ticks = config.additionalTickSpeed.get();
            List<String> tileBlacklist = config.tileBlacklist.get();
            boolean treatAsWhitelist = config.treatBlacklistAsWhitelist.get();

            List<TileEntity> tiles = getTileEntitiesInArea(world, player.getPosition(), 32);
            for (TileEntity tile : tiles) {
                if (tile instanceof ITickableTileEntity) {
                    if (treatAsWhitelist == tileBlacklist.contains(tile.getType().getRegistryName().toString())) {
                        for (int i = 0; i < ticks; i++) {
                            ((ITickableTileEntity) tile).tick();
                        }
                    }
                }
            }
        }
    }

    private static List<TileEntity> getTileEntitiesInArea(IWorld world, BlockPos pos, int radius) {
        List<TileEntity> list = new ArrayList<>();
        for (int x = pos.getX() - radius >> 4; x <= pos.getX() + radius >> 4; x++) {
            for (int z = pos.getZ() - radius >> 4; z <= pos.getZ() + radius >> 4; z++) {
                IChunk chunk = world.getChunk(x, z);
                for (BlockPos tilePos : chunk.getTileEntitiesPos()) {
                    if (tilePos.distanceSq(pos) <= radius * radius)
                        list.add(world.getTileEntity(tilePos));
                }
            }
        }
        return list;
    }

    @Override
    public ResourceLocation getName() {
        return NAME;
    }
}
