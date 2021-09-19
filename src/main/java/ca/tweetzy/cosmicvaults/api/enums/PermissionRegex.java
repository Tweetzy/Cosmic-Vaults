package ca.tweetzy.cosmicvaults.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.regex.Pattern;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 15 2021
 * Time Created: 12:10 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@AllArgsConstructor
public enum PermissionRegex {

	MAX_ALLOWED_VAULTS(Pattern.compile("cosmicvaults\\.maxallowedvaults\\.(\\d+)")),
	MAX_VAULT_SELECTION_SIZE(Pattern.compile("cosmicvaults\\.maxvaultselectionsize\\.(\\d+)")),
	MAX_VAULT_SIZE(Pattern.compile("cosmicvaults\\.maxvaultsize\\.(\\d+)"))

	,;

	@Getter
	final Pattern pattern;
}
