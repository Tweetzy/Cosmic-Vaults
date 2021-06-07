package ca.tweetzy.cosmicvaults.api;

import ca.tweetzy.core.compatibility.XMaterial;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.cosmicvaults.CosmicVaults;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/15/2020
 * Time Created: 2:46 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class CosmicVaultAPI {

    private static CosmicVaultAPI instance;

    private CosmicVaultAPI() {
    }

    public static CosmicVaultAPI get() {
        if (instance == null) {
            instance = new CosmicVaultAPI();
        }
        return instance;
    }

    public void loadVaultIcons() {
        Settings.GUI_ICON_SELECTION_ITEMS.getStringList().forEach(item -> {
            ItemStack stack = item.contains("CARROT") ? new ItemStack(Material.CARROT, 1) : XMaterial.matchXMaterial(item.toUpperCase()).orElse(XMaterial.PAPER).parseItem();
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName(TextUtils.formatText(Settings.GUI_ICON_SELECTION_ITEM_NAME.getString().replace("{material_name}", StringUtils.capitalize(stack.getType().name().toLowerCase().replace("_", " ")))));
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            List<String> lore = new ArrayList<>();
            Settings.GUI_ICON_SELECTION_ITEM_LORE.getStringList().forEach(lines -> lore.add(TextUtils.formatText(lines)));
            meta.setLore(lore);
            stack.setItemMeta(meta);
            CosmicVaults.getInstance().getVaultIcons().add(stack);
        });
    }

    public ItemStack _nextPage() {
        ItemStack stack = XMaterial.matchXMaterial(Settings.GLOBAL_NEXT_PAGE_ITEM.getString()).get().parseItem();
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(TextUtils.formatText(Settings.GLOBAL_NEXT_PAGE_NAME.getString()));
        List<String> lore = new ArrayList<>();
        Settings.GLOBAL_NEXT_PAGE_LORE.getStringList().forEach(s -> lore.add(TextUtils.formatText(s)));
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    public ItemStack _prevPage() {
        ItemStack stack = XMaterial.matchXMaterial(Settings.GLOBAL_PREV_PAGE_ITEM.getString()).get().parseItem();
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(TextUtils.formatText(Settings.GLOBAL_PREV_PAGE_NAME.getString()));
        List<String> lore = new ArrayList<>();
        Settings.GLOBAL_PREV_PAGE_LORE.getStringList().forEach(s -> lore.add(TextUtils.formatText(s)));
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    public ItemStack _locked() {
        ItemStack stack = XMaterial.matchXMaterial(Settings.GLOBAL_LOCKED_ITEM_ITEM.getString()).get().parseItem();
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(TextUtils.formatText(Settings.GLOBAL_LOCKED_ITEM_NAME.getString()));
        List<String> lore = new ArrayList<>();
        Settings.GLOBAL_LOCKED_ITEM_LORE.getStringList().forEach(s -> lore.add(TextUtils.formatText(s)));
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    public void saveCache() {
        CosmicVaults.getInstance().getCacheManager().getCachedPlayers().forEach(playerCache -> {
            CosmicVaults.getInstance().getData().set("player cache." + playerCache.getUuid().toString() + ".uuid", playerCache.getUuid().toString());
            CosmicVaults.getInstance().getData().set("player cache." + playerCache.getUuid().toString() + ".name", playerCache.getName());
            CosmicVaults.getInstance().getData().set("player cache." + playerCache.getUuid().toString() + ".max vault size", playerCache.getMaxVaultSize());
            CosmicVaults.getInstance().getData().set("player cache." + playerCache.getUuid().toString() + ".max selection size", playerCache.getMaxSelectionSize());
        });
        CosmicVaults.getInstance().getData().save();
    }

    /**
     * Used to get the vault item icon
     *
     * @param p    is the player
     * @param page is the vault page
     * @return the page icon
     */
    public ItemStack vaultItem(Player p, int page) {
        ItemStack stack = XMaterial.matchXMaterial(CosmicVaults.getInstance().getData().contains("players." + p.getUniqueId().toString() + "." + page) ? CosmicVaults.getInstance().getData().getString("players." + p.getUniqueId().toString() + "." + page + ".icon") : Settings.GUI_VAULT_SELECTION_DEFAULT_ITEM.getString()).orElse(XMaterial.PAPER).parseItem();
        if (stack.getType() == Material.CARROTS || stack.getType() == Material.CARROT) stack = new ItemStack(Material.CARROT, 1);

        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(TextUtils.formatText((CosmicVaults.getInstance().getData().contains("players." + p.getUniqueId().toString() + "." + page)) ? CosmicVaults.getInstance().getData().getString("players." + p.getUniqueId().toString() + "." + page + ".name") : Settings.GUI_VAULT_SELECTION_DEFAULT_ITEM_NAME.getString().replace("{vaultnumber}", String.valueOf(page))));
        List<String> lore = new ArrayList<>();
        Settings.GUI_VAULT_SELECTION_DEFAULT_ITEM_LORE.getStringList().forEach(line -> {
            lore.add(TextUtils.formatText(line.replace("{vaultnumber}", String.valueOf(page))));
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
        int size = Settings.DEFAULT_SELECT_MENU_SIZE.getInt();
        if (p.hasPermission("cosmicvaults.selectionsize.9")) {
            size = 9;
        }
        if (p.hasPermission("cosmicvaults.selectionsize.18")) {
            size = 18;
        }
        if (p.hasPermission("cosmicvaults.selectionsize.27")) {
            size = 27;
        }
        if (p.hasPermission("cosmicvaults.selectionsize.36")) {
            size = 36;
        }
        if (p.hasPermission("cosmicvaults.selectionsize.45")) {
            size = 45;
        }
        if (p.hasPermission("cosmicvaults.selectionsize.54")) {
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
        int size = Settings.DEFAULT_VAULT_SIZE.getInt();
        if (p.hasPermission("cosmicvaults.size.9")) {
            size = 9;
        }
        if (p.hasPermission("cosmicvaults.size.18")) {
            size = 18;
        }
        if (p.hasPermission("cosmicvaults.size.27")) {
            size = 27;
        }
        if (p.hasPermission("cosmicvaults.size.36")) {
            size = 36;
        }
        if (p.hasPermission("cosmicvaults.size.45")) {
            size = 45;
        }
        if (p.hasPermission("cosmicvaults.size.54")) {
            size = 54;
        }
        return size;
    }

    public boolean canUseVault(Player p, int page) {
        if (p.hasPermission("cosmicvaults.amount." + page)) {
            return true;
        }
        for (int x = page; x <= 99; x++) {
            if (p.hasPermission("cosmicvaults.amount." + x)) {
                return true;
            }
        }
        return false;
    }
}
