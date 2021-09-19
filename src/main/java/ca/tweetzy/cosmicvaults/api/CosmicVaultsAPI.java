package ca.tweetzy.cosmicvaults.api;

import ca.tweetzy.cosmicvaults.CosmicVaults;
import ca.tweetzy.cosmicvaults.impl.Vault;
import ca.tweetzy.cosmicvaults.impl.VaultPlayer;
import ca.tweetzy.cosmicvaults.model.VaultManager;
import ca.tweetzy.cosmicvaults.model.VaultPlayerManager;
import ca.tweetzy.tweety.collection.StrictMap;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 14 2021
 * Time Created: 11:42 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@UtilityClass
public final class CosmicVaultsAPI {

	private final VaultPlayerManager vaultPlayerManager = CosmicVaults.getVaultPlayerManager();
	private final VaultManager vaultManager = CosmicVaults.getVaultManager();

	/**
	 * Used to add a player to the vault player list
	 *
	 * @param player is the {@link Player} being added to the vault player list
	 */
	public void addVaultPlayer(@NonNull final Player player) {
		vaultPlayerManager.addVaultPlayer(player);
	}

	/**
	 * Used to remove a player from the vault player list
	 *
	 * @param uuid is the {@link Player}'s UUID
	 */
	public void removeVaultPlayer(@NonNull final UUID uuid) {
		vaultPlayerManager.removeVaultPlayer(uuid);
	}

	/**
	 * Get a vault player by their {@link UUID} or return null if not found
	 *
	 * @param uuid is the {@link UUID} of the {@link Player}
	 * @return the {@link VaultPlayer} associated with that {@param uuid}
	 */
	public VaultPlayer getVaultPlayer(@NonNull final UUID uuid) {
		return vaultPlayerManager.getVaultPlayer(uuid);
	}

	/**
	 * Get a vault player by their {@link Player} object or return null if not found
	 *
	 * @param player is the {@link Player} being fetched
	 * @return the {@link VaultPlayer} associated with that {@param player}
	 */
	public VaultPlayer getVaultPlayer(@NonNull final Player player) {
		return vaultPlayerManager.getVaultPlayer(player);
	}

	/**
	 * Add a new vault to the vault list
	 *
	 * @param vault is the {@link Vault} being added to the list
	 */
	public void addVault(@NonNull final Vault vault) {
		vaultManager.addVault(vault);
	}

	/**
	 * Remove an existing vault from the vault list
	 *
	 * @param vault is the {@link Vault} being removed from the list
	 */
	public void removeVault(@NonNull final Vault vault) {
		vaultManager.removeVault(vault);
	}

	/**
	 * Used to save vault data straight to the data file
	 *
	 * @param vault is the {@link Vault} being saved
	 */
	public void saveVault(@NonNull final Vault vault) {
		vaultManager.saveVault(vault);
	}

	/**
	 * Used to add a vault that has been edited to the tracking list
	 *
	 * @param uuid is the {@link UUID} of the edited {@link Vault}
	 */
	public void addEditedVault(@NonNull final UUID uuid) {
		vaultManager.addEditedVault(uuid);
	}

	/**
	 * Used to remove a vault from the edited tracking set
	 *
	 * @param uuid is the {@link UUID} of the edited {@link Vault}
	 */
	public void removeEditedVault(@NonNull final UUID uuid) {
		vaultManager.removeEditedVault(uuid);
	}

	/**
	 * Reset a specific vault owned by a player
	 *
	 * @param owner is the {@link UUID} of the owning {@link Player}
	 * @param number is the number of the {@link Vault}
	 */
	public void resetVaultContents(@NonNull final UUID owner, final int number) {
		vaultManager.resetVaultContents(owner, number);
	}

	/**
	 * Used to get a {@link Vault} owned by a player
	 *
	 * @param owner is the owner's {@link UUID}
	 * @param number is the number of the {@link Vault}
	 * @return null or the found {@link Vault}
	 */
	public Vault getVault(@NonNull final UUID owner, final int number) {
		return vaultManager.getVault(owner, number);
	}

	/**
	 * Return a map of all the vaults owned by a particular player
	 *
	 * @param owner is the owning {@link Player}'s {@link UUID}
	 * @return a map of owned vaults by that {@param owner}
	 */
	public StrictMap<Integer, Vault> getVaultsByPlayer(@NonNull final UUID owner) {
		return vaultManager.getVaultsByPlayer(owner);
	}
}
