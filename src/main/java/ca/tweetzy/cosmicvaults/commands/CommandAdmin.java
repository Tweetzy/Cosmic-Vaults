package ca.tweetzy.cosmicvaults.commands;

import ca.tweetzy.core.commands.AbstractCommand;
import ca.tweetzy.core.utils.NumberUtils;
import ca.tweetzy.cosmicvaults.CosmicVaults;
import ca.tweetzy.cosmicvaults.guis.PlayerVaultGUI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/15/2020
 * Time Created: 4:25 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class CommandAdmin extends AbstractCommand {

    public CommandAdmin() {
        super(CommandType.PLAYER_ONLY, "admin");
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {
        if (args.length != 2) return ReturnType.SYNTAX_ERROR;
        Player p = (Player) sender;

        Player targetPlayer = Bukkit.getPlayerExact(args[0]);
        UUID target = targetPlayer != null ? targetPlayer.getUniqueId() : null;

        if (targetPlayer == null) {
            UUID cached = CosmicVaults.getInstance().getCacheManager().findIdByName(args[0]);
            if (cached == null) {
                CosmicVaults.getInstance().getLocale().getMessage("player-offline").processPlaceholder("player", args[0]).sendPrefixedMessage(p);
                return ReturnType.FAILURE;
            }

            target = cached;
        }

        if (!NumberUtils.isInt(args[1])) {
            CosmicVaults.getInstance().getLocale().getMessage("notanumber").sendPrefixedMessage(p);
            return ReturnType.FAILURE;
        }

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(target);
        if (offlinePlayer.isOnline()) offlinePlayer.getPlayer().closeInventory();

        CosmicVaults.getInstance().getAdminEdit().put(p.getUniqueId(), target);
        CosmicVaults.getInstance().getOpenedVault().put(target, Integer.parseInt(args[1]));

        CosmicVaults.getInstance().getGuiManager().showGUI(p, new PlayerVaultGUI(target, p, Integer.parseInt(args[1]), true));

        return ReturnType.SUCCESS;
    }

    @Override
    public String getPermissionNode() {
        return "cosmicvaults.cmd.admin";
    }

    @Override
    public String getSyntax() {
        return "admin <player> <vault #>";
    }

    @Override
    public String getDescription() {
        return "Force open a player vault, changes are saved to it.";
    }

    @Override
    protected List<String> onTab(CommandSender sender, String... args) {
        if (args.length == 1) {
            return CosmicVaults.getInstance().getCacheManager().getCachedPlayerNames();
        }

        if (args.length == 2) {
            return Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        }
        return null;
    }
}
