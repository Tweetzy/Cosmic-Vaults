package ca.tweetzy.cosmicvaults;

import ca.tweetzy.core.TweetyCore;
import ca.tweetzy.core.TweetyPlugin;
import ca.tweetzy.core.commands.CommandManager;
import ca.tweetzy.core.configuration.Config;
import ca.tweetzy.core.core.PluginID;
import ca.tweetzy.core.locale.Locale;
import ca.tweetzy.core.utils.Metrics;
import ca.tweetzy.cosmicvaults.api.CosmicVaultAPI;
import ca.tweetzy.cosmicvaults.api.Settings;
import ca.tweetzy.cosmicvaults.commands.AdminCommand;
import ca.tweetzy.cosmicvaults.commands.PlayerVaultCommand;
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

    private static CosmicVaults instance;
    private Locale locale;
    private Metrics metrics;

    private ArrayList<ItemStack> vaultIcons;

    private HashMap<UUID, UUID> adminEdit;
    private HashMap<UUID, Integer> openedVault;
    private HashMap<UUID, Integer> vaultedit;

    private CommandManager commandManager;

    Config dataFile = new Config(this, "Data.yml");

    @Override
    public void onPluginLoad() {
        instance = this;
    }

    @Override
    public void onPluginEnable() {
        // Initialize Tweety Core
        TweetyCore.registerPlugin(this, 3, "EMERALD");
        TweetyCore.initEvents(this);

        Settings.setup();

        new Locale(this, "en_US");
        this.locale = Locale.getLocale(Settings.LANG.getString(), Settings.PREFIX.getString());

        this.vaultIcons = new ArrayList<>();
        this.adminEdit = new HashMap<>();
        this.openedVault = new HashMap<>();
        this.vaultedit = new HashMap<>();

        this.dataFile.load();

        // load vault icons
        CosmicVaultAPI.get().loadVaultIcons();

        this.commandManager = new CommandManager(this);
        this.commandManager.addCommand(new PlayerVaultCommand()).addSubCommands(new AdminCommand());

        // start metrics
        if (Settings.METRICS.getBoolean()) {
            this.metrics = new Metrics(this, (int) PluginID.COSMIC_VAULTS.getbStatsID());
        }
    }

    @Override
    public void onPluginDisable() {
        instance = null;
    }

    @Override
    public void onConfigReload() {

    }

    @Override
    public List<Config> getExtraConfig() {
        return null;
    }

    /**
     * Get the instance of the main class extending JavaPlugin
     *
     * @return the class instance
     */
    public static CosmicVaults getInstance() {
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
    public Config getDataFile() {
        return dataFile;
    }

    public HashMap<UUID, Integer> getOpenedVault() {
        return openedVault;
    }

    public HashMap<UUID, Integer> getVaultedit() {
        return vaultedit;
    }

    public HashMap<UUID, UUID> getAdminEdit() {
        return adminEdit;
    }
}
