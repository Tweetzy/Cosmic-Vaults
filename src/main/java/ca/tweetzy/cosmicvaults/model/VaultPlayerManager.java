package ca.tweetzy.cosmicvaults.model;

import ca.tweetzy.cosmicvaults.impl.VaultPlayer;
import ca.tweetzy.tweety.collection.StrictList;
import ca.tweetzy.tweety.collection.StrictMap;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 15 2021
 * Time Created: 2:34 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class VaultPlayerManager {

	/**
	 * Holds all the known vault players
	 */
	private final StrictMap<UUID, VaultPlayer> players = new StrictMap<>();

	public VaultPlayer getVaultPlayer(@NonNull final UUID uuid) {
		return this.players.getOrDefault(uuid, null);
	}

	public VaultPlayer getVaultPlayer(@NonNull final Player player) {
		return this.getVaultPlayer(player.getUniqueId());
	}

	public void addVaultPlayer(@NonNull final Player player) {
		this.players.put(player.getUniqueId(), new VaultPlayer(player));
	}

	public void removeVaultPlayer(@NonNull final UUID uuid) {
		this.players.remove(uuid);
	}

	public StrictList<UUID> getAllPlayers() {
		StrictList<UUID> known = new StrictList<>(this.players.keySet());
		for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
			if (!known.contains(offlinePlayer.getUniqueId()))
				known.add(offlinePlayer.getUniqueId());
		}
		return known;
	}
}