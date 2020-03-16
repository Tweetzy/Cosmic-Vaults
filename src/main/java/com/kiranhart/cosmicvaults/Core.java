package com.kiranhart.cosmicvaults;

import com.kiranhart.cosmicvaults.api.CosmicVaultAPI;
import com.kiranhart.cosmicvaults.api.HartInventoryListener;
import com.kiranhart.cosmicvaults.commands.CommandManager;
import com.kiranhart.cosmicvaults.locale.Locale;
import com.kiranhart.cosmicvaults.utils.ConfigWrapper;
import com.kiranhart.cosmicvaults.utils.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public final class Core extends JavaPlugin {

    private static Core instance;
    private Locale locale;

    private ArrayList<ItemStack> vaultIcons;

    private HashMap<UUID, Integer> openedVault;
    private HashMap<UUID, Integer> vaultedit;

    private CommandManager commandManager;
    private ConfigWrapper dataFile;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {

        // setup the default config
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        // setup the locale
        new Locale(this, "en_US");
        this.locale = Locale.getLocale(getConfig().getString("lang"));

        this.vaultIcons = new ArrayList<>();
        this.openedVault = new HashMap<>();
        this.vaultedit = new HashMap<>();

        this.dataFile = new ConfigWrapper(this, "", "Data.yml");
        this.dataFile.saveConfig();

        Bukkit.getPluginManager().registerEvents(new HartInventoryListener(), this);

        // load vault icons
        CosmicVaultAPI.get().loadVaultIcons();

        // setup the command manager
        this.commandManager = new CommandManager();
        this.commandManager.init();

        // start metrics
        Metrics metrics = new Metrics(this, 6789);
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    /**
     * Get the instance of the main class extending JavaPlugin
     *
     * @return the class instance
     */
    public static Core getInstance() {
        return instance;
    }

    /**
     * @return the locale instance
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * @return a list of all the loaded vault icons
     */
    public ArrayList<ItemStack> getVaultIcons() {
        return vaultIcons;
    }

    /**
     * get the data file
     *
     * @return the data file containing player data
     */
    public ConfigWrapper getDataFile() {
        return dataFile;
    }

    public HashMap<UUID, Integer> getOpenedVault() {
        return openedVault;
    }

    public HashMap<UUID, Integer> getVaultedit() {
        return vaultedit;
    }
}
