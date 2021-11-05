package ca.tweetzy.cosmicvaults.impl;

import ca.tweetzy.cosmicvaults.api.enums.PermissionRegex;
import ca.tweetzy.cosmicvaults.api.interfaces.IVaultPlayer;
import ca.tweetzy.cosmicvaults.settings.Settings;
import lombok.AllArgsConstructor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 15 2021
 * Time Created: 12:07 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@AllArgsConstructor
public final class VaultPlayer implements IVaultPlayer {

	private Player player;

	@Override
	public OfflinePlayer getPlayer() {
		return this.player;
	}

	@Override
	public int getMaxAllowedVaults() {
		if (player.hasPermission("cosmicvaults.maxallowedvaults.*")) return Integer.MAX_VALUE;
		return this.player.getEffectivePermissions().stream().map(i -> {
			Matcher matcher = PermissionRegex.MAX_ALLOWED_VAULTS.getPattern().matcher(i.getPermission());
			if (matcher.matches()) return Integer.parseInt(matcher.group(1));
			return 0;
		}).max(Integer::compareTo).orElse(Settings.DEFAULT_MAX_ALLOWS_VAULTS);
	}

	@Override
	public int getMaxVaultSelectionSize() {
		if (this.player.hasPermission("cosmicvaults.maxvaultselectionsize.*")) return 6;
		return this.player.getEffectivePermissions().stream().map(i -> {
			Matcher matcher = PermissionRegex.MAX_VAULT_SELECTION_SIZE.getPattern().matcher(i.getPermission());
			if (matcher.matches()) return Integer.parseInt(matcher.group(1));
			return 0;
		}).max(Integer::compareTo).orElse(Settings.DEFAULT_VAULT_SELECTION_MENU_SIZE);
	}

	@Override
	public int getMaxVaultSize() {
		if (this.player.hasPermission("cosmicvaults.maxvaultsize.*")) return 6;
		return this.player.getEffectivePermissions().stream().map(i -> {
			Matcher matcher = PermissionRegex.MAX_VAULT_SIZE.getPattern().matcher(i.getPermission());
			if (matcher.matches()) return Integer.parseInt(matcher.group(1));
			return 0;
		}).max(Integer::compareTo).orElse(Settings.DEFAULT_VAULT_SIZE);
	}
}
