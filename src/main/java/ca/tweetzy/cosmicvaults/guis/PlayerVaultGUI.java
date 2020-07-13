package ca.tweetzy.cosmicvaults.guis;

import ca.tweetzy.core.compatibility.XMaterial;
import ca.tweetzy.core.inventory.TInventory;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.cosmicvaults.CosmicVaults;
import ca.tweetzy.cosmicvaults.api.CosmicVaultAPI;
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

    public PlayerVaultGUI(Player player, int vault) {
        this.player = player;
        this.vault = vault;

        setTitle(CosmicVaults.getInstance().getConfig().getString("guis.player-vault.title").replace("{vault_number}", String.valueOf(vault)));
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

        if (!CosmicVaults.getInstance().getOpenedVault().containsKey(p.getUniqueId())) {
            return;
        }

        String name = CosmicVaults.getInstance().getConfig().getString("guis.vault-selection.default-item-name").replace("{vaultnumber}", String.valueOf(vault));
        String icon = CosmicVaults.getInstance().getConfig().getString("guis.vault-selection.default-item");

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
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        CosmicVaults.getInstance().getConfig().getStringList("blocked-vault-items").forEach(blockedItem -> {
            if (e.getCurrentItem().getType() == XMaterial.matchXMaterial(blockedItem).get().parseMaterial()) {
                e.setCancelled(true);
            }
        });
    }
}
