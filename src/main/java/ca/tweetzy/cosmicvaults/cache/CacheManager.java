package ca.tweetzy.cosmicvaults.cache;

import ca.tweetzy.cosmicvaults.CosmicVaults;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
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
    private final HashMap<UUID, String> cachedPlayers = new HashMap<>();

    public void addPlayer(UUID id, String name) {
        this.cachedPlayers.putIfAbsent(id, name);
    }

    public UUID findIdByName(String name) {
        return this.cachedPlayers.keySet().stream().filter(id -> cachedPlayers.get(id).equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public List<String> getCachedPlayerNames() {
        return this.cachedPlayers.keySet().stream().map(this.cachedPlayers::get).collect(Collectors.toList());
    }

    public void loadPlayers() {
        ConfigurationSection section = CosmicVaults.getInstance().getData().getConfigurationSection("player cache");
        if (section == null || section.getKeys(false).size() == 0) return;

        CosmicVaults.getInstance().getData().getConfigurationSection("player cache").getKeys(false).forEach(id -> {
            addPlayer(UUID.fromString(id), CosmicVaults.getInstance().getData().getString("player cache." + id + ".name"));
        });
    }
}
