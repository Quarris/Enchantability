package quarris.enchantability.mod.common.enchants.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import quarris.enchantability.api.enchants.AbstractEnchantEffect;
import quarris.enchantability.mod.common.util.ModRef;

public class SwiftChargeEnchantEffect extends AbstractEnchantEffect {

    public static final ResourceLocation NAME = ModRef.createRes("swift_charge");

    public SwiftChargeEnchantEffect(PlayerEntity player, Enchantment enchantment, int level) {
        super(player, enchantment, level);
    }

    public static void itemUse(SwiftChargeEnchantEffect effect, LivingEntityUseItemEvent.Start event) {
        ItemStack item = event.getItem();
        if (item.isFood() || item.getItem() instanceof PotionItem) {
            event.setDuration(event.getDuration()/(effect.level+1));
        }
    }

    @Override
    public ResourceLocation getName() {
        return NAME;
    }
}
