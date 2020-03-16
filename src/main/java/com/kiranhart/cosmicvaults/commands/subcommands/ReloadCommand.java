package com.kiranhart.cosmicvaults.commands.subcommands;

import com.kiranhart.cosmicvaults.Core;
import com.kiranhart.cosmicvaults.commands.Subcommand;
import com.kiranhart.cosmicvaults.statics.CosmicLang;
import com.kiranhart.cosmicvaults.statics.CosmicPerm;
import org.bukkit.command.CommandSender;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 3/16/2020
 * Time Created: 2:34 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class ReloadCommand extends Subcommand {

    @Override
    public void onCommand(CommandSender sender, String[] args) {

        if (!sender.hasPermission(CosmicPerm.RELOAD_COMMAND)) {
            Core.getInstance().getLocale().getMessage(CosmicLang.NO_PERMISSION).sendPrefixedMessage(sender);
            return;
        }

        Core.getInstance().reloadConfig();
        Core.getInstance().getDataFile().reloadConfig();
        Core.getInstance().getLocale().getMessage(CosmicLang.RELOADED).sendPrefixedMessage(sender);
    }

    @Override
    public String name() {
        return "reload";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
