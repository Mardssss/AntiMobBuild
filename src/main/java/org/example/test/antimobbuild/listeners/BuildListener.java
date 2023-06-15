package org.example.test.antimobbuild.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.List;
import java.util.stream.Collectors;

public class BuildListener implements Listener {
    private boolean canCreateIronGolem;
    private boolean canCreateSnowMan;
    private boolean canCreateWither;
    private boolean canCureVillager;
    private boolean canInfectVillager;
    private boolean canSpawnSilverFishOnBlocks;
    private boolean canSpawnFromEggs;
    private boolean broadcastMessagesToPlayer;
    private boolean broadcastMessagesToConsole;
    private boolean naturalMobSpawnEnabled;
    private List<EntityType> naturalSpawnExceptions;
    private List<EntityType> spawnExceptions;
    private FileConfiguration config;
    private FileConfiguration messages;

    public BuildListener(FileConfiguration config,FileConfiguration messages) {
        this.config = config;
        this.messages = messages;
        updateConfig(config,messages);
    }
    public void updateConfig(FileConfiguration config,FileConfiguration messages) {
        this.config = config;
        this.messages = messages;
        this.canCreateIronGolem = config.getBoolean("can-create-irongolem");
        this.canCreateSnowMan = config.getBoolean("can-create-snowman");
        this.canCreateWither = config.getBoolean("can-create-wither");
        this.canCureVillager = config.getBoolean("can-cure-villager");
        this.canInfectVillager = config.getBoolean("can-infect-villager");
        this.canSpawnSilverFishOnBlocks = config.getBoolean("can-spawn-silver-fish-on-blocks");
        this.canSpawnFromEggs = config.getBoolean("can-spawn-from-eggs.enabled");
        this.naturalMobSpawnEnabled = config.getBoolean("natural-mob-spawn.enabled");
        // broadcast messagge to players
        this.broadcastMessagesToPlayer = config.getBoolean("broadcast-messages-to-player");
        // broadcast messages to player
        this.broadcastMessagesToConsole = config.getBoolean("broadcast-messages-to-console");
        // get the config natural-mob-spawn exceptions
        this.naturalSpawnExceptions = config.getStringList("natural-mob-spawn.exceptions").stream()
                .map(EntityType::valueOf)
                .collect(Collectors.toList());
        // get the config can-spawn-from-eggs exceptions
        this.spawnExceptions = config.getStringList("can-spawn-from-eggs.exceptions").stream()
                .map(EntityType::valueOf)
                .collect(Collectors.toList());
    }

