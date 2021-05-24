package ca.tweetzy.cosmicvaults.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * The current file has been created by Kiran Hart
 * Date Created: May 21 2021
 * Time Created: 9:59 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */

@Getter
@Setter
@AllArgsConstructor
public class PlayerCache {

    private UUID uuid;
    private String name;
    private int maxVaultSize;
    private int maxSelectionSize;
}
