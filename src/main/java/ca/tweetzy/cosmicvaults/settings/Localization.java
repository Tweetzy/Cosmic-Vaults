package ca.tweetzy.cosmicvaults.settings;

import ca.tweetzy.tweety.settings.SimpleLocalization;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 14 2021
 * Time Created: 11:40 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class Localization extends SimpleLocalization {

	public static final class VaultError {

		public static String NOT_ALLOWED_TO_USE_VAULT;
		public static String VAULT_ALREADY_OPEN;
		public static String VAULT_OPENED_ADMIN;
		public static String BLOCKED_ITEM;
		public static String NOT_A_NUMBER;
		public static String VAULT_CANNOT_BE_ZERO;
		public static String CANNOT_FIND_VAULT;
		public static String PLAYER_NOT_FOUND;
		public static String PLAYER_HAS_NO_VAULTS;

		private static void init() {
			pathPrefix("Vault Error");

			NOT_ALLOWED_TO_USE_VAULT = getString("Not Allowed To Use Vault");
			VAULT_ALREADY_OPEN = getString("Vault Already Open");
			VAULT_OPENED_ADMIN = getString("Vault Opened Admin");
			BLOCKED_ITEM = getString("Blocked Item");
			NOT_A_NUMBER = getString("Not A Number");
			VAULT_CANNOT_BE_ZERO = getString("Vault Cannot Be Zero");
			CANNOT_FIND_VAULT = getString("Cannot Find Vault");
			PLAYER_NOT_FOUND = getString("Player Not Found");
			PLAYER_HAS_NO_VAULTS = getString("Player Has No Vaults");
		}
	}

	public static final class Prompt {

		public static String ENTER_VAULT_TITLE;
		public static String ENTER_VAULT_DESCRIPTION;

		private static void init() {
			pathPrefix("Prompt");
			ENTER_VAULT_TITLE = getString("Enter Vault Title");
			ENTER_VAULT_DESCRIPTION = getString("Enter Vault Description");
		}
	}

	public static final class VaultIconChange {

		public static String CHANGED;
		public static String NO_PERMISSION;

		private static void init() {
			pathPrefix("Vault Icon Change");

			CHANGED = getString("Changed");
			NO_PERMISSION = getString("No Permission");
		}
	}

	public static final class VaultNameChange {

		public static String CHANGED;
		public static String NO_PERMISSION;

		private static void init() {
			pathPrefix("Vault Name Change");

			CHANGED = getString("Changed");
			NO_PERMISSION = getString("No Permission");
		}
	}

	public static final class VaultDescriptionChange {

		public static String CHANGED;
		public static String NO_PERMISSION;

		private static void init() {
			pathPrefix("Vault Description Change");

			CHANGED = getString("Changed");
			NO_PERMISSION = getString("No Permission");
		}
	}

	public static final class VaultReset {

		public static String PLAYER;
		public static String ADMIN;

		private static void init() {
			pathPrefix("Vault Reset");

			PLAYER = getString("Player");
			ADMIN = getString("Admin");
		}
	}

	public static final class VaultDelete {

		public static String PLAYER;
		public static String ADMIN;

		private static void init() {
			pathPrefix("Vault Delete");

			PLAYER = getString("Player");
			ADMIN = getString("Admin");
		}
	}

	@Override
	protected int getConfigVersion() {
		return 1;
	}
}
