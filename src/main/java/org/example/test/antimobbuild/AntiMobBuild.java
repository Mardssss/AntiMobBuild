package org.example.test.antimobbuild;

import org.bukkit.plugin.java.JavaPlugin;
import org.example.test.antimobbuild.commands.MyCommand;
import org.example.test.antimobbuild.commands.MyTabComplete;
import org.example.test.antimobbuild.exeptions.NaturalMobSpawnException;
import org.example.test.antimobbuild.exeptions.SpawnFromEggsException;
import org.example.test.antimobbuild.listeners.BuildListener;

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
        MyCommand command = new MyCommand(config);
        MyTabComplete tabComplete = new MyTabComplete();
        getCommand("amb").setExecutor(command);
        getCommand("amb").setTabCompleter(tabComplete);

        // Print activated configuration settings to the console
        getLogger().info("Activated Configuration Settings:");
        getLogger().info("Can create Iron Golem: " + config.isCanCreateIronGolem());
        getLogger().info("Can create Snowman: " + config.isCanCreateSnowMan());
        getLogger().info("Can create Wither: " + config.isCanCreateWither());
        getLogger().info("Can cure Villager: " + config.isCanCureVillager());
        getLogger().info("Can infect Villager: " + config.isCanInfectVillager());
        getLogger().info("Can spawn Silverfish on blocks: " + config.isCanSpawnSilverFishOnBlocks());

        // Print activated exceptions for natural mob spawn and exceptions for spawning from eggs
        getLogger().info("Natural Mob Spawn Exceptions:");
        for (NaturalMobSpawnException exception : config.getNaturalMobSpawnExceptions()) {
            getLogger().info("World: " + exception.getWorld());
            getLogger().info("Enabled: " + exception.isEnabled());
            getLogger().info("Exceptions: " + exception.getMobs());
        }

        getLogger().info("Spawn From Eggs Exceptions:");
        for (SpawnFromEggsException exception : config.getSpawnFromEggsExceptions()) {
            getLogger().info("World: " + exception.getWorld());
            getLogger().info("Enabled: " + exception.isEnabled());
            getLogger().info("Exceptions: " + exception.getMobs());
        }
    }

    @Override
    public void onDisable() {
        // Print a message to the console
        getLogger().info("AntiMobBuild Plugin Disabled");

    }
}
