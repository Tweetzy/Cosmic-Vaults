package com.kiranhart.cosmicvaults.commands;
/*
    Created by Kiran Hart
    Date: March / 09 / 2020
    Time: 11:27 a.m.
*/

import com.kiranhart.cosmicvaults.Core;
import com.kiranhart.cosmicvaults.api.CosmicVaultAPI;
import com.kiranhart.cosmicvaults.commands.subcommands.ReloadCommand;
import com.kiranhart.cosmicvaults.inventories.PlayerVaultInventory;
import com.kiranhart.cosmicvaults.inventories.VaultSelectionInventory;
import com.kiranhart.cosmicvaults.statics.CosmicLang;
import com.kiranhart.cosmicvaults.statics.CosmicPerm;
import com.kiranhart.cosmicvaults.utils.Debugger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class CommandManager implements CommandExecutor {


    private ArrayList<Subcommand> commands = new ArrayList<>();

    private final String MAIN = "playervaults";

    public void init() {
        Core.getInstance().getCommand(MAIN).setExecutor(this);
        commands.add(new ReloadCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission(CosmicPerm.BASE_COMMAND)) {
            Core.getInstance().getLocale().getMessage(CosmicLang.NO_PERMISSION).sendPrefixedMessage(sender);
            return true;
        }

        if (command.getName().equalsIgnoreCase(MAIN)) {

            if (args.length == 0) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    p.openInventory(new VaultSelectionInventory(p).getInventory());
                }
                return true;
            }

            if (CosmicVaultAPI.get().isInt(args[0])) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    if (Integer.parseInt(args[0]) <= 0) {
                        Core.getInstance().getLocale().getMessage(CosmicLang.VAULT_ZERO).sendPrefixedMessage(sender);
                        return true;
                    }

                    if (CosmicVaultAPI.canUseVault(p, Integer.parseInt(args[0]))) {
                        p.openInventory(new PlayerVaultInventory(p, Integer.parseInt(args[0])).getInventory());
                    } else {
                        Core.getInstance().getLocale().getMessage(CosmicLang.VAULT_NO_PERMISSION).processPlaceholder("vault_number", Integer.parseInt(args[0])).sendPrefixedMessage(sender);
                    }
                }
                return true;
            }

            Subcommand target = this.getSubcommand(args[0]);

            if (target == null) {
                Core.getInstance().getLocale().getMessage(CosmicLang.INVALID_COMMAND).sendPrefixedMessage(sender);
                return true;
            }

            ArrayList<String> list = new ArrayList<>();
            list.addAll(Arrays.asList(args));
            list.remove(0);

            try {
                target.onCommand(sender, args);
            } catch (Exception e) {
                Debugger.report(e, false);
            }
        }

        return true;
    }

    private Subcommand getSubcommand(String name) {
        Iterator<Subcommand> subcommands = this.commands.iterator();
        while (subcommands.hasNext()) {
            Subcommand sc = subcommands.next();

            if (sc.name().equalsIgnoreCase(name)) {
                return sc;
            }

            String[] aliases;
            int length = (aliases = sc.aliases()).length;

            for (int i = 0; i < length; ++i) {
                String alias = aliases[i];
                if (name.equalsIgnoreCase(alias)) {
                    return sc;
                }

            }
        }
        return null;
    }
}
