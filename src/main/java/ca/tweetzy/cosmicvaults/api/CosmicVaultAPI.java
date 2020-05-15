package ca.tweetzy.cosmicvaults.api;

import ca.tweetzy.core.compatibility.XMaterial;
import ca.tweetzy.core.utils.NumberUtils;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.cosmicvaults.CosmicVaults;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.PermissionAttachmentInfo;

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

    private CosmicVaultAPI(){}

    public static CosmicVaultAPI get() {
        if (instance == null) {
            instance = new CosmicVaultAPI();
        }
        return instance;
    }

    public void loadVaultIcons() {
        CosmicVaults.getInstance().getConfig().getStringList("guis.icon-selection.items").forEach(item -> {
            ItemStack stack = XMaterial.matchXMaterial(item.toUpperCase()).get().parseItem();
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName(TextUtils.formatText( CosmicVaults.getInstance().getConfig().getString("guis.icon-selection.item-name").replace("{material_name}", StringUtils.capitalize(stack.getType().name().toLowerCase().replace("_", " ")))));
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            List<String> lore = new ArrayList<>();
            CosmicVaults.getInstance().getConfig().getStringList("guis.icon-selection.item-lore").forEach(lines -> lore.add(TextUtils.formatText(lines)));
            meta.setLore(lore);
            stack.setItemMeta(meta);
            CosmicVaults.getInstance().getVaultIcons().add(stack);
        });
    }

    /**
     * Used to get the vault item icon
     *
     * @param p is the player
     * @param page is the vault page
     * @return the page icon
     */
    public ItemStack vaultItem(Player p, int page) {
        ItemStack stack = XMaterial.matchXMaterial((CosmicVaults.getInstance().getDataFile().getConfig().contains("players." + p.getUniqueId().toString() + "." + page)) ? CosmicVaults.getInstance().getDataFile().getConfig().getString("players." + p.getUniqueId().toString() + "." + page + ".icon") : CosmicVaults.getInstance().getConfig().getString("guis.vault-selection.default-item")).get().parseItem();
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(TextUtils.formatText((CosmicVaults.getInstance().getDataFile().getConfig().contains("players." + p.getUniqueId().toString() + "." + page)) ? CosmicVaults.getInstance().getDataFile().getConfig().getString("players." + p.getUniqueId().toString() + "." + page + ".name") : CosmicVaults.getInstance().getConfig().getString("guis.vault-selection.default-item-name").replace("{vaultnumber}", String.valueOf(page))));
        List<String> lore = new ArrayList<>();
        CosmicVaults.getInstance().getConfig().getStringList("guis.vault-selection.lore").forEach(line -> {
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
        int size = CosmicVaults.getInstance().getConfig().getInt("default-select-menu-size");
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
        int size = CosmicVaults.getInstance().getConfig().getInt("default-vault-size");
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
        int max = 0;
        if (p.isOp()) return true;
        for (PermissionAttachmentInfo effectivePermission : p.getEffectivePermissions()) {
            if (!effectivePermission.getPermission().startsWith("CosmicVaults.amount.")) continue;

            String node[] = effectivePermission.getPermission().split("\\.");

            if (NumberUtils.isInt(node[node.length - 1])) {
                int num = Integer.parseInt(node[node.length - 1]);
                if (num > max) max = num;
            }
        }
        return (page <= max);
    }
}
