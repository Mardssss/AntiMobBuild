package org.example.test.antimobbuild;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.example.test.antimobbuild.exeptions.NaturalMobSpawnException;
import org.example.test.antimobbuild.exeptions.SpawnFromEggsException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        loadConfig();
        loadMessages();
        naturalMobSpawnExceptions();
        spawnFromEggsExepetion();
    }

    public void naturalMobSpawnExceptions() {
        List<NaturalMobSpawnException> exceptions = new ArrayList<>();
        ConfigurationSection section = config.getConfigurationSection("natural-mob-spawn");
        if (section != null) {
            for (String key : section.getKeys(false)) {
                ConfigurationSection worldSection = section.getConfigurationSection(key);
                if (worldSection == null) {
                    return;
                }
                boolean isEnabled = worldSection.getBoolean("enabled");
                List<String> exceptionsList = worldSection.getStringList("exceptions");
                String worldName = key; // Assuming the key is the world name
                exceptions.add(new NaturalMobSpawnException(worldName, exceptionsList, isEnabled));
            }
        }
        this.naturalMobSpawnExceptions = exceptions;
    }


    public void spawnFromEggsExepetion() {
        List<SpawnFromEggsException> exceptions = new ArrayList<>();
        ConfigurationSection section = config.getConfigurationSection("can-spawn-from-eggs");
        if (section != null) {
            for (String key : section.getKeys(false)) {
                ConfigurationSection exceptionSection = section.getConfigurationSection(key);
                if (exceptionSection == null) {
                    return;
                }
                List<String> worlds = exceptionSection.getStringList("worlds");
                List<String> mobs = exceptionSection.getStringList("mobs");
                boolean isEnabled = exceptionSection.getBoolean("enabled");
                exceptions.add(new SpawnFromEggsException(worlds, mobs, isEnabled));
            }
        }
        this.spawnFromEggsExceptions = exceptions;
    }

    private void loadConfig() {
        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false); // Save default config if doesn't exist
        }

        config = YamlConfiguration.loadConfiguration(configFile); // Load the config into memory
    }

    private void loadMessages() {
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

    public boolean isCanSpawnFromEggs() {
        return config.getBoolean("can-spawn-from-eggs");
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

    public boolean isNaturalMobSpawnEnabled() {
        return config.getBoolean("natural-mob-spawn.enabled");
    }

    public List<EntityType> getNaturalSpawnExceptions() {
        return config.getStringList("natural-mob-spawn.exceptions").stream()
                .map(EntityType::valueOf)
                .collect(Collectors.toList());
    }

    public List<EntityType> getSpawnExceptions() {
        return config.getStringList("can-spawn-from-eggs.exceptions").stream()
                .map(EntityType::valueOf)
                .collect(Collectors.toList());
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
