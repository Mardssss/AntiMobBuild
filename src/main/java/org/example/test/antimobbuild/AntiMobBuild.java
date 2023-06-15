package org.example.test.antimobbuild;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
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

        // Load the messages configuration file
        File messagesFile = new File(getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            saveResource("messages.yml", false);
        }
        FileConfiguration config = getConfig();
        FileConfiguration messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);

        // Create an instance of BuildListener and pass the config and messages
        BuildListener buildListener = new BuildListener(config, messagesConfig);
        // Register the BuildListener
        getServer().getPluginManager().registerEvents(buildListener, this);

        // Register the commands
        getCommand("amb").setExecutor(new Commands(configFile, messagesFile, buildListener));

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
