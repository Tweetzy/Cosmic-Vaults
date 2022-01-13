package ca.tweetzy.cosmicvaults;

import ca.tweetzy.cosmicvaults.api.CosmicVaultsAPI;
import ca.tweetzy.cosmicvaults.api.DataFile;
import ca.tweetzy.cosmicvaults.listeners.PlayerListeners;
import ca.tweetzy.cosmicvaults.model.VaultManager;
import ca.tweetzy.cosmicvaults.model.VaultPlayerManager;
import ca.tweetzy.cosmicvaults.settings.Settings;
import ca.tweetzy.tweety.Common;
import ca.tweetzy.tweety.Messenger;
import ca.tweetzy.tweety.MinecraftVersion;
import ca.tweetzy.tweety.model.SpigotUpdater;
import ca.tweetzy.tweety.plugin.TweetyPlugin;
import ca.tweetzy.tweety.remain.Remain;
import lombok.Getter;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 14 2021
 * Time Created: 11:39 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class CosmicVaults extends TweetyPlugin {

	@Getter
	private final DataFile dataFile = new DataFile("vaults", this);

	private final VaultPlayerManager vaultPlayerManager = new VaultPlayerManager();
	private final VaultManager vaultManager = new VaultManager();

	@Override
	protected void onPluginStart() {
		Common.ADD_TELL_PREFIX = true;
		Common.ADD_LOG_PREFIX = true;
		Common.setLogPrefix(Settings.PREFIX + " ");
		Common.setTellPrefix(Settings.PREFIX);
		Messenger.setInfoPrefix(Settings.PREFIX + " ");
		Messenger.setAnnouncePrefix(Settings.PREFIX + " ");
		Messenger.setErrorPrefix(Settings.PREFIX + " ");
		Messenger.setQuestionPrefix(Settings.PREFIX + " ");
		Messenger.setSuccessPrefix(Settings.PREFIX + " ");
		Messenger.setWarnPrefix(Settings.PREFIX + " ");

		// Register listeners
		registerEvents(new PlayerListeners());
	}

	@Override
	protected void onReloadablesStart() {
		// If players are on the server when the plugin is reloaded add them to the tracking
		Common.runAsync(() -> Remain.getOnlinePlayers().forEach(CosmicVaultsAPI::addVaultPlayer));
		getVaultManager().loadVaults();

		// Begin the auto save if enabled
		if (Settings.AutoSave.ENABLED) {
			Common.runTimerAsync(Settings.AutoSave.SAVE_DELAY.getTimeTicks(), () -> {
				getVaultManager().saveVaults();
				Common.log("&aSaving vault data");
			});
		}
	}

	@Override
	protected void onPluginStop() {
		Common.runLater(1, () -> getVaultManager().saveVaults());
	}

	@Override
	public SpigotUpdater getUpdateCheck() {
		return new SpigotUpdater(45463);
	}

	@Override
	public int getMetricsPluginId() {
		return 6789;
	}

	@Override
	public int getFoundedYear() {
		return 2017; // Sept 2021 -> 3.0
	}

	@Override
	public MinecraftVersion.V getMinimumVersion() {
		return MinecraftVersion.V.v1_8;
	}

	public static CosmicVaults getInstance() {
		return (CosmicVaults) TweetyPlugin.getInstance();
	}

	public static VaultPlayerManager getVaultPlayerManager() {
		return ((CosmicVaults) TweetyPlugin.getInstance()).vaultPlayerManager;
	}

	public static VaultManager getVaultManager() {
		return ((CosmicVaults) TweetyPlugin.getInstance()).vaultManager;
	}
}
