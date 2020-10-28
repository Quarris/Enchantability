package quarris.enchantability.mod.common.content;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ObjectHolder;
import quarris.enchantability.api.EnchantabilityApi;

import java.util.List;
import java.util.UUID;

public class WitherHeartItem extends Item {

    @ObjectHolder("enchantability:wither_heart")
    public static Item WITHER_HEART;

    public static Food witherHeartFood = new Food.Builder()
            .setAlwaysEdible().build();

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

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (stack.getItem() != WitherHeartItem.WITHER_HEART)
            return;

        IFormattableTextComponent translation;

        if (stack.getTag() == null) {
            translation = new TranslationTextComponent("wither_heart.tooltip.no_owner");
        } else {
            UUID ownerUUID = stack.getTag().contains("Owner") ?
                    stack.getTag().getUniqueId("Owner") : null;
            String ownerName = stack.getTag().contains("OwnerName") ?
                    stack.getTag().getString("OwnerName") :
                    "Unknown"+(ownerUUID == null ?
                            "" : "{"+ownerUUID+"}");
            translation = new TranslationTextComponent("wither_heart.tooltip.heart_owner", ownerName);
        }

        tooltip.add(new TranslationTextComponent("wither_heart.tooltip.only_worthy").mergeStyle(TextFormatting.RED));
        tooltip.add(translation.mergeStyle(TextFormatting.WHITE));
    }
}
