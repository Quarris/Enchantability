package quarris.enchantability.mod.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import org.apache.commons.lang3.tuple.Pair;
import quarris.enchantability.mod.capability.player.CapabilityHandler;
import quarris.enchantability.mod.capability.player.enchant.IPlayerEnchHandler;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CommandGetEnchant extends CommandBase {
    @Override
    public String getName() {
        return "get";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/ench get <Player>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 1) {
            throw new CommandException("Usage: " + getUsage(sender));
        }
        EntityPlayer player = server.getPlayerList().getPlayerByUsername(args[0]);
        if (player == null) {
            throw new CommandException("Player '" + args[0] + "' does not exist.");
        }
        IPlayerEnchHandler cap = player.getCapability(CapabilityHandler.PLAYER_ENCHANT_CAPABILITY, null);
        if (cap != null) {
            List<Pair<Enchantment, Integer>> pairs = cap.getEnchants();
            if (pairs.size() == 0) {
                sender.sendMessage(new TextComponentString(player.getName() + " does not have any enchants."));
            }
            else {
                sender.sendMessage(new TextComponentString(player.getName() + " has the following enchants:"));
                StringBuilder output = new StringBuilder();
                for (int i = 0; i < cap.getEnchants().size(); i++) {
                    output.append(pairs.get(i).getLeft().getTranslatedName(pairs.get(i).getRight()));
                    if (i < pairs.size() - 1) {
                        output.append(", ");
                    }
                }
                sender.sendMessage(new TextComponentString(output.toString()));
            }
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, server.getPlayerList().getOnlinePlayerNames());
        }
        return Collections.emptyList();
    }
}
