package quarris.enchantability.mod.common.enchants.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import quarris.enchantability.api.enchants.AbstractEnchantEffect;
import quarris.enchantability.mod.common.util.ModRef;
import quarris.enchantability.mod.common.util.ModUtil;

public class SmiteEnchantEffect extends AbstractEnchantEffect {

    public static final ResourceLocation NAME = ModRef.createRes("smite");

    public SmiteEnchantEffect(PlayerEntity player, Enchantment enchantment, int level) {
        super(player, enchantment, level);
    }

    public static void smite(SmiteEnchantEffect effect, AttackEntityEvent event) {
        PlayerEntity player = effect.player;
        if (!player.world.isRemote()) {
            if (player.getHeldItemMainhand().getItem() instanceof SwordItem && !player.isSwingInProgress) {
                float chance = 0.05f * effect.level;
                if (chance > ModUtil.RANDOM.nextFloat()) {
                    Entity target = event.getTarget();
                    LightningBoltEntity bolt = new LightningBoltEntity(player.world, target.posX, target.posY, target.posZ, false);
                    bolt.setCaster((ServerPlayerEntity)player);
                    ((ServerWorld)player.world).addLightningBolt(bolt);
                }
            }
        }
    }

    @Override
    public ResourceLocation getName() {
        return NAME;
    }
}
