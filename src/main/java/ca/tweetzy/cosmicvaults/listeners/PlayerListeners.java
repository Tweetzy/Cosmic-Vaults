package ca.tweetzy.cosmicvaults.listeners;

import ca.tweetzy.cosmicvaults.api.CosmicVaultsAPI;
import ca.tweetzy.tweety.Common;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 15 2021
 * Time Created: 2:58 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class PlayerListeners implements Listener {

	@EventHandler
	public void onPlayerJoinEvent(final PlayerJoinEvent event) {
		// Begin tracking them within the vault player list
		Common.runLaterAsync(() -> {
			final Player player = event.getPlayer();
			CosmicVaultsAPI.addVaultPlayer(player);
		});
	}

	@EventHandler
	public void onPlayerQuitEvent(final PlayerQuitEvent event) {
		// Remove any instance of the player from the vault player list
		Common.runLaterAsync(() -> {
			final Player player = event.getPlayer();
			CosmicVaultsAPI.removeVaultPlayer(player.getUniqueId());
		});
	}

}
