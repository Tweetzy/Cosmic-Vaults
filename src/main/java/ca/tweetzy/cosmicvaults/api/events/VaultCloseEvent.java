package ca.tweetzy.cosmicvaults.api.events;

import ca.tweetzy.cosmicvaults.impl.Vault;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 14 2021
 * Time Created: 11:43 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class VaultCloseEvent extends AbstractVaultEvent {

	public VaultCloseEvent(Vault vault) {
		super(vault);
	}
}
