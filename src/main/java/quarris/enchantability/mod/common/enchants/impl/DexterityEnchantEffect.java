package quarris.enchantability.mod.common.enchants.impl;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerEvent;
import quarris.enchantability.api.EnchantabilityApi;
import quarris.enchantability.api.enchants.AbstractEnchantEffect;
import quarris.enchantability.mod.common.util.ModRef;
import quarris.enchantability.mod.common.util.ModUtil;

public class DexterityEnchantEffect extends AbstractEnchantEffect {

    public static final ResourceLocation NAME = ModRef.createRes("dexterity");

    public DexterityEnchantEffect(PlayerEntity player, Enchantment enchantment, int level) {
        super(player, enchantment, level);
    }

    public static void craft(DexterityEnchantEffect effect, PlayerEvent.ItemCraftedEvent event) {
        PlayerEntity player = effect.player;
        if (!player.world.isRemote()) {
            ItemStack output = event.getCrafting();
            float chance = effect.level * 0.0375f;
            if (chance >= ModUtil.RANDOM.nextFloat()) {
                for (ItemStack stack : EnchantabilityApi.DEXTERITY_ITEMSTACKS) {
                    if (ItemStack.areItemsEqual(output, stack)) {
                        ItemStack copy = output.copy();
                        copy.setCount(1);
                        ItemEntity item = new ItemEntity(player.world, player.posX, player.posY, player.posZ, copy);
                        item.setNoPickupDelay();
                        player.world.addEntity(item);
                        if (ModUtil.RANDOM.nextFloat() >= chance) {
                            player.giveExperiencePoints(effect.level);
                        }
                        return;
                    }
                }

                for (Tag<Item> tag : EnchantabilityApi.DEXTERITY_TAGS) {
                    if (tag.contains(output.getItem())) {
                        ItemStack copy = output.copy();
                        copy.setCount(1);
                        ItemEntity item = new ItemEntity(player.world, player.posX, player.posY, player.posZ, copy);
                        item.setNoPickupDelay();
                        player.world.addEntity(item);
                        if (ModUtil.RANDOM.nextFloat() >= chance) {
                            player.giveExperiencePoints(effect.level);
                        }
                        return;
                    }
                }
            }
        }
    }

    @Override
    public ResourceLocation getName() {
        return NAME;
    }
}
