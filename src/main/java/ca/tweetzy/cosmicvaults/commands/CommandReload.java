package ca.tweetzy.cosmicvaults.commands;

import ca.tweetzy.core.commands.AbstractCommand;
import ca.tweetzy.cosmicvaults.CosmicVaults;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: June 18 2021
 * Time Created: 1:51 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class CommandReload extends AbstractCommand {

    public CommandReload() {
        super(CommandType.CONSOLE_OK, "reload");
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {
        CosmicVaults.getInstance().reloadConfig();
        return ReturnType.SUCCESS;
    }

    @Override
    public String getPermissionNode() {
        return "cosmicvaults.cmd.reload";
    }

    @Override
    public String getSyntax() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Used to reload the vaults configuration files";
    }

    @Override
    protected List<String> onTab(CommandSender sender, String... args) {
        return null;
    }
}
