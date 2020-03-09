package com.kiranhart.cosmicvaults.commands;
/*
    Created by Kiran Hart
    Date: March / 09 / 2020
    Time: 11:28 a.m.
*/

import org.bukkit.command.CommandSender;

public abstract class Subcommand {

    public Subcommand() {}

    public abstract void onCommand(CommandSender sender, String[] args);

    public abstract String name();

    public abstract String[] aliases();
}
