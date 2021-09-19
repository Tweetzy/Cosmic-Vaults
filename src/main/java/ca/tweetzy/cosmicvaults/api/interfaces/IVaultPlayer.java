package ca.tweetzy.cosmicvaults.api.interfaces;

import org.bukkit.OfflinePlayer;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 14 2021
 * Time Created: 11:49 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public interface IVaultPlayer {

	/**
	 * Get the player
	 *
	 * @return the {@link OfflinePlayer}
	 */
	OfflinePlayer getPlayer();

	/**
	 * The maximum allowed vaults the player is allowed to open
	 *
	 * @return the amount of vaults the player can open. ex. vaults 1 - 5
	 */
	int getMaxAllowedVaults();

	/**
	 * The max selection menu size for the player, this is how
	 * big the selection menu will be
	 *
	 * @return the max rows for the selection menu
	 */
	int getMaxVaultSelectionSize();

	/**
	 * Get the max vault size for a player
	 *
	 * @return the amount of rows a player's vault will be
	 */
	int getMaxVaultSize();
}
