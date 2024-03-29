package ca.tweetzy.cosmicvaults.commands;

import ca.tweetzy.cosmicvaults.api.CosmicVaultsAPI;
import ca.tweetzy.cosmicvaults.impl.Vault;
import ca.tweetzy.cosmicvaults.model.Permissions;
import ca.tweetzy.cosmicvaults.settings.Localization;
import ca.tweetzy.tweety.Common;
import ca.tweetzy.tweety.PlayerUtil;
import ca.tweetzy.tweety.settings.SimpleLocalization;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 19 2021
 * Time Created: 3:15 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class CommandDelete extends AbstractVaultSubCommand {

	public CommandDelete() {
		super("delete|del|remove");
		setMinArguments(1);
		setDescription("Used to delete your vault");
		setUsage("<#>");
		setPermission(Permissions.Command.DELETE);
	}

	@Override
	protected void onCommand() {
		final int vaultNumber = findNumber(0, Localization.VaultError.NOT_A_NUMBER);

		if (vaultNumber <= 0) {
			Common.tell(getSender(), Localization.VaultError.VAULT_CANNOT_BE_ZERO);
			return;
		}

		if (args.length == 1) {
			if (!(getSender() instanceof Player player)) {
				Common.tell(getSender(), SimpleLocalization.Commands.NO_CONSOLE);
				return;
			}

			final Vault vault = CosmicVaultsAPI.getVault(player.getUniqueId(), vaultNumber);

			if (vault == null) {
				Common.tell(player, Localization.VaultError.CANNOT_FIND_VAULT);
				return;
			}

			CosmicVaultsAPI.deleteVault(player.getUniqueId(), vaultNumber);
			Common.tell(player, Localization.VaultDelete.PLAYER.replace("{vault_number}", String.valueOf(vaultNumber)));
			return;
		}

		if (!PlayerUtil.hasPerm(getSender(), Permissions.Command.DELETE_OTHERS)) return;

		PlayerUtil.lookupOfflinePlayerAsync(args[1], offlinePlayer -> {
			if (!offlinePlayer.hasPlayedBefore()) {
				Common.tell(getSender(), Localization.VaultError.PLAYER_NOT_FOUND.replace("{player}", args[1]));
				return;
			}

			final Vault vault = CosmicVaultsAPI.getVault(offlinePlayer.getUniqueId(), vaultNumber);
			if (vault == null) {
				Common.tell(getSender(), Localization.VaultError.CANNOT_FIND_VAULT);
				return;
			}

			CosmicVaultsAPI.deleteVault(offlinePlayer.getUniqueId(), vaultNumber);
			Common.tell(getSender(), Localization.VaultDelete.ADMIN.replace("{player}", args[1]).replace("{vault_number}", String.valueOf(vaultNumber)));

			if (offlinePlayer.isOnline())
				Common.tell(offlinePlayer.getPlayer(), Localization.VaultDelete.PLAYER.replace("{vault_number}", String.valueOf(vaultNumber)));
		});
	}

	@Override
	protected List<String> tabComplete() {
		return NO_COMPLETE;
	}
}
