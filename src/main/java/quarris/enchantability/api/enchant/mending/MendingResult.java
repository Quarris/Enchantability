package quarris.enchantability.api.enchant.mending;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class MendingResult {

	public final ItemStack result;
	public final EntityPlayer player;
	public final int tier;

	public MendingResult(ItemStack result, EntityPlayer player, int tier) {
		this.result = result;
		this.player = player;
		this.tier = tier;
	}
}
