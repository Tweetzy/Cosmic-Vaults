package com.kiranhart.cosmicvaults.utils;
/*
    Created by Kiran Hart
    Date: March / 09 / 2020
    Time: 11:46 a.m.
*/

import com.kiranhart.cosmicvaults.Core;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Debugger {

    public static void report(Exception e, boolean show) {
        if (Core.getInstance().getConfig().getBoolean("debugger") && show) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"));
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"));
        }
    }
}
