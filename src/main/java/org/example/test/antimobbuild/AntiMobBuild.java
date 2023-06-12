package org.example.test.antimobbuild;

import org.bukkit.plugin.java.JavaPlugin;
import org.example.test.antimobbuild.commands.Commands;
import org.example.test.antimobbuild.listeners.BuildListener;

import java.io.File;

public final class AntiMobBuild extends JavaPlugin {
    @Override
    public void onEnable() {
        // Load the configuration file
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveResource("config.yml", false);
        }

        // Create an instance of BuildListener and pass the config
        BuildListener buildListener = new BuildListener(getConfig());

        // Register the BuildListener
        getServer().getPluginManager().registerEvents(buildListener, this);

        // Register the commands
        getCommand("amb").setExecutor(new Commands(configFile, buildListener));

        // Print a cool message to the console
        getLogger().info("*********************************************");
        getLogger().info("*                                           *");
        getLogger().info("*            AntiMobBuild Plugin            *");
        getLogger().info("*                                           *");
        getLogger().info("*********************************************");


        // Other plugin initialization code
    }

    @Override
    public void onDisable() {
        // Print a message to the console
        getLogger().info("AntiMobBuild Plugin Disabled");

    }
}
