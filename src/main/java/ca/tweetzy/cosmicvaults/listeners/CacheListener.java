package ca.tweetzy.cosmicvaults.listeners;

import ca.tweetzy.cosmicvaults.CosmicVaults;
import ca.tweetzy.cosmicvaults.api.CosmicVaultAPI;
import ca.tweetzy.cosmicvaults.cache.PlayerCache;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * The current file has been created by Kiran Hart
 * Date Created: May 21 2021
 * Time Created: 5:04 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class CacheListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(CosmicVaults.getInstance(), () -> CosmicVaults.getInstance().getCacheManager().addPlayer(new PlayerCache(
                player.getUniqueId(),
                player.getName(),
                CosmicVaultAPI.get().getMaxSize(player),
                CosmicVaultAPI.get().getMaxSelectionMenu(player)
        )), 20L);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(CosmicVaults.getInstance(), () -> CosmicVaults.getInstance().getCacheManager().getCachedPlayers().removeIf(playerCache -> playerCache.getUuid().equals(player.getUniqueId())), 20L);
    }
}
