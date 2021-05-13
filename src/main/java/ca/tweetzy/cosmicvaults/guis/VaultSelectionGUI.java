package ca.tweetzy.cosmicvaults.guis;

import ca.tweetzy.core.compatibility.XMaterial;
import ca.tweetzy.core.gui.Gui;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.cosmicvaults.CosmicVaults;
import ca.tweetzy.cosmicvaults.api.CosmicVaultAPI;
import ca.tweetzy.cosmicvaults.api.Settings;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/15/2020
 * Time Created: 3:04 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class VaultSelectionGUI extends Gui {

    final Player player;

    public VaultSelectionGUI(Player player) {
        this.player = player;
        setTitle(TextUtils.formatText(Settings.GUI_VAULT_SELECTION_TITLE.getString()));
        setRows(CosmicVaultAPI.get().getMaxSelectionMenu(this.player) / 9);
        setAcceptsItems(false);
        draw();
    }

    private void draw() {
        int slot = 0;
        int vault = 1;

        while (slot < CosmicVaultAPI.get().getMaxSelectionMenu(this.player)) {
            int finalSlot = slot;
            setButton(slot++, CosmicVaultAPI.get().canUseVault(this.player, vault) ? CosmicVaultAPI.get().vaultItem(this.player, vault) : CosmicVaultAPI.get()._locked(), e -> {
                int page = finalSlot + 1;

                if (!CosmicVaultAPI.get().canUseVault(this.player, page)) {
                    CosmicVaults.getInstance().getLocale().getMessage("pv.no-permission").sendPrefixedMessage(this.player);
                    return;
                }

                if (e.clickType == ClickType.LEFT) {
                    if (CosmicVaults.getInstance().getOpenedVault().containsKey(this.player.getUniqueId())) {
                        CosmicVaults.getInstance().getLocale().getMessage("vaultopenalready").sendPrefixedMessage(this.player);
                    } else {
                        CosmicVaults.getInstance().getGuiManager().showGUI(player, new PlayerVaultGUI(this.player, page));
                        CosmicVaults.getInstance().getOpenedVault().put(this.player.getUniqueId(), page);
                    }
                }

                if (e.clickType == ClickType.MIDDLE) {
                    new AnvilGUI.Builder().onComplete((player, text) -> {
                        CosmicVaults.getInstance().getData().set("players." + this.player.getUniqueId().toString() + "." + page + ".name", text);

                        if (!CosmicVaults.getInstance().getData().contains("players." + this.player.getUniqueId().toString() + "." + page + ".icon")) {
                            CosmicVaults.getInstance().getData().set("players." + this.player.getUniqueId().toString() + "." + page + ".icon", Settings.GUI_VAULT_SELECTION_DEFAULT_ITEM.getString());
                        }

                        CosmicVaults.getInstance().getData().save();
                        CosmicVaults.getInstance().getLocale().getMessage("namechanged").processPlaceholder("vault_number", page).processPlaceholder("vault_name", text).sendPrefixedMessage(this.player);
                        return AnvilGUI.Response.close();
                    }).text("Enter New Name").itemLeft(XMaterial.PAPER.parseItem()).item(XMaterial.PAPER.parseItem()).plugin(CosmicVaults.getInstance()).preventClose().open(this.player);
                }

                if (e.clickType == ClickType.RIGHT) {
                    if (Settings.DISABLE_ICON_SELECTION.getBoolean()) {
                        return;
                    }

                    e.manager.showGUI(this.player, new IconSelectionGUI());
                    CosmicVaults.getInstance().getVaultEdit().put(this.player.getUniqueId(), page);
                }

            });
            vault++;
        }
    }
}
