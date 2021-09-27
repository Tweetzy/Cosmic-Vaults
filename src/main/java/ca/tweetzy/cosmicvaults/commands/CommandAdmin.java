package ca.tweetzy.cosmicvaults.commands;

import ca.tweetzy.cosmicvaults.api.CosmicVaultsAPI;
import ca.tweetzy.cosmicvaults.menu.MenuPlayerSelection;
import ca.tweetzy.cosmicvaults.menu.MenuPlayerVaults;
import ca.tweetzy.cosmicvaults.model.Permissions;
import ca.tweetzy.cosmicvaults.settings.Localization;
import ca.tweetzy.tweety.Common;
import ca.tweetzy.tweety.PlayerUtil;
import ca.tweetzy.tweety.TabUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 20 2021
 * Time Created: 9:18 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class CommandAdmin extends AbstractVaultSubCommand {

	public CommandAdmin() {
		super("admin");
		setDescription("Admin control for player vaults");
		setUsage("[player]");
		setPermission(Permissions.Command.ADMIN);
	}

	@Override
	protected void onCommand() {
		checkConsole();
		final Player player = getPlayer();

		// open the player listing menu
		if (args.length == 0) {
			new MenuPlayerSelection().displayTo(player);
			return;
		}

		PlayerUtil.lookupOfflinePlayerAsync(args[0], offlinePlayer -> {
			if (!offlinePlayer.hasPlayedBefore()) {
				Common.tell(getSender(), Localization.VaultError.PLAYER_NOT_FOUND.replace("{player}", args[0]));
				return;
			}

			new MenuPlayerVaults(offlinePlayer.getUniqueId(), CosmicVaultsAPI.getVaultsByPlayer(offlinePlayer.getUniqueId()).values()).displayTo(player);
		});
	}

	@Override
	protected List<String> tabComplete() {
		if (args.length == 1)
			return TabUtil.complete(args[0], Arrays.stream(Bukkit.getOfflinePlayers()).map(OfflinePlayer::getName).collect(Collectors.toList()));
		return NO_COMPLETE;
	}
}
