package ca.tweetzy.cosmicvaults.api.events;

import ca.tweetzy.cosmicvaults.impl.Vault;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 14 2021
 * Time Created: 11:44 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@AllArgsConstructor
public abstract class AbstractVaultEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();

	@Getter
	private Vault vault;

	@NotNull
	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
}
