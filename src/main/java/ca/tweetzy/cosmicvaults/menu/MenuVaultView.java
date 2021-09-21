package ca.tweetzy.cosmicvaults.menu;

import ca.tweetzy.cosmicvaults.api.CosmicVaultsAPI;
import ca.tweetzy.cosmicvaults.api.events.VaultCloseEvent;
import ca.tweetzy.cosmicvaults.impl.Vault;
import ca.tweetzy.cosmicvaults.settings.Settings;
import ca.tweetzy.tweety.Common;
import ca.tweetzy.tweety.collection.StrictMap;
import ca.tweetzy.tweety.menu.Menu;
import ca.tweetzy.tweety.menu.model.MenuClickLocation;
import ca.tweetzy.tweety.remain.CompMaterial;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 15 2021
 * Time Created: 11:23 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class MenuVaultView extends Menu {

	private final Vault vault;
	private final boolean returnToSelection;

	public MenuVaultView(@NonNull final Vault vault) {
		this(vault, false);
	}

	public MenuVaultView(@NonNull final Vault vault, final boolean returnToSelection) {
		this.vault = vault;
		this.returnToSelection = returnToSelection;
		this.vault.setOpen(true);
		setTitle(this.vault.getName());
		setSize(9 * this.vault.getRows());
	}

	@Override
	public ItemStack getItemAt(int slot) {
		return this.vault.getContents().getOrDefault(slot, CompMaterial.AIR.toItem());
	}

	@Override
	protected void onMenuClose(Player player, Inventory inventory) {
		StrictMap<Integer, ItemStack> contents = new StrictMap<>();

		for (int i = 0; i < inventory.getSize(); i++) {
			final ItemStack slotItem = inventory.getItem(i);
			if (slotItem == null || slotItem.getType() == CompMaterial.AIR.toMaterial()) continue;
			contents.put(i, slotItem);
		}

		this.vault.setContents(contents);

		if (Settings.SAVE_TO_FILE_AFTER_EVERY_VAULT_CLOSE)
			CosmicVaultsAPI.saveVault(this.vault);

		this.vault.setOpen(false);
		Common.callEvent(new VaultCloseEvent(this.vault));
		if (this.returnToSelection)
			new MenuVaultSelection(CosmicVaultsAPI.getVaultPlayer(player)).displayTo(player);
	}

	@Override
	protected void onMenuClick(Player player, int slot, ItemStack clicked) {
		CosmicVaultsAPI.addEditedVault(this.vault.getUUID());
	}

	@Override
	protected boolean isActionAllowed(MenuClickLocation location, int slot, ItemStack clicked, ItemStack cursor) {
		if (location == MenuClickLocation.PLAYER_INVENTORY && clicked != null && Settings.BLOCKED_MATERIALS.contains(CompMaterial.fromItem(clicked))) return false;
		return slot >= 0 && slot <= this.vault.getRows() * 9;
	}

	@Override
	public boolean allowShiftActions() {
		return true;
	}
}
