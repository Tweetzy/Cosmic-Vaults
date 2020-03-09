package com.kiranhart.cosmicvaults;

import com.kiranhart.cosmicvaults.commands.CommandManager;
import com.kiranhart.cosmicvaults.locale.Locale;
import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin {

    private static Core instance;
    private Locale locale;

    private CommandManager commandManager;

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

        // setup the command manager
        this.commandManager = new CommandManager();
        this.commandManager.init();
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
}
