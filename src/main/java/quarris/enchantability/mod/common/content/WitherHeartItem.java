package quarris.enchantability.mod.common.content;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import quarris.enchantability.api.EnchantabilityApi;

public class WitherHeartItem extends Item {

    public static Food witherHeartFood = new Food.Builder()
            .setAlwaysEdible()
            .hunger(100)
            .saturation(100)
            .effect(() -> new EffectInstance(Effects.WITHER, 200, 9), 1f).build();

    public WitherHeartItem() {
        super(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(1).food(witherHeartFood).rarity(Rarity.EPIC));
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        if (context.getPlayer().getCapability(EnchantabilityApi.playerEnchant).orElseGet(null).isExtended()) {
            context.getPlayer().sendStatusMessage(new TranslationTextComponent("wither_heart.already_eaten"), true);
            return ActionResultType.FAIL;
        }

        return ActionResultType.PASS;
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) entityLiving;
            playerEntity.getCapability(EnchantabilityApi.playerEnchant).ifPresent(cap -> cap.setExtended(true));
        }
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }
}
