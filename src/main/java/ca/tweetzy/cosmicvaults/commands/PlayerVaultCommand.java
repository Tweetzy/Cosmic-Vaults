package ca.tweetzy.cosmicvaults.commands;

import ca.tweetzy.core.commands.AbstractCommand;
import ca.tweetzy.core.utils.NumberUtils;
import ca.tweetzy.cosmicvaults.CosmicVaults;
import ca.tweetzy.cosmicvaults.api.CosmicVaultAPI;
import ca.tweetzy.cosmicvaults.guis.PlayerVaultGUI;
import ca.tweetzy.cosmicvaults.guis.VaultSelectionGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/15/2020
 * Time Created: 2:55 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class PlayerVaultCommand extends AbstractCommand {

    public PlayerVaultCommand() {
        super(CommandType.CONSOLE_OK, "playervaults");
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {
        //if (args.length != 1) return ReturnType.SYNTAX_ERROR;

        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (args.length == 0) {
                // open selection gui
                CosmicVaults.getInstance().getGuiManager().showGUI(p, new VaultSelectionGUI(p));
                return ReturnType.SUCCESS;
            }

            if (args.length == 1) {
                if (NumberUtils.isInt(args[0])) {
                    if (Integer.parseInt(args[0]) <= 0) {
                        CosmicVaults.getInstance().getLocale().getMessage("vaultzero").sendPrefixedMessage(p);
                        return ReturnType.SYNTAX_ERROR;
                    }

                    if (CosmicVaultAPI.get().canUseVault(p, Integer.parseInt(args[0]))) {
                        if (CosmicVaults.getInstance().getOpenedVault().containsKey(p.getUniqueId()) || CosmicVaults.getInstance().getAdminEdit().containsValue(p.getUniqueId())) {
                            CosmicVaults.getInstance().getLocale().getMessage("vaultopenalready").sendPrefixedMessage(p);
                            return ReturnType.FAILURE;
                        }

                        CosmicVaults.getInstance().getGuiManager().showGUI(p, new PlayerVaultGUI(p.getUniqueId(), Integer.parseInt(args[0])));
                        CosmicVaults.getInstance().getOpenedVault().put(p.getUniqueId(), Integer.parseInt(args[0]));
                    } else {
                        CosmicVaults.getInstance().getLocale().getMessage("pv.no-permission").processPlaceholder("vault_number", Integer.parseInt(args[0])).sendPrefixedMessage(sender);
                    }
                }
                return ReturnType.SUCCESS;
            }

        } else {
            // console
        }

        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(CommandSender sender, String... args) {
        return null;
    }

    @Override
    public String getPermissionNode() {
        return "cosmicvaults.cmd";
    }

    @Override
    public String getSyntax() {
        return "/playervault";
    }

    @Override
    public String getDescription() {
        return "Primary command for the Cosmic Vault Plugin";
    }
}
