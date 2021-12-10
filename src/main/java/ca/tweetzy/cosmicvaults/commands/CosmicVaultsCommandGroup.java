package ca.tweetzy.cosmicvaults.commands;

import ca.tweetzy.cosmicvaults.api.CosmicVaultsAPI;
import ca.tweetzy.cosmicvaults.api.events.VaultOpenEvent;
import ca.tweetzy.cosmicvaults.impl.Vault;
import ca.tweetzy.cosmicvaults.impl.VaultPlayer;
import ca.tweetzy.cosmicvaults.menu.MenuVaultSelection;
import ca.tweetzy.cosmicvaults.menu.MenuVaultView;
import ca.tweetzy.cosmicvaults.model.Permissions;
import ca.tweetzy.cosmicvaults.settings.Localization;
import ca.tweetzy.tweety.Common;
import ca.tweetzy.tweety.Valid;
import ca.tweetzy.tweety.annotation.AutoRegister;
import ca.tweetzy.tweety.command.PermsCommand;
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
@AutoRegister
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

		if (vaultNumber <= 0) {
			Common.tell(player, Localization.VaultError.VAULT_CANNOT_BE_ZERO);
			return;
		}

		VaultPlayer vaultPlayer = CosmicVaultsAPI.getVaultPlayer(player);
		if (vaultPlayer == null) {
			CosmicVaultsAPI.addVaultPlayer(player);
			vaultPlayer = CosmicVaultsAPI.getVaultPlayer(player);
		}

		if (vaultNumber > vaultPlayer.getMaxAllowedVaults()) {
			Common.tell(player, Localization.VaultError.NOT_ALLOWED_TO_USE_VAULT.replace("{vault_number}", String.valueOf(vaultNumber)));
			return;
		}

		Vault vault = CosmicVaultsAPI.getVault(player.getUniqueId(), vaultNumber);
		if (vault == null) {
			vault = new Vault(vaultPlayer, vaultNumber);
			CosmicVaultsAPI.addVault(vault);
			CosmicVaultsAPI.addEditedVault(vault.getUUID());
		}

		if (!Common.callEvent(new VaultOpenEvent(vault))) return;

		// if the vault is open then stop them from opening it again
		if (vault.isOpen()) {
			Common.tell(player, Localization.VaultError.VAULT_ALREADY_OPEN.replace("{vault_number}", String.valueOf(vaultNumber)));
			return;
		}

		new MenuVaultView(vault).displayTo(player);
	}

	@Override
	protected void registerSubcommands() {
		registerSubcommand(new CommandDelete());
		registerSubcommand(new CommandReset());
		registerSubcommand(new CommandAdmin());
		registerSubcommand(new CommandConvert());
		registerSubcommand(new PermsCommand(Permissions.class));
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
