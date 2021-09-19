package ca.tweetzy.cosmicvaults.api.interfaces;

import ca.tweetzy.tweety.collection.StrictList;
import ca.tweetzy.tweety.collection.StrictMap;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 14 2021
 * Time Created: 11:46 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public interface IVault {

	/**
	 * The {@link UUID} of this particular vault
	 *
	 * @return the vault's {@link UUID}
	 */
	UUID getUUID();

	/**
	 * Get the owner's {@link UUID}
	 *
	 * @return the vault owner's {@link UUID}
	 */
	UUID getOwner();

	/**
	 * Get the number of the vault (ex. pv 1, pv 2)
	 *
	 * @return the vault number
	 */
	int getNumber();

	/**
	 * Get the total number of rows the vault should have
	 *
	 * @return the total number of rows that vault has
	 */
	int getRows();

	/**
	 * Get the name of the vault
	 *
	 * @return the name of vault
	 */
	String getName();

	/**
	 * Get the description of this vault
	 *
	 * @return the description of this particular vault
	 */
	String getDescription();

	/**
	 * Get the material that will be used as the vault icon
	 *
	 * @return the {@link Material} that will be used as the vault icon
	 */
	Material getIcon();

	/**
	 * Get all the items stored within the vault
	 *
	 * @return the {@link ItemStack} based on the given {@link Integer} slot
	 */
	StrictMap<Integer, ItemStack> getContents();

	/**
	 * Is the vault currently opened by a player?
	 *
	 * @return true if the vault is opened by a player
	 */
	boolean isOpen();

	/**
	 * @return the creation date of the vault
	 */
	long getCreationDate();

	/**
	 * Set the amount of rows the vault should have
	 *
	 * @param rows is the amount of rows the vault should have
	 */
	void setRows(final int rows);

	/**
	 * Set the name of the vault
	 *
	 * @param name the new name of the vault
	 */
	void setName(@NonNull final String name);

	/**
	 * Set the description of the vault
	 *
	 * @param description the new description of the vault
	 */
	void setDescription(@NonNull final String description);

	/**
	 * Set the {@link Material} used for the vault icon
	 *
	 * @param material the new {@link Material} for the vault icon
	 */
	void setIcon(@NonNull final Material material);

	/**
	 * Set the contents of the vault
	 *
	 * @param contents new contents of the vault
	 */
	void setContents(@NonNull final StrictMap<Integer, ItemStack> contents);

	/**
	 * Set whether a vault is opened by a player
	 *
	 * @param open is whether the vault is opened
	 */
	void setOpen(final boolean open);
}
