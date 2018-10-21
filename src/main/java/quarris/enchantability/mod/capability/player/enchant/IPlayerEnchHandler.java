package quarris.enchantability.mod.capability.player.enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagList;
import org.apache.commons.lang3.tuple.Pair;
import quarris.enchantability.mod.capability.player.IModCapability;

import java.util.List;

public interface IPlayerEnchHandler extends IModCapability {

    /**
     * @return the player with the capability.
     */
    EntityPlayer getPlayer();

    /**
     * @return the list of enchant that the player holds.
     */
    List<Pair<Enchantment, Integer>> getEnchants();

    /**
     * Checks if the player has the enchant with any tier.
     * @see IPlayerEnchHandler#hasEnchant(Enchantment, int)
     * @param enchant the enchant to check.
     * @return the tier of the enchant. 0 if no enchant of the type.
     */
    int hasEnchant(Enchantment enchant);

    /**
     * Checks if the player has the enchant with a specific tier.
     * @see IPlayerEnchHandler#hasEnchant(Enchantment)
     * @param enchant the enchant to check.
     * @param tier the tier
     * @return whether the player has the enchant.
     */
    boolean hasEnchant(Enchantment enchant, int tier);

    /**
     * Adds an enchant to the list.
     * @param enchant The enchant to add.
     * @param tier The tier of the enchant to add
     */
    void addEnchant(Enchantment enchant, int tier);

    void clearEnchants();

    boolean isDirty();

    void markDirty();

    void markClean();

    NBTTagList serializeNBT();

    void deserializeNBT(NBTTagList nbt);
}
