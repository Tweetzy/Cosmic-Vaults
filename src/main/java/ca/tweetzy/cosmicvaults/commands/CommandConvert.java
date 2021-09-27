package ca.tweetzy.cosmicvaults.commands;

import ca.tweetzy.cosmicvaults.CosmicVaults;
import ca.tweetzy.cosmicvaults.api.CosmicVaultsAPI;
import ca.tweetzy.cosmicvaults.impl.Vault;
import ca.tweetzy.cosmicvaults.model.Permissions;
import ca.tweetzy.tweety.Common;
import ca.tweetzy.tweety.FileUtil;
import ca.tweetzy.tweety.collection.StrictMap;
import ca.tweetzy.tweety.remain.CompMaterial;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.UUID;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 27 2021
 * Time Created: 3:18 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class CommandConvert extends AbstractVaultSubCommand {

	public CommandConvert() {
		super("convert");
		setDescription("Used to convert 2.0 vault data into the 3.0.0 format");
		setPermission(Permissions.Command.CONVERT);
	}

	@Override
	protected void onCommand() {
		if (args.length == 0) {
			tell("&cThis is not a guaranteed solution, things might break or data may not be converted correctly. If you understand this, please run &4/pv convert confirm");
			return;
		}

		if (args.length != 1 && !args[0].equalsIgnoreCase("confirm")) return;

		final File oldDataFile = FileUtil.getFile("Data.yml");
		if (!oldDataFile.exists()) {
			tell("&cCould not find the old Data.yml");
			return;
		}

		final YamlConfiguration yamlConfig = YamlConfiguration.loadConfiguration(oldDataFile);
		final ConfigurationSection configurationSection = yamlConfig.getConfigurationSection("players");

		if (configurationSection == null || configurationSection.getKeys(false).size() == 0) {
			tell("&cOld Data.yml file does not contain any vault data");
			return;
		}

		for (String playerUUID : configurationSection.getKeys(false)) {

			final ConfigurationSection playerVaultsConfigurationSection = configurationSection.getConfigurationSection(playerUUID);
			if (playerVaultsConfigurationSection == null || playerVaultsConfigurationSection.getKeys(false).size() == 0) continue;

			for (String vaultNumber : playerVaultsConfigurationSection.getKeys(false)) {

				final String vaultName = playerVaultsConfigurationSection.getString("name");
				final CompMaterial vaultIcon = CompMaterial.fromString(playerVaultsConfigurationSection.getString("icon", "EMERALD"));

				final Vault vault = new Vault(
						UUID.randomUUID(),
						UUID.fromString(playerUUID),
						Integer.parseInt(vaultNumber),
						6,
						vaultName,
						"&7Default Vault Description",
						vaultIcon.getMaterial(),
						new StrictMap<>(),
						false,
						System.currentTimeMillis()
				);

				final StrictMap<Integer, ItemStack> contents = new StrictMap<>();

				for (int i = 0; i < 9 * vault.getRows(); i++) {
					if (yamlConfig.contains("players." + playerUUID + "." + vaultNumber + ".contents." + i)) {
						contents.put(i, yamlConfig.getItemStack("players." + playerUUID + "." + vaultNumber + ".contents." + i));
					}
				}

				vault.setContents(contents);
				CosmicVaultsAPI.addVault(vault);
			}
		}

		Common.runAsync(() -> {
			tell("&aConverted " + CosmicVaultsAPI.getAllVaults().size() + " vaults");
			CosmicVaults.getVaultManager().saveVaults();
		});
	}
}
