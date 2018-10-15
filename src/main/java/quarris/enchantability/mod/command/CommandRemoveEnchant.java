package quarris.enchantability.mod.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import org.apache.commons.lang3.tuple.Pair;
import quarris.enchantability.mod.capability.player.CapabilityHandler;
import quarris.enchantability.mod.capability.player.enchant.IPlayerEnchHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandRemoveEnchant extends CommandBase {
    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/ench remove <Enchantment>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 2) {
            throw new CommandException("Usage: " + getUsage(sender));
        }
        EntityPlayer player = server.getPlayerList().getPlayerByUsername(args[0]);
        Enchantment enchantment = Enchantment.getEnchantmentByLocation(args[1]);
        if (player == null) {
            throw new CommandException("Player '" + args[0] + "' does not exist.");
        }
        if (enchantment == null) {
            throw new CommandException("Enchantment '" + args[1] + "' does not exist.");
        }
        IPlayerEnchHandler cap = player.getCapability(CapabilityHandler.PLAYER_ENCHANT_CAPABILITY, null);
        if (cap != null) {
            cap.removeEnchant(enchantment);
            sender.sendMessage(new TextComponentString("Successfully removed "+ I18n.translateToLocal(enchantment.getName()) +" from "+player.getName()));
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, server.getPlayerList().getOnlinePlayerNames());
        }
        else if (args.length == 2) {
            List<String> enchantments = new ArrayList<>();
            EntityPlayer player = server.getPlayerList().getPlayerByUsername(args[0]);
            if (player != null) {
                IPlayerEnchHandler cap = player.getCapability(CapabilityHandler.PLAYER_ENCHANT_CAPABILITY, null);
                if (cap != null) {
                    for (Pair<Enchantment, Integer> pair : cap.getEnchants()) {
                        enchantments.add(pair.getLeft().getRegistryName().toString());
                    }
                    return getListOfStringsMatchingLastWord(args, enchantments);
                }
            }
        }
        return Collections.emptyList();
    }
}
