package com.kiranhart.cosmicvaults.inventories;

import com.kiranhart.cosmicvaults.Core;
import com.kiranhart.cosmicvaults.api.CosmicVaultAPI;
import com.kiranhart.cosmicvaults.api.XMaterial;
import com.kiranhart.cosmicvaults.statics.CosmicLang;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 3/13/2020
 * Time Created: 1:56 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class VaultSelectionInventory extends HartInventory {

    private Player p;

    public VaultSelectionInventory(Player p) {
        this.p = p;
        this.defaultSize = CosmicVaultAPI.get().getMaxSelectionMenu(this.p);
        this.defaultTitle = ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("guis.vault-selection.title"));
    }

    @Override
    public void onClick(InventoryClickEvent e, int slot) {
        e.setCancelled(true);
        Player p = (Player) e.getWhoClicked();

        if (slot >= 0 && slot <= 53) {

            int page = slot + 1;

           if (!CosmicVaultAPI.canUseVault(p, page)) {
               Core.getInstance().getLocale().getMessage(CosmicLang.NO_PERMISSION).sendPrefixedMessage(p);
               return;
           }

            if (e.getClick() == ClickType.LEFT) {
                p.openInventory(new PlayerVaultInventory(p, page).getInventory());
                Core.getInstance().getOpenedVault().put(p.getUniqueId(), page);
                return;
            }

            if (e.getClick() == ClickType.MIDDLE) {
                new AnvilGUI.Builder().onComplete((player, text) -> {
                    Core.getInstance().getDataFile().getConfig().set("players." + p.getUniqueId().toString() + "." + page + ".name", text);
                    Core.getInstance().getDataFile().saveConfig();
                    Core.getInstance().getLocale().getMessage(CosmicLang.NAME_CHANGED).processPlaceholder("vault_number", page).processPlaceholder("vault_name", text).sendPrefixedMessage(p);
                    return AnvilGUI.Response.close();
                }).text("Enter New Name").item(XMaterial.PAPER.parseItem()).plugin(Core.getInstance()).open(p);
            }

            if (e.getClick() == ClickType.RIGHT) {
                if (Core.getInstance().getConfig().getBoolean("disable-icon-selection")) {
                    return;
                }

                p.openInventory(new IconSelectionInventory().getInventory());
                Core.getInstance().getVaultedit().put(p.getUniqueId(), page);
                return;
            }
        }
    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, this.defaultSize, this.defaultTitle);
        int slot = 0;
        int vault = 1;

        while (slot < CosmicVaultAPI.get().getMaxSelectionMenu(p)) {

            if (CosmicVaultAPI.canUseVault(p, vault)) {
                inventory.setItem(slot, CosmicVaultAPI.get().vaultItem(p, vault));
            } else {
                ItemStack locked = XMaterial.matchXMaterial(Core.getInstance().getConfig().getString("global-item.locked-item.item")).get().parseItem();
                ItemMeta meta = locked.getItemMeta();
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("global-item.locked-item.name")));
                List<String> lore = new ArrayList<>();
                Core.getInstance().getConfig().getStringList("global-item.locked-item.lore").forEach(line -> lore.add(ChatColor.translateAlternateColorCodes('&', line)));
                meta.setLore(lore);
                locked.setItemMeta(meta);
                inventory.setItem(slot, locked);
            }

            slot++;
            vault++;
        }

        return inventory;
    }
}
