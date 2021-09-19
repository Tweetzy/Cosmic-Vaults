package ca.tweetzy.cosmicvaults.api.events;

import ca.tweetzy.cosmicvaults.impl.Vault;
import org.bukkit.event.Cancellable;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 14 2021
 * Time Created: 11:43 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class VaultOpenEvent extends AbstractVaultEvent implements Cancellable {

	private boolean cancelled;

	public VaultOpenEvent(Vault vault) {
		super(vault);
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancelled = cancel;
	}
}
