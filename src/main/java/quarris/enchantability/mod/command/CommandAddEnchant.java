package quarris.enchantability.mod.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import quarris.enchantability.mod.capability.player.CapabilityHandler;
import quarris.enchantability.mod.capability.player.enchant.IPlayerEnchHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CommandAddEnchant extends CommandBase {
    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/ench add <Player> <Enchantment> [tier]";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 2 || args.length > 3) {
            throw new CommandException("Usage: " + getUsage(sender));
        }
        EntityPlayer player = server.getPlayerList().getPlayerByUsername(args[0]);
        Enchantment enchantment = Enchantment.getEnchantmentByLocation(args[1]);
        int tier = 1;
        if (player == null) {
            throw new CommandException("Player '" + args[0] + "' does not exist.");
        }
        if (enchantment == null) {
            throw new CommandException("Enchantment '" + args[1] + "' does not exist.");
        }
        if (args.length == 3) {
            try {
                tier = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                throw new CommandException("The given tier does not exist");
            }
            if (tier < 1 || tier > enchantment.getMaxLevel()) {
                throw new CommandException("The tier is not within the range of the enchantment. Must be between 1 and "+enchantment.getMaxLevel());
            }
        }
        IPlayerEnchHandler cap = player.getCapability(CapabilityHandler.PLAYER_ENCHANT_CAPABILITY, null);
        if (cap != null) {
            cap.addEnchant(enchantment, tier);
            sender.sendMessage(new TextComponentString("Successfully added "+enchantment.getTranslatedName(tier)+" to "+player.getName()));
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, server.getPlayerList().getOnlinePlayerNames());
        }
        else if (args.length == 2) {
            List<String> enchantments = new ArrayList<>();
            for (ResourceLocation enchLocation : Enchantment.REGISTRY.getKeys()) {
                enchantments.add(enchLocation.toString());
            }
            return getListOfStringsMatchingLastWord(args, enchantments);
        }
        else if (args.length == 3) {
            Enchantment ench = Enchantment.getEnchantmentByLocation(args[1]);
            if (ench != null) {
                List<String> list = new ArrayList<>();
                for (int i = 1; i <= ench.getMaxLevel(); i++) {
                    list.add(String.valueOf(i));
                }
                return list;
            }
        }
        return Collections.emptyList();
    }
}
