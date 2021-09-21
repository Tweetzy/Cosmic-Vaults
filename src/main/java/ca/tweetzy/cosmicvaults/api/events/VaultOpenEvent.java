package ca.tweetzy.cosmicvaults.api.events;

import ca.tweetzy.cosmicvaults.impl.Vault;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 14 2021
 * Time Created: 11:43 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class VaultOpenEvent extends AbstractVaultEvent implements Cancellable {

	@Getter
	@Setter
	private boolean cancelled;

	public VaultOpenEvent(Vault vault) {
		super(vault);
	}
}
