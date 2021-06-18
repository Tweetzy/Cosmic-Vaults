package ca.tweetzy.cosmicvaults;

import ca.tweetzy.core.TweetyCore;
import ca.tweetzy.core.TweetyPlugin;
import ca.tweetzy.core.commands.CommandManager;
import ca.tweetzy.core.compatibility.ServerVersion;
import ca.tweetzy.core.configuration.Config;
import ca.tweetzy.core.gui.GuiManager;
import ca.tweetzy.core.utils.Metrics;
import ca.tweetzy.cosmicvaults.api.CosmicVaultAPI;
import ca.tweetzy.cosmicvaults.api.LocaleSettings;
import ca.tweetzy.cosmicvaults.api.Settings;
import ca.tweetzy.cosmicvaults.cache.CacheManager;
import ca.tweetzy.cosmicvaults.commands.CommandAdmin;
import ca.tweetzy.cosmicvaults.commands.CommandPlayerVault;
import ca.tweetzy.cosmicvaults.commands.CommandReload;
import ca.tweetzy.cosmicvaults.commands.CommandSettings;
import ca.tweetzy.cosmicvaults.listeners.CacheListener;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/15/2020
 * Time Created: 2:37 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class CosmicVaults extends TweetyPlugin {

    @Getter
    private static CosmicVaults instance;

    @Getter
    private final GuiManager guiManager = new GuiManager(this);

    @Getter
    private final Config data = new Config(this, "Data.yml");

    @Getter
    private CommandManager commandManager;

    @Getter
    private final ArrayList<ItemStack> vaultIcons = new ArrayList<>();

    @Getter
    private final HashMap<UUID, UUID> adminEdit = new HashMap<>();

    @Getter
    private final HashMap<UUID, Integer> openedVault = new HashMap<>();

    @Getter
    private final HashMap<UUID, Integer> vaultEdit = new HashMap<>();

    @Getter
    private CacheManager cacheManager;

    protected Metrics metrics;

    @Override
    public void onPluginLoad() {
        instance = this;
    }

    @Override
    public void onPluginEnable() {
        // Initialize Tweety Core
        TweetyCore.registerPlugin(this, 3, "EMERALD");

        // Stop the plugin if the server version is not 1.8 or higher
        if (ServerVersion.isServerVersionAtOrBelow(ServerVersion.V1_7)) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Setup the settings file
        Settings.setup();

        // Setup the locale
        setLocale(Settings.LANG.getString());
        LocaleSettings.setup();

        // Load the data file
        this.data.load();

        this.cacheManager = new CacheManager();
        this.cacheManager.loadPlayers();
        getServer().getPluginManager().registerEvents(new CacheListener(), this);

        // Guis
        this.guiManager.init();
        CosmicVaultAPI.get().loadVaultIcons();

        // Commands
        this.commandManager = new CommandManager(this);
        this.commandManager.addCommand(new CommandPlayerVault()).addSubCommands(new CommandAdmin(), new CommandSettings(), new CommandReload());

        // start metrics
        this.metrics = new Metrics(this, 6789);
    }

    @Override
    public void onPluginDisable() {
        CosmicVaultAPI.get().saveCache();
        instance = null;
    }

    @Override
    public void onConfigReload() {
        Settings.setup();
        setLocale(Settings.LANG.getString());
        LocaleSettings.setup();
    }

    @Override
    public List<Config> getExtraConfig() {
        return null;
    }
}
