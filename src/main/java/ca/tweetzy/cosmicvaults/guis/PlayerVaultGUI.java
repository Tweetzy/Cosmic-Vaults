package ca.tweetzy.cosmicvaults.guis;

import ca.tweetzy.core.compatibility.XMaterial;
import ca.tweetzy.core.gui.Gui;
import ca.tweetzy.core.utils.PlayerUtils;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.cosmicvaults.CosmicVaults;
import ca.tweetzy.cosmicvaults.api.Settings;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.UUID;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/15/2020
 * Time Created: 3:22 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class PlayerVaultGUI extends Gui {

    UUID player;
    Player admin;
    int vault;
    boolean adminView;

    public PlayerVaultGUI(UUID player, int vault) {
        this(player, null, vault, false);
    }

    public PlayerVaultGUI(UUID player, Player admin, int vault, boolean adminView) {
        this.player = player;
        this.admin = admin;
        this.vault = vault;
        this.adminView = adminView;

        setTitle(TextUtils.formatText(Settings.GUI_PLAYER_VAULT_TITLE.getString().replace("{vault_number}", String.valueOf(vault))));
        setRows(CosmicVaults.getInstance().getCacheManager().getCachedPlayers().stream().filter(playerCache ->  playerCache.getUuid().equals(this.player)).findFirst().orElse(null).getMaxVaultSize() / 9);
        setAcceptsItems(true);
        setUnlockedRange(0, 89);

        setOnClose(close -> {
            String name = Settings.GUI_VAULT_SELECTION_DEFAULT_ITEM_NAME.getString().replace("{vaultnumber}", String.valueOf(this.vault));
            String icon = Settings.GUI_VAULT_SELECTION_DEFAULT_ITEM.getString();

            if (!this.adminView) {
                if (!CosmicVaults.getInstance().getOpenedVault().containsKey(this.player)) {
                    return;
                }

                if (!CosmicVaults.getInstance().getData().contains("players." + this.player.toString() + "." + vault)) {
                    CosmicVaults.getInstance().getData().set("players." + this.player.toString() + "." + vault + ".icon", icon);
                    CosmicVaults.getInstance().getData().set("players." + this.player.toString() + "." + vault + ".name", TextUtils.formatText(name));
                    CosmicVaults.getInstance().getData().save();
                }

                for (int i = 0; i < getRows() * 9; i++) {
                    int finalI = i;
                    Settings.BLOCKED_ITEMS.getStringList().forEach(blocked -> {
                        if (getItem(finalI) != null && getItem(finalI).getType() != XMaterial.AIR.parseMaterial()) {
                            if (getItem(finalI).getType() == XMaterial.matchXMaterial(blocked).get().parseMaterial()) {
                                PlayerUtils.giveItem(close.player, getItem(finalI));
                                setItem(finalI, XMaterial.AIR.parseItem());
                            }
                        }

                        CosmicVaults.getInstance().getData().set("players." + this.player.toString() + "." + vault + ".contents." + finalI, getItem(finalI));
                    });
                }

                CosmicVaults.getInstance().getData().save();
                CosmicVaults.getInstance().getOpenedVault().remove(this.player);

            } else {
                if (!CosmicVaults.getInstance().getAdminEdit().containsKey(this.admin.getUniqueId())) {
                    return;
                }

                UUID target = CosmicVaults.getInstance().getAdminEdit().get(this.admin.getUniqueId());

                if (!CosmicVaults.getInstance().getData().contains("players." + target.toString() + "." + vault)) {
                    CosmicVaults.getInstance().getData().set("players." + target.toString() + "." + vault + ".icon", icon);
                    CosmicVaults.getInstance().getData().set("players." + target.toString() + "." + vault + ".name", TextUtils.formatText(name));
                    CosmicVaults.getInstance().getData().save();
                }

                for (int i = 0; i < getRows() * 9; i++) {
                    int finalI = i;
                    Settings.BLOCKED_ITEMS.getStringList().forEach(blocked -> {
                        if (getItem(finalI) != null && getItem(finalI).getType() != XMaterial.AIR.parseMaterial()) {
                            if (getItem(finalI).getType() == XMaterial.matchXMaterial(blocked).get().parseMaterial()) {
                                PlayerUtils.giveItem(close.player, getItem(finalI));
                                setItem(finalI, XMaterial.AIR.parseItem());
                            }
                        }

                        CosmicVaults.getInstance().getData().set("players." + target.toString() + "." + vault + ".contents." + finalI, getItem(finalI));
                    });
                }

                CosmicVaults.getInstance().getData().save();
                CosmicVaults.getInstance().getOpenedVault().remove(target);
                CosmicVaults.getInstance().getAdminEdit().remove(this.admin.getUniqueId());
            }
        });

        draw();
    }

    private void draw() {
        if (!CosmicVaults.getInstance().getData().contains("players." + this.player.toString() + "." + this.vault)) {
            return;
        }

        if (!CosmicVaults.getInstance().getData().contains("players." + this.player.toString() + "." + this.vault + ".contents")) {
            return;
        }

        for (String keys : CosmicVaults.getInstance().getData().getConfigurationSection("players." + this.player.toString() + "." + this.vault + ".contents").getKeys(false)) {
            int slot = Integer.parseInt(keys);
            setItem(slot, CosmicVaults.getInstance().getData().getItemStack("players." + this.player.toString() + "." + this.vault + ".contents." + keys));
        }
    }
}
