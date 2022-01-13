package ca.tweetzy.cosmicvaults.commands;

import ca.tweetzy.cosmicvaults.api.CosmicVaultsAPI;
import ca.tweetzy.cosmicvaults.impl.Vault;
import ca.tweetzy.cosmicvaults.model.Permissions;
import ca.tweetzy.cosmicvaults.settings.Localization;
import ca.tweetzy.tweety.Common;
import ca.tweetzy.tweety.PlayerUtil;
import ca.tweetzy.tweety.settings.SimpleLocalization;
import org.bukkit.entity.Player;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 20 2021
 * Time Created: 1:37 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class CommandReset extends AbstractVaultSubCommand {

	public CommandReset() {
		super("reset");
		setMinArguments(1);
		setDescription("Reset your vault settings");
		setUsage("<#>");
		setPermission(Permissions.Command.RESET);
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

			CosmicVaultsAPI.resetVaultContents(player.getUniqueId(), vaultNumber);
			Common.tell(player, Localization.VaultReset.PLAYER.replace("{vault_number}", String.valueOf(vaultNumber)));
			return;
		}

		if (!PlayerUtil.hasPerm(getSender(), Permissions.Command.RESET_OTHERS)) return;

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

			CosmicVaultsAPI.resetVaultContents(offlinePlayer.getUniqueId(), vaultNumber);
			Common.tell(getSender(), Localization.VaultReset.ADMIN.replace("{player}", args[1]).replace("{vault_number}", String.valueOf(vaultNumber)));

			if (offlinePlayer.isOnline())
				Common.tell(offlinePlayer.getPlayer(), Localization.VaultReset.PLAYER.replace("{vault_number}", String.valueOf(vaultNumber)));
		});
	}
}
