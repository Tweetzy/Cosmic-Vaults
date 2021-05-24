package ca.tweetzy.cosmicvaults.cache;

import ca.tweetzy.cosmicvaults.CosmicVaults;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The current file has been created by Kiran Hart
 * Date Created: May 21 2021
 * Time Created: 5:26 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class CacheManager {

    @Getter
    private final LinkedList<PlayerCache> cachedPlayers = new LinkedList<>();

    public void addPlayer(PlayerCache playerCache) {
        this.cachedPlayers.removeIf(players -> players.getUuid().equals(playerCache.getUuid()));
        this.cachedPlayers.add(playerCache);
    }

    public UUID findIdByName(String name) {
        return this.cachedPlayers.stream().filter(players -> players.getName().equalsIgnoreCase(name)).findFirst().orElse(null).getUuid();
    }

    public List<String> getCachedPlayerNames() {
        return this.cachedPlayers.stream().map(PlayerCache::getName).collect(Collectors.toList());
    }

    public void loadPlayers() {
        ConfigurationSection section = CosmicVaults.getInstance().getData().getConfigurationSection("player cache");
        if (section == null || section.getKeys(false).size() == 0) return;

        CosmicVaults.getInstance().getData().getConfigurationSection("player cache").getKeys(false).forEach(id -> {
            int vaultSize = CosmicVaults.getInstance().getData().getInt("player cache." + id + ".max vault size");
            int selectionSize = CosmicVaults.getInstance().getData().getInt("player cache." + id + ".max selection size");

            addPlayer(new PlayerCache(
                    UUID.fromString(id),
                    CosmicVaults.getInstance().getData().getString("player cache." + id + ".name"),
                    CosmicVaults.getInstance().getData().contains("player cache." + id + ".max vault size") ? vaultSize : 54,
                    CosmicVaults.getInstance().getData().contains("player cache." + id + ".max selection size") ? selectionSize : 54
            ));
        });
    }
}
