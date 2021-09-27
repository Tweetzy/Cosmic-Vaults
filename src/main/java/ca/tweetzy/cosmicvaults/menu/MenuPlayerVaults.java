package ca.tweetzy.cosmicvaults.menu;

import ca.tweetzy.cosmicvaults.api.events.VaultOpenEvent;
import ca.tweetzy.cosmicvaults.impl.Vault;
import ca.tweetzy.cosmicvaults.settings.Localization;
import ca.tweetzy.cosmicvaults.settings.Settings;
import ca.tweetzy.tweety.Common;
import ca.tweetzy.tweety.menu.MenuPagged;
import ca.tweetzy.tweety.menu.model.ItemCreator;
import ca.tweetzy.tweety.menu.model.SkullCreator;
import ca.tweetzy.tweety.model.Replacer;
import ca.tweetzy.tweety.remain.CompMaterial;
import ca.tweetzy.tweety.remain.Remain;
import ca.tweetzy.tweety.settings.SimpleSettings;
import lombok.NonNull;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.UUID;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 20 2021
 * Time Created: 9:43 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class MenuPlayerVaults extends MenuPagged<Vault> {

	private final UUID owner;

	public MenuPlayerVaults(@NonNull final UUID owner, @NonNull final Collection<Vault> vaults) {
		super(null, 6, 9 * 4, vaults);
		setTitle("&d&l" + Remain.getOfflinePlayerByUUID(owner).getName());
		this.owner = owner;
	}

	@Override
	protected ItemStack convertToItemStack(Vault vault) {
		return ItemCreator
				.of(CompMaterial.fromMaterial(vault.getIcon()))
				.name(Settings.VaultSelectionMenu.Items.OPENED_NAME.replace("{vault_title}", vault.getName()))
				.lores(Replacer.replaceArray(Settings.VaultSelectionMenu.Items.OPENED_LORE,
						"vault_description", vault.getDescription(),
						"vault_creation_date", SimpleSettings.TIMESTAMP_FORMAT.format(vault.getCreationDate()),
						"vault_item_count", vault.getContents().size()
				))
				.build().make();
	}

	@Override
	public ItemStack getItemAt(int slot) {
		if (slot == 4) return ItemCreator.of(SkullCreator.itemFromUuid(owner)).name("&d&l" + Remain.getOfflinePlayerByUUID(owner).getName()).build().make();
		return super.getItemAt(slot);
	}

	@Override
	protected void onPageClick(Player player, Vault vault, ClickType click) {
		// open the vault
		if (click == ClickType.LEFT) {
			if (!Common.callEvent(new VaultOpenEvent(vault))) return;
			final OfflinePlayer vaultOwner = Remain.getOfflinePlayerByUUID(this.owner);

			if (vault.isOpen() && !vaultOwner.isOnline()) {
				Common.tell(player, Localization.VaultError.VAULT_OPENED_ADMIN.replace("{vault_number}", String.valueOf(vault.getNumber())));
				return;
			}

			if (vault.isOpen() && vaultOwner.isOnline() && vaultOwner.getPlayer() != null) {
				vaultOwner.getPlayer().closeInventory();
				vault.setOpen(false);
			}

			new MenuVaultView(vault).displayTo(player);
		}

		if (click == ClickType.RIGHT) {
			new MenuVaultEdit(vault).displayTo(player);
		}
	}

	@Override
	protected int startingSlot() {
		return 9;
	}
}
