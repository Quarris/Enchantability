package quarris.enchantability.api.enchant.mending;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class MendingResult {

	public final ItemStack item;
	public final EntityPlayer player;
	public final int tier;

	public MendingResult(ItemStack item, EntityPlayer player, int tier) {
		this.item = item;
		this.player = player;
		this.tier = tier;
	}
}
