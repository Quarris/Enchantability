package quarris.enchantability.mod.common.enchants.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
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
            Item item = player.getHeldItemMainhand().getItem();
            if ((item instanceof SwordItem || item instanceof AxeItem) && !player.isSwingInProgress) {
                float chance = 0.05f * effect.level();
                if (chance >= ModUtil.RANDOM.nextFloat()) {
                    Entity target = event.getTarget();
                    LightningBoltEntity bolt = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, player.world);
                    bolt.setPosition(target.getPosX(), target.getPosY(), target.getPosZ());
                    bolt.setCaster((ServerPlayerEntity)player);
                    bolt.getPersistentData().putUniqueId("Enchantibility:AvoidPlayer", player.getUniqueID());
                    player.world.addEntity(bolt);
                }
            }
        }
    }

    public static void avoidPlayer(SmiteEnchantEffect effect, EntityStruckByLightningEvent event) {
        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = effect.player;
            if (!player.world.isRemote()) {
                CompoundNBT data = event.getLightning().getPersistentData();
                if (data.hasUniqueId("Enchantibility:AvoidPlayer") && data.getUniqueId("Enchantibility:AvoidPlayer").equals(player.getUniqueID())) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @Override
    public ResourceLocation getName() {
        return NAME;
    }
}
