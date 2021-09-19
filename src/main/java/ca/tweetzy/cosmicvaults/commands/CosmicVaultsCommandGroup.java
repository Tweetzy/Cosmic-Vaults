package ca.tweetzy.cosmicvaults.commands;

import ca.tweetzy.cosmicvaults.api.CosmicVaultsAPI;
import ca.tweetzy.cosmicvaults.api.events.VaultOpenEvent;
import ca.tweetzy.cosmicvaults.impl.Vault;
import ca.tweetzy.cosmicvaults.impl.VaultPlayer;
import ca.tweetzy.cosmicvaults.menu.MenuVaultSelection;
import ca.tweetzy.cosmicvaults.menu.MenuVaultView;
import ca.tweetzy.tweety.Common;
import ca.tweetzy.tweety.Valid;
import ca.tweetzy.tweety.command.SimpleCommandGroup;
import ca.tweetzy.tweety.settings.SimpleLocalization;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 14 2021
 * Time Created: 11:50 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CosmicVaultsCommandGroup extends SimpleCommandGroup {

	@Getter
	private final static CosmicVaultsCommandGroup instance = new CosmicVaultsCommandGroup();

	@Override
	protected String getHeaderPrefix() {
		return "" + ChatColor.GREEN + ChatColor.BOLD;
	}

	@Override
	protected void zeroArgActions(CommandSender commandSender) {
		if (!(commandSender instanceof Player player)) {
			return;
		}

		VaultPlayer vaultPlayer = CosmicVaultsAPI.getVaultPlayer(player);
		if (vaultPlayer == null) {
			CosmicVaultsAPI.addVaultPlayer(player);
			vaultPlayer = CosmicVaultsAPI.getVaultPlayer(player);
		}

		new MenuVaultSelection(vaultPlayer).displayTo(player);
	}

	@Override
	protected void dynamicArgActions(CommandSender commandSender, String arg) {
		if (!(commandSender instanceof Player player)) return;

		final boolean isValidNumber = Valid.isInteger(arg);
		if (!isValidNumber) {
			Common.tell(player, SimpleLocalization.Commands.INVALID_ARGUMENT.replace("{label}", getLabel()));
			return;
		}

		final int vaultNumber = Integer.parseInt(arg);

		VaultPlayer vaultPlayer = CosmicVaultsAPI.getVaultPlayer(player);
		if (vaultPlayer == null) {
			CosmicVaultsAPI.addVaultPlayer(player);
			vaultPlayer = CosmicVaultsAPI.getVaultPlayer(player);
		}

		if (vaultNumber > vaultPlayer.getMaxAllowedVaults()) {
			Common.tell(player,"&csmh, you don't got access to that vault");
			return;
		}

		Vault vault = CosmicVaultsAPI.getVault(player.getUniqueId(), vaultNumber);
		if (vault == null) {
			vault = new Vault(vaultPlayer, vaultNumber);
			CosmicVaultsAPI.addVault(vault);
			CosmicVaultsAPI.addEditedVault(vault.getUUID());
		}

		if (!Common.callEvent(new VaultOpenEvent(vault))) return;

		new MenuVaultView(vault).displayTo(player);
	}

	@Override
	protected void registerSubcommands() {
	}

	@Override
	protected String getCredits() {
		return null;
	}

	@Override
	protected boolean useZeroArgAction() {
		return true;
	}

	@Override
	protected boolean handleDynamicArgActions() {
		return true;
	}
}
