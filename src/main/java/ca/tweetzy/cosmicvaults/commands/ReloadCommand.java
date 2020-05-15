package ca.tweetzy.cosmicvaults.commands;

import ca.tweetzy.core.commands.AbstractCommand;
import ca.tweetzy.cosmicvaults.CosmicVaults;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/15/2020
 * Time Created: 3:43 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class ReloadCommand extends AbstractCommand {

    public ReloadCommand() {
        super(CommandType.CONSOLE_OK, "reload");
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {

        CosmicVaults.getInstance().reloadConfig();
        CosmicVaults.getInstance().getDataFile().reloadConfig();
        CosmicVaults.getInstance().getLocale().getMessage("reloaded").sendPrefixedMessage(sender);

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
        return "Reload plugin files";
    }

    @Override
    protected List<String> onTab(CommandSender sender, String... args) {
        return null;
    }
}
