package ca.tweetzy.cosmicvaults.menu;

import ca.tweetzy.cosmicvaults.api.CosmicVaultsAPI;
import ca.tweetzy.cosmicvaults.api.events.VaultOpenEvent;
import ca.tweetzy.cosmicvaults.impl.Vault;
import ca.tweetzy.cosmicvaults.impl.VaultPlayer;
import ca.tweetzy.cosmicvaults.settings.Localization;
import ca.tweetzy.cosmicvaults.settings.Settings;
import ca.tweetzy.tweety.Common;
import ca.tweetzy.tweety.MathUtil;
import ca.tweetzy.tweety.collection.StrictMap;
import ca.tweetzy.tweety.menu.Menu;
import ca.tweetzy.tweety.menu.model.ItemCreator;
import ca.tweetzy.tweety.model.Replacer;
import ca.tweetzy.tweety.remain.CompMaterial;
import ca.tweetzy.tweety.settings.SimpleSettings;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 15 2021
 * Time Created: 12:38 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class MenuVaultSelection extends Menu {

	private final VaultPlayer vaultPlayer;
	private final StrictMap<Integer, Vault> opened;

	public MenuVaultSelection(@NonNull final VaultPlayer vaultPlayer) {
		this.vaultPlayer = vaultPlayer;
		this.opened = CosmicVaultsAPI.getVaultsByPlayer(this.vaultPlayer.getPlayer().getUniqueId());
		setTitle(Settings.VaultSelectionMenu.TITLE);
		setSize(MathUtil.max(9, 9 * vaultPlayer.getMaxVaultSelectionSize()));
	}

	@Override
	public ItemStack getItemAt(int slot) {
		return createVaultIcon(slot + 1);
	}

	@Override
	protected void onMenuClick(Player player, int slot, InventoryAction action, ClickType click, ItemStack cursor, ItemStack clicked, boolean cancelled) {
		final int vaultNumber = slot + 1;
		if (vaultNumber > this.vaultPlayer.getMaxAllowedVaults()) return;

		Vault vault = this.opened.get(vaultNumber);
		if (vault == null) {
			vault = new Vault(this.vaultPlayer, vaultNumber);
			CosmicVaultsAPI.addVault(vault);
			// adding to the edit list bc its the first time the vault was opened, so gotta
			// save whatever even if they don't put anything inside
			CosmicVaultsAPI.addEditedVault(vault.getUUID());
		}

		// open the vault
		if (click == ClickType.LEFT) {
			if (!Common.callEvent(new VaultOpenEvent(vault))) return;
			if (vault.isOpen()) {
				Common.tell(player, Localization.VaultError.VAULT_ALREADY_OPEN.replace("{vault_number}", String.valueOf(vaultNumber)));
				return;
			}

			new MenuVaultView(vault, true).displayTo(player);
		}

		if (click == ClickType.RIGHT) {
			new MenuVaultEdit(vault).displayTo(player);
		}
	}

	private ItemStack createVaultIcon(int vaultNumber) {
		if (vaultNumber > this.vaultPlayer.getMaxAllowedVaults()) return ItemCreator
				.of(Settings.VaultSelectionMenu.Items.LOCKED_MATERIAL)
				.name(Settings.VaultSelectionMenu.Items.LOCKED_NAME)
				.lores(Settings.VaultSelectionMenu.Items.LOCKED_LORE)
				.build().make();

		final Vault vault = this.opened.get(vaultNumber);

		return ItemCreator
				.of(vault == null ? Settings.VaultSelectionMenu.Items.UNOPENED_MATERIAL : CompMaterial.fromMaterial(vault.getIcon()))
				.name(vault == null ? Settings.VaultSelectionMenu.Items.UNOPENED_NAME : Settings.VaultSelectionMenu.Items.OPENED_NAME.replace("{vault_title}", vault.getName()))
				.lores(vault == null ? Settings.VaultSelectionMenu.Items.UNOPENED_LORE : Replacer.replaceArray(Settings.VaultSelectionMenu.Items.OPENED_LORE,
						"vault_description", vault.getDescription(),
						"vault_creation_date", SimpleSettings.TIMESTAMP_FORMAT.format(vault.getCreationDate()),
						"vault_item_count", vault.getContents().size()
				))
				.build().make();
	}
}
