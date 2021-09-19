package ca.tweetzy.cosmicvaults.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 19 2021
 * Time Created: 1:22 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@AllArgsConstructor
public enum CosmicVaultsConstants {

	VAULT_PARENT_NODE("Vaults"),


	;


	@Getter
	private final String path;
}
