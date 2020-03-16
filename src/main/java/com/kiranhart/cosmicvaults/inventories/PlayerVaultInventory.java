package com.kiranhart.cosmicvaults.inventories;
/*
    Created by Kiran Hart
    Date: March / 09 / 2020
    Time: 11:49 a.m.
*/

import com.kiranhart.cosmicvaults.Core;
import com.kiranhart.cosmicvaults.api.CosmicVaultAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class PlayerVaultInventory extends HartInventory {

    private Player p;
    private int vault;

    public PlayerVaultInventory(Player p, int vault) {
        this.p = p;
        this.vault = vault;
    }

    @Override
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();

        if (!Core.getInstance().getOpenedVault().containsKey(p.getUniqueId())) {
            return;
        }

        String name = Core.getInstance().getConfig().getString("guis.vault-selection.default-item-name").replace("{vaultnumber}", String.valueOf(vault));
        String icon = Core.getInstance().getConfig().getString("guis.vault-selection.default-item");

        if (!Core.getInstance().getDataFile().getConfig().contains("players." + p.getUniqueId().toString() + "." + vault)) {
            Core.getInstance().getDataFile().getConfig().set("players." + p.getUniqueId().toString() + "." + vault + ".icon", icon);
            Core.getInstance().getDataFile().getConfig().set("players." + p.getUniqueId().toString() + "." + vault + ".name", ChatColor.translateAlternateColorCodes('&', name));
            Core.getInstance().getDataFile().saveConfig();
        }

        for (int i = 0; i < e.getInventory().getSize(); i++) {
            Core.getInstance().getDataFile().getConfig().set("players." + p.getUniqueId().toString() + "." + vault + ".contents." + i, e.getInventory().getItem(i));
        }

        Core.getInstance().getDataFile().saveConfig();
        Core.getInstance().getOpenedVault().remove(p.getUniqueId());
    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, CosmicVaultAPI.get().getMaxSize(p), ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("guis.player-vault.title").replace("%vault_number%", String.valueOf(vault))));

        if (Core.getInstance().getDataFile().getConfig().contains("players." + p.getUniqueId().toString() + "." + vault)) {

            // no contents
            if (!Core.getInstance().getDataFile().getConfig().contains("players." + p.getUniqueId().toString() + "." + vault + ".contents")) {
                return inventory;
            }

            // contents
            for (String keys : Core.getInstance().getDataFile().getConfig().getConfigurationSection("players." + p.getUniqueId().toString() + "." + vault + ".contents").getKeys(false)) {
                int slot = Integer.parseInt(keys);
                inventory.setItem(slot, Core.getInstance().getDataFile().getConfig().getItemStack("players." + p.getUniqueId().toString() + "." + vault + ".contents." + keys));
            }

        } else {
            return inventory;
        }
        return inventory;
    }
}