    @EventHandler
    public void onCreatureCreate(CreatureSpawnEvent event) {
        CreatureSpawnEvent.SpawnReason spawnReason = event.getSpawnReason();
        EntityType entityType = event.getEntityType();
        if (entityType == EntityType.IRON_GOLEM && spawnReason == CreatureSpawnEvent.SpawnReason.BUILD_IRONGOLEM) {
            if (!canCreateIronGolem) {
                event.setCancelled(true);
                if (broadcastMessagesToPlayer) {
                    event.getEntity().getWorld().getPlayers().forEach(player -> player.sendMessage(messages.getString(ChatColor.translateAlternateColorCodes('&',"irongolem-spawn-denied"))));
                }
                if (broadcastMessagesToConsole) {
                    Bukkit.getLogger().info("IRONGOLEM spawning is not allowed.");
                }
            }
        } else if (entityType == EntityType.SNOWMAN && spawnReason == CreatureSpawnEvent.SpawnReason.BUILD_SNOWMAN) {
            if (!canCreateSnowMan) {
                event.setCancelled(true);
                if (broadcastMessagesToPlayer) {
                    event.getEntity().getWorld().getPlayers().forEach(player -> player.sendMessage(messages.getString(ChatColor.translateAlternateColorCodes('&',"snowman-spawn-denied"))));
                }
                if (broadcastMessagesToConsole) {
                    Bukkit.getLogger().info("SNOWMAN spawning is not allowed.");
                }
            }
        } else if (entityType == EntityType.WITHER && spawnReason == CreatureSpawnEvent.SpawnReason.BUILD_WITHER) {
            if (!canCreateWither) {
                event.setCancelled(true);
                if (broadcastMessagesToPlayer) {
                    event.getEntity().getWorld().getPlayers().forEach(player -> player.sendMessage(messages.getString(ChatColor.translateAlternateColorCodes('&',"wither-spawn-denied"))));
                }
                if (broadcastMessagesToConsole) {
                    Bukkit.getLogger().info("WITHER spawning is not allowed.");
                }
            }
        } else if (entityType == EntityType.VILLAGER && spawnReason == CreatureSpawnEvent.SpawnReason.CURED) {
            if (!canCureVillager) {
                event.setCancelled(true);
                if (broadcastMessagesToPlayer) {
                    event.getEntity().getWorld().getPlayers().forEach(player -> player.sendMessage(messages.getString(ChatColor.translateAlternateColorCodes('&',"villager-cure-denied"))));
                }
                if (broadcastMessagesToConsole) {
                    Bukkit.getLogger().info("VILLAGER curing is not allowed.");
                }
            }
        } else if (entityType == EntityType.VILLAGER && spawnReason == CreatureSpawnEvent.SpawnReason.INFECTION) {
            if (!canInfectVillager) {
                event.setCancelled(true);
                if (broadcastMessagesToPlayer) {
                    event.getEntity().getWorld().getPlayers().forEach(player -> player.sendMessage(messages.getString(ChatColor.translateAlternateColorCodes('&',"villager-infection-denied"))));
                }
                if (broadcastMessagesToConsole) {
                    Bukkit.getLogger().info("VILLAGER infection is not allowed.");
                }
            }
        } else if (entityType == EntityType.SILVERFISH && spawnReason == CreatureSpawnEvent.SpawnReason.SILVERFISH_BLOCK) {
            if (!canSpawnSilverFishOnBlocks) {
                event.setCancelled(true);
                if (broadcastMessagesToPlayer) {
                    event.getEntity().getWorld().getPlayers().forEach(player -> player.sendMessage(messages.getString(ChatColor.translateAlternateColorCodes('&',"silverfish-spawn-denied"))));
                }
                if (broadcastMessagesToConsole) {
                    Bukkit.getLogger().info("Spawning silverfigh in block  is not allowed.");
                }
            }
        } else if (spawnReason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG) {
            if (!canSpawnFromEggs && !spawnExceptions.contains(entityType)) {
                event.setCancelled(true);
                if (broadcastMessagesToPlayer) {
                    event.getEntity().getWorld().getPlayers().forEach(player -> player.sendMessage(messages.getString(ChatColor.translateAlternateColorCodes('&',"spawn-from-eggs-denied"))));
                }
                if (broadcastMessagesToConsole) {
                    Bukkit.getLogger().info("Mob spawning from spawn eggs "+event.getEntityType().name().toLowerCase()+ " is not allowed");
                }
            }
        }else if (naturalMobSpawnEnabled) {
            if (!naturalSpawnExceptions.contains(entityType)){
                event.setCancelled(true);
            }
        }
    }
    /*
    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event){
        EntityType entityType= event.getEntityType();
        if (naturalMobSpawnEnabled){
            if(!naturalSpawnExceptions.contains(entityType)) {
                event.setCancelled(true);
                if (broadcastMessagesToPlayer){
                    event.getEntity().getWorld().getPlayers().forEach(player -> player.sendMessage(messages.getString(ChatColor.translateAlternateColorCodes('&',"natural-spawn-denied"))));
                }
            }
        }
        */
        /*
        if (event.getEntityType() == EntityType.CREEPER){
            event.setCancelled(true);
            if (broadcastMessagesToPlayer){
                event.getEntity().getWorld().getPlayers().forEach(player -> player.sendMessage("you can spawn creeper"));
            }
        }
        */
}
