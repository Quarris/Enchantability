package quarris.enchantability.mod.common.content;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ObjectHolder;
import quarris.enchantability.api.EnchantabilityApi;

public class WitherHeartItem extends Item {

    @ObjectHolder("enchantability:wither_heart")
    public static Item WITHER_HEART;

    public static Food witherHeartFood = new Food.Builder()
            .setAlwaysEdible()
            .effect(() -> new EffectInstance(Effects.WITHER, 200, 9), 1f).build();

    public WitherHeartItem() {
        super(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(1).food(witherHeartFood).rarity(Rarity.EPIC));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        if (player.getCapability(EnchantabilityApi.playerEnchant).orElseGet(null).isExtended()) {
            player.sendStatusMessage(new TranslationTextComponent("wither_heart.already_eaten"), true);
            return ActionResult.resultFail(player.getHeldItem(hand));
        }

        return super.onItemRightClick(world, player, hand);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entity) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;

            if (stack.getTag() == null || player.getUniqueID().equals(stack.getTag().getUniqueId("Owner"))) {
                player.getCapability(EnchantabilityApi.playerEnchant).ifPresent(cap -> cap.setExtended(true));
                player.sendStatusMessage(new TranslationTextComponent("wither_heart.eaten"), true);
            } else {
                player.sendStatusMessage(new TranslationTextComponent("wither_heart.not_worthy").mergeStyle(TextFormatting.RED), true);
            }
        }
        return super.onItemUseFinish(stack, worldIn, entity);
    }
}
