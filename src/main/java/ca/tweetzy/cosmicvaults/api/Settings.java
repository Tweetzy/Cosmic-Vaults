package ca.tweetzy.cosmicvaults.api;

import ca.tweetzy.core.configuration.Config;
import ca.tweetzy.core.configuration.ConfigSetting;
import ca.tweetzy.cosmicvaults.CosmicVaults;

import java.util.Arrays;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 7/14/2020
 * Time Created: 4:33 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
@SuppressWarnings("all")
public class Settings {

    static final Config config = CosmicVaults.getInstance().getCoreConfig();

    public static final ConfigSetting DEBUGGER = new ConfigSetting(config, "debugger", true, "Use the debugger?");
    public static final ConfigSetting LANG = new ConfigSetting(config, "lang", "en_US", "Default language file");

    public static final ConfigSetting DEFAULT_VAULT_SIZE = new ConfigSetting(config, "default vault size", 54, "Max is 54, Min is 9. Has to be a multiple of 9");
    public static final ConfigSetting DEFAULT_SELECT_MENU_SIZE = new ConfigSetting(config, "default select menu size", 54, "Max is 54, Min is 9. Has to be a multiple of 9");
    public static final ConfigSetting DISABLE_ICON_SELECTION = new ConfigSetting(config, "disable icon selection", false, "Should icon selection be disabled?");
    public static final ConfigSetting DISABLE_VAULT_SELECTION_ON_CLOSE = new ConfigSetting(config, "disable vault selection on close", true, "Should icon selection be disabled on close?");

    public static final ConfigSetting BLOCKED_ITEMS = new ConfigSetting(config, "blocked vault icons", Arrays.asList("BEDROCK", "BARRIER"), "A list of item Material names that should be blocked");

    public static final ConfigSetting GLOBAL_NEXT_PAGE_ITEM = new ConfigSetting(config, "global items.next page.item", "ARROW");
    public static final ConfigSetting GLOBAL_NEXT_PAGE_NAME = new ConfigSetting(config, "global items.next page.name", "&eNext >>");
    public static final ConfigSetting GLOBAL_NEXT_PAGE_LORE = new ConfigSetting(config, "global items.next page.lore", Arrays.asList("&7Click to go to next page"));

    public static final ConfigSetting GLOBAL_PREV_PAGE_ITEM = new ConfigSetting(config, "global items.prev page.item", "ARROW");
    public static final ConfigSetting GLOBAL_PREV_PAGE_NAME = new ConfigSetting(config, "global items.prev page.name", "&e<< Back");
    public static final ConfigSetting GLOBAL_PREV_PAGE_LORE = new ConfigSetting(config, "global items.prev page.lore", Arrays.asList("&7Click to go back a page"));

    public static final ConfigSetting GLOBAL_LOCKED_ITEM_ITEM = new ConfigSetting(config, "global items.prev page.item", "RED_STAINED_GLASS_PANE");
    public static final ConfigSetting GLOBAL_LOCKED_ITEM_NAME = new ConfigSetting(config, "global items.prev page.name", "&c&lLocked Vault");
    public static final ConfigSetting GLOBAL_LOCKED_ITEM_LORE = new ConfigSetting(config, "global items.prev page.lore", Arrays.asList("&7You don't have access to open", "&7this vault currently."));

    public static final ConfigSetting GUI_VAULT_SELECTION_TITLE = new ConfigSetting(config, "guis.vault selection.title", "&7Player Vaults");
    public static final ConfigSetting GUI_VAULT_SELECTION_DEFAULT_ITEM_NAME = new ConfigSetting(config, "guis.vault selection.default item name", "&b&lPV #{vaultnumber}");
    public static final ConfigSetting GUI_VAULT_SELECTION_DEFAULT_ITEM = new ConfigSetting(config, "guis.vault selection.default item", "EMERALD");
    public static final ConfigSetting GUI_VAULT_SELECTION_DEFAULT_ITEM_LORE = new ConfigSetting(config, "guis.vault selection.default item lore", Arrays.asList("&d/pv {vaultnumber}", "", "&7Left-Click to enter Vault", "&7Middle-Click to edit name", "&7Right-Click to edit icon"));

    public static final ConfigSetting GUI_PLAYER_VAULT_TITLE = new ConfigSetting(config, "guis.player vault.title", "&8Vault: &e{vault_number}");
    public static final ConfigSetting GUI_PLAYER_VAULT_BORDERED = new ConfigSetting(config, "guis.player vault.bordered", false);
    public static final ConfigSetting GUI_PLAYER_VAULT_BORDER_ITEM = new ConfigSetting(config, "guis.player vault.border item", "LIME_STAINED_GLASS_PANE");

    public static final ConfigSetting GUI_ICON_SELECTION_TITLE = new ConfigSetting(config, "guis.icon selection.title", "&7Select Vault Icon");
    public static final ConfigSetting GUI_ICON_SELECTION_ITEM_NAME = new ConfigSetting(config, "guis.icon selection.item name", "&b{material_name}");
    public static final ConfigSetting GUI_ICON_SELECTION_ITEM_LORE = new ConfigSetting(config, "guis.icon selection.item lore", "&7Click to select material.");
    public static final ConfigSetting GUI_ICON_SELECTION_ITEMS = new ConfigSetting(config, "guis.icon selection.items", Arrays.asList(
            "IRON_HELMET",
            "GOLD_HELMET",
            "DIAMOND_HELMET",
            "IRON_CHESTPLATE",
            "GOLD_CHESTPLATE",
            "DIAMOND_CHESTPLATE",
            "IRON_LEGGINGS",
            "GOLD_LEGGINGS",
            "DIAMOND_LEGGINGS",
            "IRON_BOOTS",
            "GOLD_BOOTS",
            "DIAMOND_BOOTS",
            "IRON_SWORD",
            "GOLD_SWORD",
            "DIAMOND_SWORD",
            "APPLE",
            "CAKE",
            "CARROT",
            "IRON_INGOT",
            "GOLD_INGOT",
            "DIAMOND",
            "EGG",
            "BOOK",
            "REDSTONE",
            "REDSTONE_BLOCK",
            "REPEATER",
            "SADDLE",
            "SAND",
            "TNT",
            "TORCH",
            "PACKED_ICE",
            "DISPENSER",
            "ICE",
            "OBSIDIAN",
            "SPONGE",
            "BEDROCK",
            "ENCHANTMENT_TABLE",
            "EMERALD",
            "BUCKET",
            "WATER_BUCKET",
            "LAVA_BUCKET",
            "GOLDEN_APPLE",
            "EXP_BOTTLE",
            "BED"
    ));

    public static void setup() {
        config.load();
        config.setAutoremove(true).setAutosave(true);
        config.saveChanges();
    }
}
