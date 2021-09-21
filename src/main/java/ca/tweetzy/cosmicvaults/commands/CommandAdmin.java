package ca.tweetzy.cosmicvaults.commands;

import ca.tweetzy.cosmicvaults.menu.MenuPlayerSelection;
import ca.tweetzy.cosmicvaults.model.Permissions;
import ca.tweetzy.tweety.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
		setUsage("[player] [#]");
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
	}
}
