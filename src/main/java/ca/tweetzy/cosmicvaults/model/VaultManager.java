package ca.tweetzy.cosmicvaults.model;

import ca.tweetzy.cosmicvaults.CosmicVaults;
import ca.tweetzy.cosmicvaults.api.enums.CosmicVaultsConstants;
import ca.tweetzy.cosmicvaults.api.events.VaultDeleteEvent;
import ca.tweetzy.cosmicvaults.impl.Vault;
import ca.tweetzy.cosmicvaults.settings.Settings;
import ca.tweetzy.tweety.Common;
import ca.tweetzy.tweety.Valid;
import ca.tweetzy.tweety.collection.StrictList;
import ca.tweetzy.tweety.collection.StrictMap;
import ca.tweetzy.tweety.collection.StrictSet;
import ca.tweetzy.tweety.menu.model.ItemCreator;
import ca.tweetzy.tweety.model.Replacer;
import ca.tweetzy.tweety.remain.CompMaterial;
import ca.tweetzy.tweety.settings.SimpleSettings;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 15 2021
 * Time Created: 2:34 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class VaultManager {

	/**
	 * Holds all the known vaults created / owned by online & offline players
	 */
	@Getter
	private final StrictList<Vault> vaults = new StrictList<>();

	/**
	 * Only contains vaults that have been changed / edited since the last file save
	 */
	@Getter
	private final StrictSet<UUID> editedVaults = new StrictSet<>();

	public void addVault(@NonNull final Vault vault) {
		this.vaults.add(vault);
	}

	public void removeVault(@NonNull final Vault vault) {
		this.vaults.remove(vault);
	}

	public void addEditedVault(@NonNull final UUID uuid) {
		if (!this.editedVaults.contains(uuid)) {
			this.editedVaults.add(uuid);
		}
	}

	public void removeEditedVault(@NonNull final UUID uuid) {
		if (this.editedVaults.contains(uuid)) {
			this.editedVaults.remove(uuid);
		}
	}

	public void resetVault(@NonNull final UUID owner, final int number) {
		final Vault vault = this.getVault(owner, number);
		Valid.checkNotNull(vault, "Tried to clear contents of a vault that does not exists (owner=" + owner.toString() + " number=" + number + ")");
		vault.setName("&7Vault #&d" + number);
		vault.setDescription("&7Default description");
		vault.setIcon(CompMaterial.EMERALD.getMaterial());
		vault.setContents(new StrictMap<>());
		this.addEditedVault(vault.getUUID());
	}

	public void deleteVault(@NonNull final UUID owner, final int number) {
		final Vault vault = this.getVault(owner, number);
		Valid.checkNotNull(vault, "Tried to deleted vault that does not exists (owner=" + owner.toString() + " number=" + number + ")");
		if (!Common.callEvent(new VaultDeleteEvent(vault))) return;

		Common.runAsync(() -> CosmicVaults.getInstance().getDataFile().setField(CosmicVaultsConstants.VAULT_PARENT_NODE.getPath() + "." + vault.getUUID().toString(), null));
		this.removeEditedVault(vault.getUUID());
		this.removeVault(vault);
	}

	public Vault getVault(@NonNull final UUID owner, final int number) {
		return this.vaults.getSource().stream().filter(vault -> vault.getOwner().equals(owner) && vault.getNumber() == number).findFirst().orElse(null);
	}

	public StrictMap<Integer, Vault> getVaultsByPlayer(@NonNull final UUID owner) {
		StrictMap<Integer, Vault> owned = new StrictMap<>();
		this.vaults.getSource().stream().filter(vault -> vault.getOwner().equals(owner)).collect(Collectors.toList()).forEach(vault -> owned.put(vault.getNumber(), vault));
		return owned;
	}

	public void saveVault(@NonNull final Vault vault) {
		if (Settings.ONLY_SAVE_VAULTS_WITH_CHANGES && !this.editedVaults.contains(vault.getUUID())) return;

		final String basePath = CosmicVaultsConstants.VAULT_PARENT_NODE.getPath() + "." + vault.getUUID().toString();
		CosmicVaults.getInstance().getDataFile().setField(basePath + ".owner", vault.getOwner().toString());
		CosmicVaults.getInstance().getDataFile().setField(basePath + ".number", vault.getNumber());
		CosmicVaults.getInstance().getDataFile().setField(basePath + ".rows", vault.getRows());
		CosmicVaults.getInstance().getDataFile().setField(basePath + ".name", vault.getName());
		CosmicVaults.getInstance().getDataFile().setField(basePath + ".description", vault.getDescription());
		CosmicVaults.getInstance().getDataFile().setField(basePath + ".icon", vault.getIcon().name());
		CosmicVaults.getInstance().getDataFile().setField(basePath + ".creationDate", vault.getCreationDate());
		CosmicVaults.getInstance().getDataFile().setField(basePath + ".contents", vault.getContents().serialize());
		removeEditedVault(vault.getUUID());
	}

	public void loadVaults() {
		Common.runAsync(() -> {
			ConfigurationSection configurationSection = CosmicVaults.getInstance().getDataFile().getConfigField("Vaults");
			if (configurationSection == null || configurationSection.getKeys(false).size() == 0) return;

			configurationSection.getKeys(false).forEach(key -> {

				final UUID vaultUUID = UUID.fromString(key);
				final UUID vaultOwner = UUID.fromString(Objects.requireNonNull(CosmicVaults.getInstance().getDataFile().getConfigField("Vaults." + key + ".owner")));
				final Integer vaultNumber = CosmicVaults.getInstance().getDataFile().getConfigField("Vaults." + key + ".number");
				final Integer vaultRows = CosmicVaults.getInstance().getDataFile().getConfigField("Vaults." + key + ".rows");
				final String vaultName = CosmicVaults.getInstance().getDataFile().getConfigField("Vaults." + key + ".name");
				final String vaultDescription = CosmicVaults.getInstance().getDataFile().getConfigField("Vaults." + key + ".description");
				final Material vaultIcon = CompMaterial.fromString(Objects.requireNonNull(CosmicVaults.getInstance().getDataFile().getConfigField("Vaults." + key + ".icon"))).getMaterial();
				final Long vaultCreationDate = CosmicVaults.getInstance().getDataFile().getConfigField("Vaults." + key + ".creationDate");
				final StrictMap<Integer, ItemStack> vaultContents = new StrictMap<>();

				ConfigurationSection vaultItems = CosmicVaults.getInstance().getDataFile().getConfigField("Vaults." + key + ".contents");
				if (vaultItems != null && vaultItems.getKeys(false).size() != 0)  {
					vaultItems.getKeys(false).forEach(slot -> {
						vaultContents.put(Integer.parseInt(slot), CosmicVaults.getInstance().getDataFile().getConfigField("Vaults." + key + ".contents." + slot));
					});
				}

				try {
					addVault(new Vault(
							vaultUUID,
							vaultOwner,
							vaultNumber,
							vaultRows,
							vaultName,
							vaultDescription,
							vaultIcon,
							vaultContents,
							false,
							vaultCreationDate
					));
				} catch (NullPointerException e) {
					Common.logFramed("Cosmic Vaults ran into an issue while trying", "to load the vault with the id: " + vaultUUID.toString());
				}
			});
		});
	}

	public void saveVaults() {
		this.vaults.forEach(this::saveVault);
	}
}