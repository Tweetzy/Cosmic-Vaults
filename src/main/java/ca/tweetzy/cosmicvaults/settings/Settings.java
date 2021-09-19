package ca.tweetzy.cosmicvaults.settings;

import ca.tweetzy.tweety.model.SimpleTime;
import ca.tweetzy.tweety.remain.CompMaterial;
import ca.tweetzy.tweety.settings.SimpleSettings;
import org.bukkit.Material;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 14 2021
 * Time Created: 11:40 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class Settings extends SimpleSettings {

	public static String PREFIX;
	public static Integer DEFAULT_VAULT_SELECTION_MENU_SIZE;
	public static Integer DEFAULT_VAULT_SIZE;
	public static Integer DEFAULT_MAX_ALLOWS_VAULTS;

	public static Boolean SAVE_TO_FILE_AFTER_EVERY_VAULT_CLOSE;
	public static Boolean ONLY_SAVE_VAULTS_WITH_CHANGES;

	private static void init() {
		pathPrefix(null);

		PREFIX = getString("Prefix");
		DEFAULT_VAULT_SELECTION_MENU_SIZE = getInteger("Default Vault Selection Menu Size");
		DEFAULT_VAULT_SIZE = getInteger("Default Vault Size");
		DEFAULT_MAX_ALLOWS_VAULTS = getInteger("Default Max Allowed Vaults");
		SAVE_TO_FILE_AFTER_EVERY_VAULT_CLOSE = getBoolean("Save To File After Every Vault Close");
		ONLY_SAVE_VAULTS_WITH_CHANGES = getBoolean("Only Save Vaults With Changes");
	}


	public static final class AutoSave {

		public static Boolean ENABLED;
		public static SimpleTime SAVE_DELAY;

		private static void init() {
			pathPrefix("Auto Save");
			ENABLED = getBoolean("Enabled");
			SAVE_DELAY = getTime("Save Delay");
		}
	}

	public static final class VaultSelectionMenu {

		public static String TITLE;

		private static void init() {
			pathPrefix("Guis.Vault Selection");
			TITLE = getString("Title");
		}

		public static final class Items {

			public static CompMaterial LOCKED_MATERIAL;
			public static String LOCKED_NAME;
			public static List<String> LOCKED_LORE;

			public static CompMaterial OPENED_MATERIAL;
			public static String OPENED_NAME;
			public static List<String> OPENED_LORE;

			public static CompMaterial UNOPENED_MATERIAL;
			public static String UNOPENED_NAME;
			public static List<String> UNOPENED_LORE;

			private static void init() {
				pathPrefix("Guis.Vault Selection.Items");

				LOCKED_MATERIAL = getMaterial("Locked.Material");
				LOCKED_NAME = getString("Locked.Name");
				LOCKED_LORE = getStringList("Locked.Lore");

				OPENED_MATERIAL = getMaterial("Opened.Material");
				OPENED_NAME = getString("Opened.Name");
				OPENED_LORE = getStringList("Opened.Lore");

				UNOPENED_MATERIAL = getMaterial("Unopened.Material");
				UNOPENED_NAME = getString("Unopened.Name");
				UNOPENED_LORE = getStringList("Unopened.Lore");
			}
		}
	}
	@Override
	protected int getConfigVersion() {
		return 1;
	}
}
