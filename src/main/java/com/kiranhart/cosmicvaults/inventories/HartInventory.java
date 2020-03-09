package com.kiranhart.cosmicvaults.inventories;
/*
    Created by Kiran Hart
    Date: March / 09 / 2020
    Time: 11:49 a.m.
*/

import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;

public abstract class HartInventory implements InventoryHolder {

    protected String defaultTitle = ChatColor.translateAlternateColorCodes('&', "&ebDefault Title");
    protected int defaultSize = 54;
    protected int page = 1;

    public void onClick(InventoryClickEvent e) {
    }

    public void onClick(InventoryClickEvent e, int slot) {
    }

    public void onOpen(InventoryOpenEvent e) {
    }

    public void onClose(InventoryCloseEvent e) {
    }

    protected HartInventory setPage(int page) {
        if (this.page <= 0) {
            this.page = 1;
        } else {
            this.page = page;
        }
        return this;
    }

}
