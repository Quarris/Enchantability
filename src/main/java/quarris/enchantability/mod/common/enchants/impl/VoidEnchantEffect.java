package quarris.enchantability.mod.common.enchants.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.TickEvent;
import quarris.enchantability.api.enchants.AbstractEnchantEffect;
import quarris.enchantability.mod.common.util.ModRef;

public class VoidEnchantEffect extends AbstractEnchantEffect {

    public static final ResourceLocation NAME = ModRef.createRes("void");

    public VoidEnchantEffect(PlayerEntity player, Enchantment enchantment, int level) {
        super(player, enchantment, level);
    }

    public static void voidTeleport(VoidEnchantEffect effect, TickEvent.PlayerTickEvent event) {
        PlayerEntity player = effect.player;
        if (event.phase == TickEvent.Phase.START && !player.world.isRemote()) {

            /* TODO Waiting for mappings
            if (player.world.func_230315_m_() == Registry. DimensionType.func_236019_a_() && player.getPosY() <= -60) {
                player.changeDimension(DimensionType.OVERWORLD);
            }
             */
        }
    }

    @Override
    public ResourceLocation getName() {
        return NAME;
    }
}
