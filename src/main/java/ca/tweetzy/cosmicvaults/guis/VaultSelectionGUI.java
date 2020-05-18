package ca.tweetzy.cosmicvaults.guis;

import ca.tweetzy.core.compatibility.XMaterial;
import ca.tweetzy.core.inventory.TInventory;
import ca.tweetzy.core.utils.items.ItemUtils;
import ca.tweetzy.cosmicvaults.CosmicVaults;
import ca.tweetzy.cosmicvaults.api.CosmicVaultAPI;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/15/2020
 * Time Created: 3:04 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class VaultSelectionGUI extends TInventory {

    private Player player;

    public VaultSelectionGUI(Player player) {
        this.player = player;
        setDynamic(false);
        setRows(CosmicVaultAPI.get().getMaxSelectionMenu(this.player) / 9);
        setTitle(CosmicVaults.getInstance().getConfig().getString("guis.vault-selection.title"));
    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, getSize(), getTitle());
        int slot = 0;
        int vault = 1;

        while (slot < CosmicVaultAPI.get().getMaxSelectionMenu(this.player)) {

            if (CosmicVaultAPI.get().canUseVault(this.player, vault)) {
                inventory.setItem(slot, CosmicVaultAPI.get().vaultItem(this.player, vault));
            } else {
                inventory.setItem(slot, ItemUtils.createConfigItem(CosmicVaults.getInstance().getConfig(), "global-item.locked-item"));
            }

            slot++;
            vault++;
        }

        return inventory;
    }

    @Override
    public void onClick(InventoryClickEvent e, int slot) {
        e.setCancelled(true);
        Player p = (Player) e.getWhoClicked();

        if (slot >= 0 && slot <= 53) {

            int page = slot + 1;

            if (!CosmicVaultAPI.get().canUseVault(p, page)) {
                CosmicVaults.getInstance().getLocale().getMessage("pv.no-permission").sendPrefixedMessage(p);
                return;
            }

            if (e.getClick() == ClickType.LEFT) {
                p.openInventory(new PlayerVaultGUI(p, page).getInventory());
                CosmicVaults.getInstance().getOpenedVault().put(p.getUniqueId(), page);
                return;
            }

            if (e.getClick() == ClickType.MIDDLE) {
                new AnvilGUI.Builder().onComplete((player, text) -> {
                    CosmicVaults.getInstance().getDataFile().getConfig().set("players." + p.getUniqueId().toString() + "." + page + ".name", text);
                    CosmicVaults.getInstance().getDataFile().saveConfig();
                    CosmicVaults.getInstance().getLocale().getMessage("namechanged").processPlaceholder("vault_number", page).processPlaceholder("vault_name", text).sendPrefixedMessage(p);
                    return AnvilGUI.Response.close();
                }).text("Enter New Name").item(XMaterial.PAPER.parseItem()).plugin(CosmicVaults.getInstance()).preventClose().open(p);
            }

            if (e.getClick() == ClickType.RIGHT) {
                if (CosmicVaults.getInstance().getConfig().getBoolean("disable-icon-selection")) {
                    return;
                }

                p.openInventory(new IconSelectionGUI().getInventory());
                CosmicVaults.getInstance().getVaultedit().put(p.getUniqueId(), page);
                return;
            }
        }
    }
}
