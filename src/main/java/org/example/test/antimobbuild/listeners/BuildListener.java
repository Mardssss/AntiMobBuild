package org.example.test.antimobbuild.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.example.test.antimobbuild.Config;

public class BuildListener implements Listener {
    private final Config config;

    public BuildListener(Config config) {
        this.config = config;
    }

    @EventHandler
    public void onCreatureCreate(CreatureSpawnEvent event) {
        CreatureSpawnEvent.SpawnReason spawnReason = event.getSpawnReason();
        EntityType entityType = event.getEntityType();
        if (entityType == EntityType.IRON_GOLEM && spawnReason == CreatureSpawnEvent.SpawnReason.BUILD_IRONGOLEM) {
            if (!config.isCanCreateIronGolem()) {
                event.setCancelled(true);

                if (config.isBroadcastMessagesToPlayers()) {
                    event.getEntity().getWorld().getPlayers().forEach(player -> player.sendMessage(config.ironGolemSpawnDenied()));
                }
                if (config.logMessagesToConsole()) {
                    Bukkit.getLogger().info("IRONGOLEM spawning is not allowed.");
                }
            }
        } else if (entityType == EntityType.SNOWMAN && spawnReason == CreatureSpawnEvent.SpawnReason.BUILD_SNOWMAN) {
            if (!config.isCanCreateSnowMan()) {
                event.setCancelled(true);

                if (config.isBroadcastMessagesToPlayers()) {
                    event.getEntity().getWorld().getPlayers().forEach(player -> player.sendMessage(config.snowmanSpawnDenied()));
                }
                if (config.logMessagesToConsole()) {
                    Bukkit.getLogger().info("SNOWMAN spawning is not allowed.");
                }
            }
        } else if (entityType == EntityType.WITHER && spawnReason == CreatureSpawnEvent.SpawnReason.BUILD_WITHER) {
            if (!config.isCanCreateWither()) {
                event.setCancelled(true);
                if (config.isBroadcastMessagesToPlayers()) {
                    event.getEntity().getWorld().getPlayers().forEach(player -> player.sendMessage(config.witherSpawnDenied()));
                }
                if (config.logMessagesToConsole()) {
                    Bukkit.getLogger().info("WITHER spawning is not allowed.");
                }
            }
        } else if (entityType == EntityType.VILLAGER && spawnReason == CreatureSpawnEvent.SpawnReason.CURED) {
            if (!config.isCanCureVillager()) {
                event.setCancelled(true);
                if (config.isBroadcastMessagesToPlayers()) {
                    event.getEntity().getWorld().getPlayers().forEach(player -> player.sendMessage(config.villagerCureDenied()));
                }
                if (config.logMessagesToConsole()) {
                    Bukkit.getLogger().info("VILLAGER curing is not allowed.");
                }
            }
        } else if (entityType == EntityType.VILLAGER && spawnReason == CreatureSpawnEvent.SpawnReason.INFECTION) {
            if (!config.isCanInfectVillager()) {
                event.setCancelled(true);
                if (config.isBroadcastMessagesToPlayers()) {
                    event.getEntity().getWorld().getPlayers().forEach(player -> player.sendMessage(config.villagerInfectionDenied()));
                }
                if (config.logMessagesToConsole()) {
                    Bukkit.getLogger().info("VILLAGER infection is not allowed.");
                }
            }
        } else if (entityType == EntityType.SILVERFISH && spawnReason == CreatureSpawnEvent.SpawnReason.SILVERFISH_BLOCK) {
            if (!config.isCanSpawnSilverFishOnBlocks()) {
                event.setCancelled(true);
                if (config.isBroadcastMessagesToPlayers()) {
                    event.getEntity().getWorld().getPlayers().forEach(player -> player.sendMessage(config.silverfishSpawnDenied()));
                }
                if (config.logMessagesToConsole()) {
                    Bukkit.getLogger().info("Spawning silverfigh in block  is not allowed.");
                }
            }
        } else if (spawnReason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG) {
            if (!config.isCanSpawnFromEggs() && !config.getSpawnExceptions().contains(entityType)) {
                event.setCancelled(true);
                if (config.isBroadcastMessagesToPlayers()) {
                    event.getEntity()
                            .getWorld()
                            .getPlayers()
                            .forEach(player ->
                                    player.sendMessage(config.spawnFromEggsDenied()));
                }
                if (config.logMessagesToConsole()) {
                    Bukkit.getLogger().info("Mob spawning from spawn eggs "+event.getEntityType().name().toLowerCase()+ " is not allowed");
                }
            }
        } else if (config.isNaturalMobSpawnEnabled()) {
            if (!config.getNaturalSpawnExceptions().contains(entityType)) {
                Bukkit.getLogger().info(config.naturalSpawnDenied());
                event.setCancelled(true);
            }
        }
    }
}
