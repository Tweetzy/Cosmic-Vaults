package ca.tweetzy.cosmicvaults.commands;

import ca.tweetzy.core.commands.AbstractCommand;
import ca.tweetzy.core.configuration.editor.PluginConfigGui;
import ca.tweetzy.cosmicvaults.CosmicVaults;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: May 21 2021
 * Time Created: 4:41 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class CommandSettings extends AbstractCommand  {

    public CommandSettings() {
        super(CommandType.PLAYER_ONLY, "settings");
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... strings) {
        Player player = (Player) sender;
        CosmicVaults.getInstance().getGuiManager().showGUI(player, new PluginConfigGui(CosmicVaults.getInstance(), CosmicVaults.getInstance().getLocale().getMessage("general.prefix").getMessage()));
        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(CommandSender commandSender, String... strings) {
        return null;
    }

    @Override
    public String getPermissionNode() {
        return "cosmicvaults.cmd.settings";
    }

    @Override
    public String getSyntax() {
        return "settings";
    }

    @Override
    public String getDescription() {
        return null;
    }
}
