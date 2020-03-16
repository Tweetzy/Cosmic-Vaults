package com.kiranhart.cosmicvaults.api;

import com.kiranhart.cosmicvaults.Core;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 3/13/2020
 * Time Created: 2:13 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class CosmicVaultAPI {

    private static CosmicVaultAPI instance;

    private CosmicVaultAPI(){}

    public static CosmicVaultAPI get() {
        if (instance == null) {
            instance = new CosmicVaultAPI();
        }
        return instance;
    }

    /**
     * Used to load all the vault icons, only called once on enable
     */
    public void loadVaultIcons() {
        Core.getInstance().getConfig().getStringList("guis.icon-selection.items").forEach(item -> {
            ItemStack stack = XMaterial.matchXMaterial(item.toUpperCase()).get().parseItem();
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("guis.icon-selection.item-name").replace("%material_name%", StringUtils.capitalize(stack.getType().name().toLowerCase().replace("_", " ")))));
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            List<String> lore = new ArrayList<>();
            Core.getInstance().getConfig().getStringList("guis.icon-selection.item-lore").forEach(lines -> lore.add(ChatColor.translateAlternateColorCodes('&', lines)));
            meta.setLore(lore);
            stack.setItemMeta(meta);

            Core.getInstance().getVaultIcons().add(stack);
        });
    }

    /**
     * Used to create a global item
     *
     * @param path to the global item inside the config
     * @return an item stack of the global item
     */
    public ItemStack createGlobalItem(String path) {
        ItemStack stack = XMaterial.matchXMaterial(Core.getInstance().getConfig().getString(path + ".item")).get().parseItem();
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString(path + ".name")));
        List<String> lore = new ArrayList<>();
        Core.getInstance().getConfig().getStringList(path + ".lore").forEach(lines -> lore.add(ChatColor.translateAlternateColorCodes('&', lines)));
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    /**
     * Used to get the vault item icon
     *
     * @param p is the player
     * @param page is the vault page
     * @return the page icon
     */
    public ItemStack vaultItem(Player p, int page) {
        ItemStack stack = XMaterial.matchXMaterial((Core.getInstance().getDataFile().getConfig().contains("players." + p.getUniqueId().toString() + "." + page)) ? Core.getInstance().getDataFile().getConfig().getString("players." + p.getUniqueId().toString() + "." + page + ".icon") : Core.getInstance().getConfig().getString("guis.vault-selection.default-item")).get().parseItem();
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', (Core.getInstance().getDataFile().getConfig().contains("players." + p.getUniqueId().toString() + "." + page)) ? Core.getInstance().getDataFile().getConfig().getString("players." + p.getUniqueId().toString() + "." + page + ".name") : Core.getInstance().getConfig().getString("guis.vault-selection.default-item-name").replace("{vaultnumber}", String.valueOf(page))));
        List<String> lore = new ArrayList<>();
        Core.getInstance().getConfig().getStringList("guis.vault-selection.lore").forEach(line -> {
            lore.add(ChatColor.translateAlternateColorCodes('&', line.replace("{vaultnumber}", String.valueOf(page))));
        });
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    /**
     * Get the max selection size of a player
     *
     * @param p is the player you're checking
     * @return the max selection a player can have
     */
    public int getMaxSelectionMenu(Player p) {
        int size = Core.getInstance().getConfig().getInt("default-select-menu-size");
        if (p.hasPermission("CosmicVaults.selectionsize.9")) {
            size = 9;
        }
        if (p.hasPermission("CosmicVaults.selectionsize.18")) {
            size = 18;
        }
        if (p.hasPermission("CosmicVaults.selectionsize.27")) {
            size = 27;
        }
        if (p.hasPermission("CosmicVaults.selectionsize.36")) {
            size = 36;
        }
        if (p.hasPermission("CosmicVaults.selectionsize.45")) {
            size = 45;
        }
        if (p.hasPermission("CosmicVaults.selectionsize.54")) {
            size = 54;
        }
        return size;
    }

    /**
     * Used to get the max size of a player vault
     *
     * @param p is the player you're checking
     * @return the max size a player can have
     */
    public int getMaxSize(Player p) {
        int size = Core.getInstance().getConfig().getInt("default-vault-size");
        if (p.hasPermission("CosmicVaults.size.9")) {
            size = 9;
        }
        if (p.hasPermission("CosmicVaults.size.18")) {
            size = 18;
        }
        if (p.hasPermission("CosmicVaults.size.27")) {
            size = 27;
        }
        if (p.hasPermission("CosmicVaults.size.36")) {
            size = 36;
        }
        if (p.hasPermission("CosmicVaults.size.45")) {
            size = 45;
        }
        if (p.hasPermission("CosmicVaults.size.54")) {
            size = 54;
        }
        return size;
    }

    /**
     * Check if the args is a number
     *
     * @param number is the string you're checking
     * @return if the string is a number
     */
    public boolean isInt(String number) {
        try {
            Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean canUseVault(Player p, int page) {
        if (p.isOp()) return true;
        if (p.hasPermission("CosmicVaults.amount." + page)) return true;
        for (int x = page; x <= 54; x++) {
            if (p.hasPermission("CosmicVaults.amount." + page)) return true;
        }
        return false;
    }
}
