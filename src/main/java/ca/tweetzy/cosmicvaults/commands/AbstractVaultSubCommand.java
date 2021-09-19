package ca.tweetzy.cosmicvaults.commands;

import ca.tweetzy.cosmicvaults.CosmicVaults;
import ca.tweetzy.tweety.command.SimpleSubCommand;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 15 2021
 * Time Created: 10:08 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public abstract class AbstractVaultSubCommand extends SimpleSubCommand {

	protected AbstractVaultSubCommand(String aliases) {
		super(CosmicVaults.getInstance().getMainCommand(), aliases);
	}
}
