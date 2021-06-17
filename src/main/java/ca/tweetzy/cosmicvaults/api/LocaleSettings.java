package ca.tweetzy.cosmicvaults.api;

import ca.tweetzy.core.configuration.Config;
import ca.tweetzy.cosmicvaults.CosmicVaults;

import java.util.HashMap;

/**
 * The current file has been created by Kiran Hart
 * Date Created: June 17 2021
 * Time Created: 12:10 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class LocaleSettings {

    static final HashMap<String, String> languageNodes = new HashMap<>();

    static {
        languageNodes.put("general.prefix", "&8[&bCosmicVaults&8]");
        languageNodes.put("notanumber", "&cThat isn't a nunber, try again!");
        languageNodes.put("reloaded", "&aAll files have been reloaded!");
        languageNodes.put("vaultzero", "&cYou cannot open vaults lower than 1");
        languageNodes.put("iconchanged", "&aSet Vault &b%vault_number%&a icon to &b%item%");
        languageNodes.put("namechanged", "&aVault #&b%vault_number%&a renamed to &b%vault_name%");
        languageNodes.put("vaultopenalready", "&cThat vault is already opened by someone!");
        languageNodes.put("player-offline", "&cThe player &b%player%&c is not online currently.");
        languageNodes.put("pv.no-permission", "&cYou do not have permission to open vault: &b%vault_number%");
    }

    public static void setup() {
        Config config = CosmicVaults.getInstance().getLocale().getConfig();

        languageNodes.keySet().forEach(key -> {
            config.setDefault(key, languageNodes.get(key));
        });

        config.setAutoremove(true).setAutosave(true);
        config.saveChanges();
    }
}
