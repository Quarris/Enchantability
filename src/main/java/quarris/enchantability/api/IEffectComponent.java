package quarris.enchantability.api;

import net.minecraftforge.eventbus.api.Event;
import quarris.enchantability.api.enchants.IEnchantEffect;

public interface IEffectComponent<F extends IEnchantEffect, T extends Event> {

    void run(F effect, T event);

}
