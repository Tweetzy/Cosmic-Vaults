package ca.tweetzy.cosmicvaults.settings;

import ca.tweetzy.tweety.collection.StrictList;
import ca.tweetzy.tweety.model.SimpleTime;
import ca.tweetzy.tweety.remain.CompMaterial;
import ca.tweetzy.tweety.settings.SimpleSettings;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 14 2021
 * Time Created: 11:40 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class Settings extends SimpleSettings {

	public static String PREFIX;
	public static String DEFAULT_VAULT_TITLE;
	public static String DEFAULT_VAULT_DESC;
	public static Integer DEFAULT_VAULT_SELECTION_MENU_SIZE;
	public static Integer DEFAULT_VAULT_SIZE;
	public static Integer DEFAULT_MAX_ALLOWS_VAULTS;

	public static Boolean SAVE_TO_FILE_AFTER_EVERY_VAULT_CLOSE;
	public static Boolean ONLY_SAVE_VAULTS_WITH_CHANGES;
	public static StrictList<CompMaterial> VAULT_ICONS;
	public static StrictList<CompMaterial> BLOCKED_MATERIALS;
	public static Boolean USE_AVAILABLE_MATERIALS;

	private static void init() {
		pathPrefix(null);

		PREFIX = getString("Prefix");

		DEFAULT_VAULT_TITLE = getString("Default Vault Title");
		DEFAULT_VAULT_DESC = getString("Default Vault Desc");

		DEFAULT_VAULT_SELECTION_MENU_SIZE = getInteger("Default Vault Selection Menu Size");
		DEFAULT_VAULT_SIZE = getInteger("Default Vault Size");
		DEFAULT_MAX_ALLOWS_VAULTS = getInteger("Default Max Allowed Vaults");
		SAVE_TO_FILE_AFTER_EVERY_VAULT_CLOSE = getBoolean("Save To File After Every Vault Close");
		ONLY_SAVE_VAULTS_WITH_CHANGES = getBoolean("Only Save Vaults With Changes");

		VAULT_ICONS = getMaterialList("Vault Icons");
		BLOCKED_MATERIALS = getMaterialList("Blocked Materials");
		USE_AVAILABLE_MATERIALS = getBoolean("Use Available Materials");
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

	public static final class PlayerSelectionMenu {

		public static String TITLE;

		private static void init() {
			pathPrefix("Guis.Player Selection");
			TITLE = getString("Title");
		}

		public static final class Items {

			public static String PLAYER_NAME;
			public static List<String> PLAYER_LORE;

			private static void init() {
				pathPrefix("Guis.Player Selection.Items");
				PLAYER_NAME = getString("Player.Name");
				PLAYER_LORE = getStringList("Player.Lore");
			}
		}
	}

	public static final class VaultIconMenu {

		public static String TITLE;

		private static void init() {
			pathPrefix("Guis.Vault Icon Select");
			TITLE = getString("Title");
		}
	}

	public static final class VaultEditMenu {

		public static String TITLE;
		public static CompMaterial BACKGROUND_ITEM;
		public static Integer ROWS;

		private static void init() {
			pathPrefix("Guis.Vault Edit");
			TITLE = getString("Title");
			BACKGROUND_ITEM = getMaterial("Background Item");
			ROWS = getInteger("Rows");
		}

		public static final class Items {

			public static List<Integer> NAME_SLOTS;
			public static CompMaterial NAME_MATERIAL;
			public static String NAME_NAME;
			public static List<String> NAME_LORE;

			public static List<Integer> DESCRIPTION_SLOTS;
			public static CompMaterial DESCRIPTION_MATERIAL;
			public static String DESCRIPTION_NAME;
			public static List<String> DESCRIPTION_LORE;

			public static List<Integer> ICON_SLOTS;
			public static String ICON_NAME;
			public static List<String> ICON_LORE;

			public static List<Integer> RESET_SLOTS;
			public static CompMaterial RESET_MATERIAL;
			public static String RESET_NAME;
			public static List<String> RESET_LORE;

			public static List<Integer> DELETE_SLOTS;
			public static CompMaterial DELETE_MATERIAL;
			public static String DELETE_NAME;
			public static List<String> DELETE_LORE;

			private static void init() {
				pathPrefix("Guis.Vault Edit.Items");

				NAME_SLOTS = getList("Name.Slots", Integer.class);
				NAME_MATERIAL = getMaterial("Name.Material");
				NAME_NAME = getString("Name.Name");
				NAME_LORE = getStringList("Name.Lore");

				DESCRIPTION_SLOTS = getList("Description.Slots", Integer.class);
				DESCRIPTION_MATERIAL = getMaterial("Description.Material");
				DESCRIPTION_NAME = getString("Description.Name");
				DESCRIPTION_LORE = getStringList("Description.Lore");

				ICON_SLOTS = getList("Icon.Slots", Integer.class);
				ICON_NAME = getString("Icon.Name");
				ICON_LORE = getStringList("Icon.Lore");

				RESET_SLOTS = getList("Reset.Slots", Integer.class);
				RESET_MATERIAL = getMaterial("Reset.Material");
				RESET_NAME = getString("Reset.Name");
				RESET_LORE = getStringList("Reset.Lore");

				DELETE_SLOTS = getList("Delete.Slots", Integer.class);
				DELETE_MATERIAL = getMaterial("Delete.Material");
				DELETE_NAME = getString("Delete.Name");
				DELETE_LORE = getStringList("Delete.Lore");
			}
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
