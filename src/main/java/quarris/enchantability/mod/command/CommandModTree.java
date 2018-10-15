package quarris.enchantability.mod.command;

import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;

public class CommandModTree extends CommandTreeBase {

    public CommandModTree() {
        addSubcommand(new CommandAddEnchant());
        addSubcommand(new CommandRemoveEnchant());
        addSubcommand(new CommandGetEnchant());
    }

    @Override
    public String getName() {
        return "ench";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/ench <add> <Player> <Enchantment> [tier] OR  /ench <remove> <Player> <Enchantment> OR /ench <get> <Player>";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 4;
    }
}
