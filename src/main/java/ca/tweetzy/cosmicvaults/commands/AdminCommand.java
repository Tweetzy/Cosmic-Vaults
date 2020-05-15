package ca.tweetzy.cosmicvaults.commands;

import ca.tweetzy.core.commands.AbstractCommand;
import ca.tweetzy.core.inventory.TInventory;
import ca.tweetzy.core.utils.NumberUtils;
import ca.tweetzy.core.utils.PlayerUtils;
import ca.tweetzy.cosmicvaults.CosmicVaults;
import ca.tweetzy.cosmicvaults.guis.PlayerVaultGUI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/15/2020
 * Time Created: 4:25 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class AdminCommand extends AbstractCommand {

    public AdminCommand() {
        super(CommandType.PLAYER_ONLY, "admin");
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {
        if (args.length != 2) return ReturnType.SYNTAX_ERROR;
        Player p = (Player) sender;

        if (Bukkit.getPlayerExact(args[0]) == null) {
            CosmicVaults.getInstance().getLocale().getMessage("player-offline").processPlaceholder("player", args[0]).sendMessage(p);
            return ReturnType.FAILURE;
        }

        if (!NumberUtils.isInt(args[1])) {
            CosmicVaults.getInstance().getLocale().getMessage("notanumber").sendMessage(p);
            return ReturnType.FAILURE;
        }

        Player target = Bukkit.getPlayerExact(args[0]);

        assert target != null;
        if (target.getOpenInventory().getTopInventory().getHolder() instanceof TInventory) {
            target.closeInventory();
        }

        CosmicVaults.getInstance().getOpenedVault().remove(target.getUniqueId());

        CosmicVaults.getInstance().getOpenedVault().put(p.getUniqueId(), Integer.parseInt(args[1]));
        p.openInventory(new PlayerVaultGUI(target, Integer.parseInt(args[1])).getInventory());

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
            return Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList());
        }

        if (args.length == 2) {
            return Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        }
        return null;
    }
}
