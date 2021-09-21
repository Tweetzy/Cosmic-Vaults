package ca.tweetzy.cosmicvaults.menu;

import ca.tweetzy.cosmicvaults.api.CosmicVaultsAPI;
import ca.tweetzy.cosmicvaults.impl.Vault;
import ca.tweetzy.cosmicvaults.settings.Localization;
import ca.tweetzy.cosmicvaults.settings.Settings;
import ca.tweetzy.tweety.Common;
import ca.tweetzy.tweety.collection.StrictMap;
import ca.tweetzy.tweety.menu.MenuPagged;
import ca.tweetzy.tweety.menu.model.ItemCreator;
import ca.tweetzy.tweety.menu.model.SkullCreator;
import ca.tweetzy.tweety.remain.Remain;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.UUID;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 20 2021
 * Time Created: 9:20 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class MenuPlayerSelection extends MenuPagged<UUID> {

	public MenuPlayerSelection() {
		super(null, 6, 9 * 4, CosmicVaultsAPI.getAllPlayerIdsWithVaults());
		setTitle(Settings.PlayerSelectionMenu.TITLE);
	}

	@Override
	protected ItemStack convertToItemStack(UUID item) {
		return ItemCreator.of(SkullCreator.itemFromUuid(item))
				.name(Settings.PlayerSelectionMenu.Items.PLAYER_NAME.replace("{player_name}", Objects.requireNonNull(Remain.getOfflinePlayerByUUID(item).getName())))
				.lores(Settings.PlayerSelectionMenu.Items.PLAYER_LORE)
				.build().make();
	}

	@Override
	protected void onPageClick(Player player, UUID ownerUUID, ClickType click) {
		final StrictMap<Integer, Vault> foundVaults = CosmicVaultsAPI.getVaultsByPlayer(ownerUUID);
		if (foundVaults.size() == 0) {
			Common.tell(player, Localization.VaultError.PLAYER_HAS_NO_VAULTS.replace("{player}", Objects.requireNonNull(Remain.getOfflinePlayerByUUID(ownerUUID).getName())));
			return;
		}

		new MenuPlayerVaults(ownerUUID, foundVaults.values()).displayTo(player);
	}

	@Override
	protected int startingSlot() {
		return 9;
	}
}
