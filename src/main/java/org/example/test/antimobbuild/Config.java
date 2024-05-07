package org.example.test.antimobbuild;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.example.test.antimobbuild.exeptions.NaturalMobSpawnException;
import org.example.test.antimobbuild.exeptions.SpawnFromEggsException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Config {
    private final AntiMobBuild plugin;
    private final File configFile;
    private final File messageFile;
    private FileConfiguration config;
    private FileConfiguration messageConfig;

    private List<NaturalMobSpawnException> naturalMobSpawnExceptions;
    private List<SpawnFromEggsException> spawnFromEggsExceptions;

    public Config(AntiMobBuild plugin) {
        this.plugin = plugin;
        configFile = new File(plugin.getDataFolder(), "config.yml");
        messageFile = new File(plugin.getDataFolder(), "messages.yml");
        loadConfig(plugin);
        loadMessages(plugin);
    }

    public void naturalMobSpawnExceptions() {
        List<NaturalMobSpawnException> exceptions = new ArrayList<>();
        ConfigurationSection section = config.getConfigurationSection("natural-mob-spawn");
        if (section != null) {
            for (String worldName : section.getKeys(false)) {
                ConfigurationSection worldSection = section.getConfigurationSection(worldName);
                if (worldSection == null) {
                    return;
                }
                boolean isEnabled = worldSection.getBoolean("enabled");
                List<String> exceptionsList = worldSection.getStringList("exceptions");
                exceptions.add(new NaturalMobSpawnException(worldName, exceptionsList, isEnabled));
            }
        }
        this.naturalMobSpawnExceptions = exceptions;
    }


    public void spawnFromEggsException() {
        List<SpawnFromEggsException> exceptions = new ArrayList<>();
        ConfigurationSection section = config.getConfigurationSection("can-spawn-from-eggs");
        if (section != null) {
            for (String worldName : section.getKeys(false)) {
                ConfigurationSection worldSection = section.getConfigurationSection(worldName);
                if (worldSection == null) {
                    return;
                }
                boolean isEnabled = worldSection.getBoolean("enabled");
                List<String> exceptionsList = worldSection.getStringList("exceptions");
                exceptions.add(new SpawnFromEggsException(worldName, exceptionsList, isEnabled));
            }
        }
        this.spawnFromEggsExceptions = exceptions;
    }

    private void loadConfig(AntiMobBuild plugin) {
        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false); // Save default config if doesn't exist
        }

        config = YamlConfiguration.loadConfiguration(configFile); // Load the config into memory
        try {
            naturalMobSpawnExceptions();
            spawnFromEggsException();
        } catch (Exception e) {
            // Log an error message indicating the exception
            plugin.getLogger().severe("An error occurred while handling mob spawn exceptions: " + e.getMessage());
        }
    }

    private void loadMessages(AntiMobBuild plugin) {
        if (!messageFile.exists()) {
            plugin.saveResource("messages.yml", false); // Save default config if doesn't exist
        }

        messageConfig = YamlConfiguration.loadConfiguration(messageFile); // Load the config into memory
    }

    public boolean isCanCreateIronGolem() {
        return config.getBoolean("can-create-golem");
    }

    public boolean isCanCreateSnowMan() {
        return config.getBoolean("can-create-snowman");
    }

    public boolean isCanCreateWither() {
        return config.getBoolean("can-create-wither");
    }

    public boolean isCanCureVillager() {
        return config.getBoolean("can-cure-villager");
    }

    public boolean isCanInfectVillager() {
        return config.getBoolean("can-infect-villager");
    }

    public boolean isCanSpawnSilverFishOnBlocks() {
        return config.getBoolean("can-spawn-silver-fish-on-blocks");
    }


    public boolean isMessagePlayer() {
        return config.getBoolean("message-player");
    }

    public boolean isBroadcastMessagesToPlayers() {
        return config.getBoolean("broadcast-messages-to-players");
    }

    public boolean logMessagesToConsole() {
        return config.getBoolean("log-messages-to-console");
    }

    public String ironGolemSpawnDenied() {
        return messageConfig.getString(Utils.color("irongolem-spawn-denied"));
    }

    public String witherSpawnDenied() {
        return messageConfig.getString(Utils.color("wither-spawn-denied"));
    }

    public String snowmanSpawnDenied() {
        return messageConfig.getString(Utils.color("snowman-spawn-denied"));
    }

    public String villagerCureDenied() {
        return messageConfig.getString(Utils.color("villager-cure-denied"));
    }

    public String villagerInfectionDenied() {
        return messageConfig.getString(Utils.color("villager-infection-denied"));
    }

    public String silverfishSpawnDenied() {
        return messageConfig.getString(Utils.color("silverfish-spawn-denied"));
    }

    public String spawnFromEggsDenied() {
        return messageConfig.getString(Utils.color("spawn-from-eggs-denied"));
    }

    public String naturalSpawnDenied() {
        return messageConfig.getString(Utils.color("natural-spawn-denied"));
    }

    public File getConfigFile() {
        return configFile;
    }

    public File getMessageFile() {
        return messageFile;
    }
    // getters methods
    public List<NaturalMobSpawnException> getNaturalMobSpawnExceptions() {
        return naturalMobSpawnExceptions;
    }

    public List<SpawnFromEggsException> getSpawnFromEggsExceptions() {
        return spawnFromEggsExceptions;
    }
}
