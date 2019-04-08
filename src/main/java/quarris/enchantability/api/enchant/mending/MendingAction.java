package quarris.enchantability.api.enchant.mending;

import java.util.function.Consumer;

public class MendingAction {

	public final Consumer<MendingResult> result;
	public final int cost;

	public MendingAction(Consumer<MendingResult> result, int cost) {
		this.result = result;
		this.cost = cost;
	}
}
