package org.example.test.antimobbuild.listeners;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.List;
import java.util.stream.Collectors;

public class BuildListener implements Listener {
    private FileConfiguration customConfig;
    private boolean canCreateIronGolem;
    private boolean canCreateSnowMan;
    private boolean canCreateWither;
    private boolean canCureVillager;
    private boolean canInfectVillager;
    private boolean canSpawnSilverFishOnBlocks;
    private boolean canSpawnFromEggs;
    private boolean broadcastMessagesToPlayer;
    private boolean broadcastMessagesToConsole;
    private List<EntityType> spawnExceptions;

    public BuildListener(FileConfiguration config) {
        updateConfig(config);
    }

    public void updateConfig(FileConfiguration config) {
        this.customConfig = config;
        this.canCreateIronGolem = config.getBoolean("canCreateIronGolem");
        this.canCreateSnowMan = config.getBoolean("canCreateSnowMan");
        this.canCreateWither = config.getBoolean("canCreateWither");
        this.canCureVillager = config.getBoolean("canCureVillager");
        this.canInfectVillager = config.getBoolean("canInfectVillager");
        this.canSpawnSilverFishOnBlocks = config.getBoolean("canSpawnSilverFishOnBlocks");
        this.canSpawnFromEggs = config.getBoolean("canSpawnFromEggs");
        this.broadcastMessagesToPlayer = config.getBoolean("broadcastMessagesToPlayer");
        this.broadcastMessagesToConsole = config.getBoolean("broadcastMessagesToConsole");
        // Update spawnExceptions from the config
        this.spawnExceptions = config.getStringList("exceptions").stream()
                .map(EntityType::valueOf)
                .collect(Collectors.toList());
    }


    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        CreatureSpawnEvent.SpawnReason spawnReason = event.getSpawnReason();
        EntityType entityType = event.getEntityType();

        if (entityType == EntityType.IRON_GOLEM && spawnReason == CreatureSpawnEvent.SpawnReason.BUILD_IRONGOLEM) {
            if (!canCreateIronGolem) {
                event.setCancelled(true);
                if (broadcastMessagesToPlayer) {
                    event.getEntity().getWorld().getPlayers().forEach(player -> player.sendMessage("Iron Golem spawning is not allowed."));
                }
                if (broadcastMessagesToConsole) {
                    Bukkit.getLogger().info("IRON_GOLEM spawning is not allowed.");
                }
            }
        } else if (entityType == EntityType.SNOWMAN && spawnReason == CreatureSpawnEvent.SpawnReason.BUILD_SNOWMAN) {
            if (!canCreateSnowMan) {
                event.setCancelled(true);
                if (broadcastMessagesToPlayer) {
                    event.getEntity().getWorld().getPlayers().forEach(player -> player.sendMessage("Snowman spawning is not allowed."));
                }
                if (broadcastMessagesToConsole) {
                    Bukkit.getLogger().info("SNOWMAN spawning is not allowed.");
                }
            }
        } else if (entityType == EntityType.WITHER && spawnReason == CreatureSpawnEvent.SpawnReason.BUILD_WITHER) {
            if (!canCreateWither) {
                event.setCancelled(true);
                if (broadcastMessagesToPlayer) {
                    event.getEntity().getWorld().getPlayers().forEach(player -> player.sendMessage("Wither spawning is not allowed."));
                }
                if (broadcastMessagesToConsole) {
                    Bukkit.getLogger().info("WITHER spawning is not allowed.");
                }
            }
        } else if (entityType == EntityType.VILLAGER && spawnReason == CreatureSpawnEvent.SpawnReason.CURED) {
            if (!canCureVillager) {
                event.setCancelled(true);
                if (broadcastMessagesToPlayer) {
                    event.getEntity().getWorld().getPlayers().forEach(player -> player.sendMessage("Villager curing is not allowed."));
                }
                if (broadcastMessagesToConsole) {
                    Bukkit.getLogger().info("VILLAGER curing is not allowed.");
                }
            }
        } else if (entityType == EntityType.VILLAGER && spawnReason == CreatureSpawnEvent.SpawnReason.INFECTION) {
            if (!canInfectVillager) {
                event.setCancelled(true);
                if (broadcastMessagesToPlayer) {
                    event.getEntity().getWorld().getPlayers().forEach(player -> player.sendMessage("Villager infection is not allowed."));
                }
                if (broadcastMessagesToConsole) {
                    Bukkit.getLogger().info("VILLAGER infection is not allowed.");
                }
            }
        } else if (entityType == EntityType.SILVERFISH && spawnReason == CreatureSpawnEvent.SpawnReason.SILVERFISH_BLOCK) {
            if (!canSpawnSilverFishOnBlocks) {
                event.setCancelled(true);
                if (broadcastMessagesToPlayer) {
                    event.getEntity().getWorld().getPlayers().forEach(player -> player.sendMessage("Silverfish spawning is not allowed."));
                }
                if (broadcastMessagesToConsole) {
                    Bukkit.getLogger().info("SILVERFISH spawning is not allowed.");
                }
            }
        } else if (spawnReason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG) {
            if (!canSpawnFromEggs && !spawnExceptions.contains(entityType)) {
                event.setCancelled(true);
                if (broadcastMessagesToPlayer) {
                    event.getEntity().getWorld().getPlayers().forEach(player -> player.sendMessage("Mob spawning from spawn eggs "+entityType.toString().toLowerCase()+" is not allowed."));
                }
                if (broadcastMessagesToConsole) {
                    Bukkit.getLogger().info("Mob spawning from spawn eggs "+entityType+toString().toLowerCase()+ " is not allowed");
                }
            }
        }
    }
}
