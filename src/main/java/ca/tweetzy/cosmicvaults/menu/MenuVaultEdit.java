package ca.tweetzy.cosmicvaults.menu;

import ca.tweetzy.cosmicvaults.api.CosmicVaultsAPI;
import ca.tweetzy.cosmicvaults.api.events.VaultDescriptionChangeEvent;
import ca.tweetzy.cosmicvaults.api.events.VaultNameChangeEvent;
import ca.tweetzy.cosmicvaults.impl.Vault;
import ca.tweetzy.cosmicvaults.model.Permissions;
import ca.tweetzy.cosmicvaults.settings.Localization;
import ca.tweetzy.cosmicvaults.settings.Settings;
import ca.tweetzy.tweety.Common;
import ca.tweetzy.tweety.PlayerUtil;
import ca.tweetzy.tweety.conversation.SimplePrompt;
import ca.tweetzy.tweety.menu.Menu;
import ca.tweetzy.tweety.menu.button.Button;
import ca.tweetzy.tweety.menu.button.ButtonConversation;
import ca.tweetzy.tweety.menu.button.ButtonMenu;
import ca.tweetzy.tweety.menu.model.ItemCreator;
import ca.tweetzy.tweety.model.Replacer;
import ca.tweetzy.tweety.remain.CompMaterial;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 19 2021
 * Time Created: 5:53 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class MenuVaultEdit extends Menu {

	private final Vault vault;
	private final Button nameButton;
	private final Button descriptionButton;
	private final Button iconButton;
	private final Button resetButton;
	private final Button deleteButton;

	public MenuVaultEdit(Vault vault) {
		this.vault = vault;
		setTitle(Settings.VaultEditMenu.TITLE.replace("{vault_number}", String.valueOf(this.vault.getNumber())));
		setSize(9 * Settings.VaultEditMenu.ROWS);

		nameButton = new ButtonConversation(new SimplePrompt() {
			@Override
			protected String getPrompt(ConversationContext context) {
				return Localization.Prompt.ENTER_VAULT_TITLE;
			}

			@Nullable
			@Override
			protected Prompt acceptValidatedInput(@NotNull ConversationContext conversationContext, @NotNull String content) {
				if (content.length() >= 3) {
					if (!PlayerUtil.hasPerm(getViewer(), Permissions.Vault.EDIT_NAME)) {
						Common.tell(getViewer(), Localization.VaultNameChange.NO_PERMISSION);
						return END_OF_CONVERSATION;
					}

					if (!Common.callEvent(new VaultNameChangeEvent(vault))) return END_OF_CONVERSATION;

					vault.setName(content);
					CosmicVaultsAPI.addEditedVault(vault.getUUID());
					Common.tell(getViewer(), Localization.VaultNameChange.CHANGED.replace("{vault_name}", content));
				}
				return Prompt.END_OF_CONVERSATION;
			}

		}, ItemCreator.of(Settings.VaultEditMenu.Items.NAME_MATERIAL, Settings.VaultEditMenu.Items.NAME_NAME, Replacer.replaceArray(Settings.VaultEditMenu.Items.NAME_LORE, "vault_title", this.vault.getName())));

		descriptionButton = new ButtonConversation(new SimplePrompt() {
			@Override
			protected String getPrompt(ConversationContext context) {
				return Localization.Prompt.ENTER_VAULT_DESCRIPTION;
			}

			@Nullable
			@Override
			protected Prompt acceptValidatedInput(@NotNull ConversationContext conversationContext, @NotNull String content) {
				if (content.length() >= 3) {
					if (!PlayerUtil.hasPerm(getViewer(), Permissions.Vault.EDIT_DESCRIPTION)) {
						Common.tell(getViewer(), Localization.VaultDescriptionChange.NO_PERMISSION);
						return END_OF_CONVERSATION;
					}

					if (!Common.callEvent(new VaultDescriptionChangeEvent(vault))) return END_OF_CONVERSATION;

					vault.setDescription(content);
					CosmicVaultsAPI.addEditedVault(vault.getUUID());
					Common.tell(getViewer(), Localization.VaultDescriptionChange.CHANGED.replace("{vault_description}", content));
				}
				return Prompt.END_OF_CONVERSATION;
			}

		}, ItemCreator.of(Settings.VaultEditMenu.Items.DESCRIPTION_MATERIAL, Settings.VaultEditMenu.Items.DESCRIPTION_NAME, Replacer.replaceArray(Settings.VaultEditMenu.Items.DESCRIPTION_LORE, "vault_description", this.vault.getDescription())));

		iconButton = new ButtonMenu(new MenuIconSelect(this.vault), ItemCreator.of(CompMaterial.fromMaterial(this.vault.getIcon()), Settings.VaultEditMenu.Items.ICON_NAME, Settings.VaultEditMenu.Items.ICON_LORE));

		resetButton = Button.makeSimple(ItemCreator.of(Settings.VaultEditMenu.Items.RESET_MATERIAL, Settings.VaultEditMenu.Items.RESET_NAME, Settings.VaultEditMenu.Items.RESET_LORE), player -> {
			if (!PlayerUtil.hasPerm(getViewer(), Permissions.Vault.EDIT_RESET)) {
				return;
			}

			CosmicVaultsAPI.resetVaultContents(player.getUniqueId(), this.vault.getNumber());
			Common.tell(player, Localization.VaultReset.PLAYER.replace("{vault_number}", String.valueOf(this.vault.getNumber())));
			this.newInstance().displayTo(player);
		});

		deleteButton = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType click) {
				if (!PlayerUtil.hasPerm(getViewer(), Permissions.Vault.EDIT_DELETE)) {
					return;
				}

				if (click == ClickType.MIDDLE) {
					CosmicVaultsAPI.deleteVault(player.getUniqueId(), vault.getNumber());
					Common.tell(player, Localization.VaultDelete.PLAYER.replace("{vault_number}", String.valueOf(vault.getNumber())));
					new MenuVaultSelection(CosmicVaultsAPI.getVaultPlayer(player)).displayTo(player);
				}
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(Settings.VaultEditMenu.Items.DELETE_MATERIAL, Settings.VaultEditMenu.Items.DELETE_NAME, Settings.VaultEditMenu.Items.DELETE_LORE).make();
			}
		};

	}

	@Override
	public Menu newInstance() {
		return new MenuVaultEdit(this.vault);
	}

	@Override
	public ItemStack getItemAt(int slot) {
		if (Settings.VaultEditMenu.Items.NAME_SLOTS.contains(slot)) return this.nameButton.getItem();
		if (Settings.VaultEditMenu.Items.DESCRIPTION_SLOTS.contains(slot)) return this.descriptionButton.getItem();
		if (Settings.VaultEditMenu.Items.ICON_SLOTS.contains(slot)) return this.iconButton.getItem();
		if (Settings.VaultEditMenu.Items.RESET_SLOTS.contains(slot)) return this.resetButton.getItem();
		if (Settings.VaultEditMenu.Items.DELETE_SLOTS.contains(slot)) return this.deleteButton.getItem();
		return Settings.VaultEditMenu.BACKGROUND_ITEM.toItem();
	}

	@Override
	protected void onMenuClose(Player player, Inventory inventory) {
		Common.runLater(1, player::updateInventory);
	}
}
