package ca.tweetzy.cosmicvaults.guis;

import ca.tweetzy.core.compatibility.XMaterial;
import ca.tweetzy.core.compatibility.XSound;
import ca.tweetzy.core.gui.Gui;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.cosmicvaults.CosmicVaults;
import ca.tweetzy.cosmicvaults.api.CosmicVaultAPI;
import ca.tweetzy.cosmicvaults.api.Settings;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/15/2020
 * Time Created: 3:13 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class IconSelectionGUI extends Gui {

    final List<ItemStack> icons;

    public IconSelectionGUI() {
        this.icons = CosmicVaults.getInstance().getVaultIcons();
        setTitle(TextUtils.formatText(Settings.GUI_ICON_SELECTION_TITLE.getString()));
        setAcceptsItems(false);

        if (this.icons.size() <= 9) setRows(1);
        if (this.icons.size() >= 10 && this.icons.size() <= 18) setRows(2);
        if (this.icons.size() >= 19 && this.icons.size() <= 27) setRows(3);
        if (this.icons.size() >= 28 && this.icons.size() <= 36) setRows(4);
        if (this.icons.size() >= 37 && this.icons.size() <= 45) setRows(5);
        if (this.icons.size() >= 54) setRows(6);

        setOnClose(close -> {
            close.manager.showGUI(close.player, new VaultSelectionGUI(close.player));
        });

        setOnOpen(open -> open.player.playSound(open.player.getLocation(), XSound.matchXSound(Settings.VAULT_OPEN_SOUND.getString()).get().parseSound(), 1.0F, 1.0F));

        draw();
    }

    private void draw() {
        reset();

        pages = (int) Math.max(1, Math.ceil(this.icons.size() / (double) 45));
        setPrevPage(5, 3, CosmicVaultAPI.get()._prevPage());
        setNextPage(5, 5, CosmicVaultAPI.get()._prevPage());

        int slot = 0;
        List<ItemStack> data = this.icons.stream().skip((page - 1) * 45L).limit(45).collect(Collectors.toList());
        for (ItemStack item : data) {
            setButton(slot++, item, e -> {
                int vault = CosmicVaults.getInstance().getVaultEdit().get(e.player.getUniqueId());
                String name = CosmicVaults.getInstance().getData().getString("players." + e.player.getUniqueId().toString() + "." + vault + ".name");
                CosmicVaults.getInstance().getData().set("players." + e.player.getUniqueId().toString() + "." + vault + ".icon", item.getType() == Material.CARROT ? "CARROT" : Objects.requireNonNull(XMaterial.matchXMaterial(item).parseMaterial()).name());
                CosmicVaults.getInstance().getData().set("players." + e.player.getUniqueId().toString() + "." + vault + ".name", name);
                CosmicVaults.getInstance().getData().save();
                CosmicVaults.getInstance().getVaultEdit().remove(e.player.getUniqueId());
                e.gui.close();
                CosmicVaults.getInstance().getLocale().getMessage("iconchanged").processPlaceholder("vault_number", vault).processPlaceholder("item", StringUtils.capitalize(XMaterial.matchXMaterial(item).parseMaterial().name().toLowerCase().replace("_", " "))).sendPrefixedMessage(e.player);
            });
        }
    }
}
