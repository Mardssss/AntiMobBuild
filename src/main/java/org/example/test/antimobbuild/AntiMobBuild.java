package org.example.test.antimobbuild;

import org.bukkit.plugin.java.JavaPlugin;
import org.example.test.antimobbuild.commands.Commands;
import org.example.test.antimobbuild.listeners.BuildListener;

import java.util.Objects;

public final class AntiMobBuild extends JavaPlugin {
    Config config;
    @Override
    public void onEnable() {
        this.config = new Config(this);

        // Create an instance of BuildListener and pass the config and messages
        BuildListener buildListener = new BuildListener(this.config);
        // Register the BuildListener
        getServer().getPluginManager().registerEvents(buildListener, this);

        // Register the commands
        Objects.requireNonNull(getCommand("amb")).setExecutor(new Commands(config));

        // Print a cool message to the console
        getLogger().info("*********************************************");
        getLogger().info("*                                           *");
        getLogger().info("*            AntiMobBuild Plugin            *");
        getLogger().info("*                                           *");
        getLogger().info("*********************************************");
        getLogger().info("test");

        // Other plugin initialization code
    }

    @Override
    public void onDisable() {
        // Print a message to the console
        getLogger().info("AntiMobBuild Plugin Disabled");

    }
}
