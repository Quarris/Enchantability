package quarris.enchantability.mod.common.enchants.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.LogicalSide;
import quarris.enchantability.api.enchants.AbstractEnchantEffect;
import quarris.enchantability.mod.common.util.ModRef;

public class VoidEnchantEffect extends AbstractEnchantEffect {

    public static final ResourceLocation NAME = ModRef.createRes("void");

    public VoidEnchantEffect(PlayerEntity player, Enchantment enchantment, int level) {
        super(player, enchantment, level);
    }

    public static void voidTeleport(VoidEnchantEffect effect, TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START && event.side == LogicalSide.SERVER) {
            ServerPlayerEntity player = (ServerPlayerEntity) effect.player;
            ServerWorld playerWorld = player.getServerWorld();
            ServerWorld toWorld = player.server.getWorld(World.field_234918_g_);
            if (toWorld == null)
                return;

            if (playerWorld.func_234923_W_().func_240901_a_().equals(DimensionType.field_242712_c) && player.getPosY() <= -60) {
                player.changeDimension(toWorld);
            }
        }
    }

    @Override
    public ResourceLocation getName() {
        return NAME;
    }
}
