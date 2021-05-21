package ca.tweetzy.cosmicvaults.listeners;

import ca.tweetzy.cosmicvaults.CosmicVaults;
import ca.tweetzy.cosmicvaults.api.CosmicVaultAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

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
        Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(CosmicVaults.getInstance(), () -> CosmicVaults.getInstance().getCacheManager().addPlayer(player.getUniqueId(), player.getName()), 20L);
    }
}
