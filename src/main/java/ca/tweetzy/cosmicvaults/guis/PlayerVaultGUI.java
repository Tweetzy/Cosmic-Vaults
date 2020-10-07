package ca.tweetzy.cosmicvaults.guis;

import ca.tweetzy.core.compatibility.XMaterial;
import ca.tweetzy.core.inventory.TInventory;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.cosmicvaults.CosmicVaults;
import ca.tweetzy.cosmicvaults.api.CosmicVaultAPI;
import ca.tweetzy.cosmicvaults.api.Settings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/15/2020
 * Time Created: 3:22 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class PlayerVaultGUI extends TInventory {

    private Player player;
    private int vault;
    private boolean adminView;

    public PlayerVaultGUI(Player player, int vault) {
        this.player = player;
        this.vault = vault;
        this.adminView = false;

        setTitle(Settings.GUI_PLAYER_VAULT_TITLE.getString().replace("{vault_number}", String.valueOf(vault)));
        setPage(1);
        setRows(CosmicVaultAPI.get().getMaxSize(player) / 9);
    }

    public PlayerVaultGUI(Player player, int vault, boolean adminView) {
        this.player = player;
        this.vault = vault;
        this.adminView = adminView;

        setTitle("&4" + Settings.GUI_PLAYER_VAULT_TITLE.getString().replace("{vault_number}", String.valueOf(vault)));
        setPage(1);
        setRows(CosmicVaultAPI.get().getMaxSize(player) / 9);
    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, getSize(), getTitle());

        if (CosmicVaults.getInstance().getDataFile().contains("players." + this.player.getUniqueId().toString() + "." + vault)) {

            // no contents
            if (!CosmicVaults.getInstance().getDataFile().contains("players." + this.player.getUniqueId().toString() + "." + vault + ".contents")) {
                return inventory;
            }

            // contents
            for (String keys : CosmicVaults.getInstance().getDataFile().getConfigurationSection("players." + this.player.getUniqueId().toString() + "." + vault + ".contents").getKeys(false)) {
                int slot = Integer.parseInt(keys);
                inventory.setItem(slot, CosmicVaults.getInstance().getDataFile().getItemStack("players." + this.player.getUniqueId().toString() + "." + vault + ".contents." + keys));
            }

        } else {
            return inventory;
        }
        return inventory;
    }

    @Override
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();

        String name = Settings.GUI_VAULT_SELECTION_DEFAULT_ITEM_NAME.getString().replace("{vaultnumber}", String.valueOf(vault));
        String icon = Settings.GUI_VAULT_SELECTION_DEFAULT_ITEM.getString();;

        if (!adminView) {
            if (!CosmicVaults.getInstance().getOpenedVault().containsKey(p.getUniqueId())) {
                return;
            }

            if (!CosmicVaults.getInstance().getDataFile().contains("players." + p.getUniqueId().toString() + "." + vault)) {
                CosmicVaults.getInstance().getDataFile().set("players." + p.getUniqueId().toString() + "." + vault + ".icon", icon);
                CosmicVaults.getInstance().getDataFile().set("players." + p.getUniqueId().toString() + "." + vault + ".name", TextUtils.formatText(name));
                CosmicVaults.getInstance().getDataFile().save();
            }

            for (int i = 0; i < e.getInventory().getSize(); i++) {
                CosmicVaults.getInstance().getDataFile().set("players." + p.getUniqueId().toString() + "." + vault + ".contents." + i, e.getInventory().getItem(i));
            }

            CosmicVaults.getInstance().getDataFile().save();
            CosmicVaults.getInstance().getOpenedVault().remove(p.getUniqueId());
        } else {
            if (!CosmicVaults.getInstance().getAdminEdit().containsKey(p.getUniqueId())) {
                return;
            }

            Player target = Bukkit.getPlayer(CosmicVaults.getInstance().getAdminEdit().get(p.getUniqueId()));

            if (!CosmicVaults.getInstance().getDataFile().contains("players." + target.getUniqueId().toString() + "." + vault)) {
                CosmicVaults.getInstance().getDataFile().set("players." + target.getUniqueId().toString() + "." + vault + ".icon", icon);
                CosmicVaults.getInstance().getDataFile().set("players." + target.getUniqueId().toString() + "." + vault + ".name", TextUtils.formatText(name));
                CosmicVaults.getInstance().getDataFile().save();
            }

            for (int i = 0; i < e.getInventory().getSize(); i++) {
                CosmicVaults.getInstance().getDataFile().set("players." + target.getUniqueId().toString() + "." + vault + ".contents." + i, e.getInventory().getItem(i));
            }

            CosmicVaults.getInstance().getDataFile().save();
            CosmicVaults.getInstance().getOpenedVault().remove(target.getUniqueId());
            CosmicVaults.getInstance().getAdminEdit().remove(p.getUniqueId());
        }
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        Settings.BLOCKED_ITEMS.getStringList().forEach(blockedItem -> {
            if (e.getCurrentItem().getType() == XMaterial.matchXMaterial(blockedItem).get().parseMaterial()) {
                e.setCancelled(true);
            }
        });
    }
}
