package com.kiranhart.cosmicvaults.inventories;

import com.google.common.collect.Lists;
import com.kiranhart.cosmicvaults.Core;
import com.kiranhart.cosmicvaults.api.CosmicVaultAPI;
import com.kiranhart.cosmicvaults.api.XMaterial;
import com.kiranhart.cosmicvaults.statics.CosmicLang;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 3/13/2020
 * Time Created: 2:13 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class IconSelectionInventory extends HartInventory {

    private List<List<ItemStack>> chunks;

    public IconSelectionInventory() {
        this.page = 1;
        this.defaultTitle = ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("guis.icon-selection.title"));
        chunks = Lists.partition(Core.getInstance().getVaultIcons(), 45);
        
        if (chunks.get(this.page - 1).size() <= 9) this.defaultSize = 9;
        if (chunks.get(this.page - 1).size() >= 10 && chunks.get(this.page - 1).size() <= 18) this.defaultSize = 18;
        if (chunks.get(this.page - 1).size() >= 19 && chunks.get(this.page - 1).size() <= 27) this.defaultSize = 27;
        if (chunks.get(this.page - 1).size() >= 28 && chunks.get(this.page - 1).size() <= 36) this.defaultSize = 36;
        if (chunks.get(this.page - 1).size() >= 37 && chunks.get(this.page - 1).size() <= 45) this.defaultSize = 45;
        if (chunks.get(this.page - 1).size() >= 54) this.defaultSize = 54;
    }

    @Override
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        Bukkit.getServer().getScheduler().runTaskLater(Core.getInstance(), () -> p.openInventory(new VaultSelectionInventory(p).getInventory()), 1L);
    }

    @Override
    public void onClick(InventoryClickEvent e, int slot) {
        e.setCancelled(true);
        Player p = (Player) e.getWhoClicked();

        ItemStack is = e.getCurrentItem();
        int vault = Core.getInstance().getVaultedit().get(p.getUniqueId());
        String name = Core.getInstance().getDataFile().getConfig().getString("players." + p.getUniqueId().toString() + "." + vault + ".name");

        Core.getInstance().getDataFile().getConfig().set("players." + p.getUniqueId().toString() + "." + vault + ".icon", XMaterial.matchXMaterial(is).parseMaterial().name());
        Core.getInstance().getDataFile().getConfig().set("players." + p.getUniqueId().toString() + "." + vault + ".name", name);

        Core.getInstance().getDataFile().saveConfig();
        Core.getInstance().getVaultedit().remove(p.getUniqueId());
        p.openInventory(new VaultSelectionInventory(p).getInventory());

        Core.getInstance().getLocale().getMessage(CosmicLang.ICON_CHANGE).processPlaceholder("vault_number", vault).processPlaceholder("item", StringUtils.capitalize(XMaterial.matchXMaterial(is).parseMaterial().name().toLowerCase().replace("_", " "))).sendPrefixedMessage(p);
    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, this.defaultSize, this.defaultTitle);
        chunks.get(this.page - 1).forEach(item -> inventory.setItem(inventory.firstEmpty(), item));

        if (chunks.get(this.page - 1).size() >= 54) {
            inventory.setItem(48, CosmicVaultAPI.get().createGlobalItem("prev-page"));
            inventory.setItem(50, CosmicVaultAPI.get().createGlobalItem("next-page"));
        }

        return inventory;
    }
}
