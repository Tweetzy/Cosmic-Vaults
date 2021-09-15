package ca.tweetzy.cosmicvaults;

import ca.tweetzy.tweety.MinecraftVersion;
import ca.tweetzy.tweety.plugin.SimplePlugin;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 14 2021
 * Time Created: 11:39 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class CosmicVaults extends SimplePlugin {


	@Override
	protected void onPluginStart() {

	}

	public static CosmicVaults getInstance() {
		return (CosmicVaults) SimplePlugin.getInstance();
	}

	@Override
	public MinecraftVersion.V getMinimumVersion() {
		return MinecraftVersion.V.v1_8;
	}
}
