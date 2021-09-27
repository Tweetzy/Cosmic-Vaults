package ca.tweetzy.cosmicvaults.model;

import ca.tweetzy.tweety.command.annotation.Permission;
import ca.tweetzy.tweety.command.annotation.PermissionGroup;
import ca.tweetzy.tweety.constants.TweetyPermissions;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 20 2021
 * Time Created: 10:09 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class Permissions extends TweetyPermissions {

	@Permission("Controls the size of the player's vault, # must be multiples of 9 between 9 and 54")
	public static final String VAULT_SIZE = "cosmicvaults.maxvaultsize.#";

	@Permission("Controls the size of the player's selection menu, # must be multiples of 9 between 9 and 54")
	public static final String VAULT_SELECTION_SIZE = "cosmicvaults.maxvaultselectionsize.#";

	@Permission("Determines how many vaults a player can have / use # must be 1 or higher")
	public static final String MAX_ALLOWED_VAULTS = "cosmicvaults.maxallowedvaults.#";

	@PermissionGroup("Vault Related")
	public static final class Vault {

		@Permission("Allows the user to edit their vault's name")
		public static final String EDIT_NAME = "cosmicvaults.vaultedit.name";

		@Permission("Allows the user to edit their vault's description")
		public static final String EDIT_DESCRIPTION = "cosmicvaults.vaultedit.description";

		@Permission("Allows the user to edit their vault's icon")
		public static final String EDIT_ICON = "cosmicvaults.vaultedit.icon";

		@Permission("Allows the user to reset their vault through the edit menu")
		public static final String EDIT_RESET = "cosmicvaults.vaultedit.reset";

		@Permission("Allows the user to delete their vault through the edit menu")
		public static final String EDIT_DELETE = "cosmicvaults.vaultedit.delete";
	}

	@PermissionGroup("Command Related")
	public static final class Command {

		@Permission("Allows access to admin control of vaults")
		public static final String ADMIN = "cosmicvaults.command.admin";

		@Permission("Allows you to delete your vault")
		public static final String DELETE = "cosmicvaults.command.delete";

		@Permission("Allows you to delete vaults owned by other players")
		public static final String DELETE_OTHERS = "cosmicvaults.command.delete.others";

		@Permission("Allows you to reset your vault")
		public static final String RESET = "cosmicvaults.command.reset";

		@Permission("Allows you to reset vaults owned by other players")
		public static final String RESET_OTHERS = "cosmicvaults.command.reset.others";

		@Permission("Allows the user to run the conversion command")
		public static final String CONVERT = "cosmicvaults.command.convert";
	}
}
