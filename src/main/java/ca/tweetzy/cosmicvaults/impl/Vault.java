package ca.tweetzy.cosmicvaults.impl;

import ca.tweetzy.cosmicvaults.api.interfaces.IVault;
import ca.tweetzy.tweety.collection.StrictMap;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 15 2021
 * Time Created: 12:03 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@AllArgsConstructor
public final class Vault implements IVault {

	private UUID uuid;
	private UUID owner;
	private int number;
	private int rows;
	private String name;
	private String description;
	private Material icon;
	private StrictMap<Integer, ItemStack> contents;
	private boolean open;
	private final long creationDate;

	public Vault(VaultPlayer vaultPlayer, int number) {
		this(UUID.randomUUID(), vaultPlayer.getPlayer().getUniqueId(), number, vaultPlayer.getMaxVaultSize(), "&7Vault #&d" + number, "&7Default description", Material.EMERALD, new StrictMap<>(), false, System.currentTimeMillis());
	}

	@Override
	public UUID getUUID() {
		return this.uuid;
	}

	@Override
	public UUID getOwner() {
		return this.owner;
	}

	@Override
	public int getNumber() {
		return this.number;
	}

	@Override
	public int getRows() {
		return this.rows;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public Material getIcon() {
		return this.icon;
	}

	@Override
	public StrictMap<Integer, ItemStack> getContents() {
		return this.contents;
	}

	@Override
	public boolean isOpen() {
		return this.open;
	}

	@Override
	public long getCreationDate() {
		return this.creationDate;
	}

	@Override
	public void setRows(int rows) {
		this.rows = rows;
	}

	@Override
	public void setName(@NonNull String name) {
		this.name = name;
	}

	@Override
	public void setDescription(@NonNull String description) {
		this.description = description;
	}

	@Override
	public void setIcon(@NonNull Material material) {
		this.icon = material;
	}

	@Override
	public void setContents(@NonNull StrictMap<Integer, ItemStack> contents) {
		this.contents = contents;
	}

	@Override
	public void setOpen(boolean open) {
		this.open = open;
	}
}
