package ca.tweetzy.cosmicvaults.guis;

import ca.tweetzy.core.compatibility.XMaterial;
import ca.tweetzy.core.inventory.TInventory;
import ca.tweetzy.core.utils.items.ItemUtils;
import ca.tweetzy.cosmicvaults.CosmicVaults;
import ca.tweetzy.cosmicvaults.api.CosmicVaultAPI;
import ca.tweetzy.cosmicvaults.api.Settings;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/15/2020
 * Time Created: 3:13 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class IconSelectionGUI extends TInventory {

    private List<List<ItemStack>> chunks;

    public IconSelectionGUI() {
        setPage(1);
        setTitle(Settings.GUI_ICON_SELECTION_TITLE.getString());

        chunks = Lists.partition(CosmicVaults.getInstance().getVaultIcons(), 45);

        if (chunks.get(getPage() - 1).size() <= 9) setRows(1);
        if (chunks.get(getPage() - 1).size() >= 10 && chunks.get(getPage() - 1).size() <= 18) setRows(2);
        if (chunks.get(getPage() - 1).size() >= 19 && chunks.get(getPage() - 1).size() <= 27) setRows(3);
        if (chunks.get(getPage() - 1).size() >= 28 && chunks.get(getPage() - 1).size() <= 36) setRows(4);
        if (chunks.get(getPage() - 1).size() >= 37 && chunks.get(getPage() - 1).size() <= 45) setRows(5);
        if (chunks.get(getPage() - 1).size() >= 54) setRows(6);
    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, getSize(), getTitle());
        chunks.get(this.page - 1).forEach(item -> inventory.setItem(inventory.firstEmpty(), item));

        if (chunks.get(this.page - 1).size() >= 54) {
            inventory.setItem(48, CosmicVaultAPI.get()._prevPage());
            inventory.setItem(50, CosmicVaultAPI.get()._nextPage());
        }

        return inventory;
    }

    @Override
    public void onClick(InventoryClickEvent e, int slot) {
        e.setCancelled(true);
        Player p = (Player) e.getWhoClicked();

        ItemStack is = e.getCurrentItem();
        int vault = CosmicVaults.getInstance().getVaultedit().get(p.getUniqueId());
        String name = CosmicVaults.getInstance().getDataFile().getString("players." + p.getUniqueId().toString() + "." + vault + ".name");

        CosmicVaults.getInstance().getDataFile().set("players." + p.getUniqueId().toString() + "." + vault + ".icon", XMaterial.matchXMaterial(is).parseMaterial().name());
        CosmicVaults.getInstance().getDataFile().set("players." + p.getUniqueId().toString() + "." + vault + ".name", name);

        CosmicVaults.getInstance().getDataFile().save();
        CosmicVaults.getInstance().getVaultedit().remove(p.getUniqueId());
        p.openInventory(new VaultSelectionGUI(p).getInventory());

        CosmicVaults.getInstance().getLocale().getMessage("iconchanged").processPlaceholder("vault_number", vault).processPlaceholder("item", StringUtils.capitalize(XMaterial.matchXMaterial(is).parseMaterial().name().toLowerCase().replace("_", " "))).sendPrefixedMessage(p);
    }

    @Override
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        Bukkit.getServer().getScheduler().runTaskLater(CosmicVaults.getInstance(), () -> p.openInventory(new VaultSelectionGUI(p).getInventory()), 1L);
    }
}
