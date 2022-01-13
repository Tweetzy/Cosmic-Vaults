package ca.tweetzy.cosmicvaults.menu;

import ca.tweetzy.cosmicvaults.api.CosmicVaultsAPI;
import ca.tweetzy.cosmicvaults.api.events.VaultIconChangeEvent;
import ca.tweetzy.cosmicvaults.impl.Vault;
import ca.tweetzy.cosmicvaults.model.InventorySafeMaterials;
import ca.tweetzy.cosmicvaults.model.Permissions;
import ca.tweetzy.cosmicvaults.settings.Localization;
import ca.tweetzy.cosmicvaults.settings.Settings;
import ca.tweetzy.tweety.Common;
import ca.tweetzy.tweety.ItemUtil;
import ca.tweetzy.tweety.PlayerUtil;
import ca.tweetzy.tweety.menu.Menu;
import ca.tweetzy.tweety.menu.MenuPagged;
import ca.tweetzy.tweety.menu.model.ItemCreator;
import ca.tweetzy.tweety.remain.CompMaterial;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 19 2021
 * Time Created: 6:22 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class MenuIconSelect extends MenuPagged<CompMaterial> {

	private final Vault vault;

	public MenuIconSelect(Vault vault) {
		super(null, IntStream.rangeClosed(9, 44).boxed().collect(Collectors.toList()), Settings.USE_AVAILABLE_MATERIALS ? InventorySafeMaterials.get() : Settings.VAULT_ICONS);
		setTitle(Settings.VaultIconMenu.TITLE);
		this.vault = vault;
	}

	@Override
	public Menu newInstance() {
		return new MenuIconSelect(this.vault);
	}

	@Override
	protected ItemStack convertToItemStack(CompMaterial item) {
		return ItemCreator.of(item).hideTags(true).make();
	}

	@Override
	protected void onPageClick(Player player, CompMaterial item, ClickType click) {
		if (!PlayerUtil.hasPerm(getViewer(), Permissions.Vault.EDIT_ICON)) {
			Common.tell(getViewer(), Localization.VaultIconChange.NO_PERMISSION);
			new MenuVaultEdit(this.vault).displayTo(player);
			return;
		}

		if (Common.callEvent(new VaultIconChangeEvent(this.vault))) {
			this.vault.setIcon(item.getMaterial());
			CosmicVaultsAPI.addEditedVault(this.vault.getUUID());
			Common.tell(player, Localization.VaultIconChange.CHANGED.replace("{vault_icon}", ItemUtil.bountifyCapitalized(item)));
		}

		new MenuVaultEdit(this.vault).displayTo(player);
	}

	@Override
	protected void onMenuClose(Player player, Inventory inventory) {
		Common.runLater(1, player::updateInventory);
	}
}
