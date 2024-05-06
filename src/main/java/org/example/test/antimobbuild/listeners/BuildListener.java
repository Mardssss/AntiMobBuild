package org.example.test.antimobbuild.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.example.test.antimobbuild.Config;
import org.example.test.antimobbuild.exeptions.NaturalMobSpawnException;
import org.example.test.antimobbuild.exeptions.SpawnFromEggsException;

import java.util.List;

public class BuildListener implements Listener {
    private final Config config;

    public BuildListener(Config config) {
        this.config = config;
    }

    @EventHandler
    public void onCreatureCreate(CreatureSpawnEvent event) {
        CreatureSpawnEvent.SpawnReason spawnReason = event.getSpawnReason();
        EntityType entityType = event.getEntityType();
        if (entityType == EntityType.IRON_GOLEM &&
                spawnReason == CreatureSpawnEvent.SpawnReason.BUILD_IRONGOLEM) {
            if (!config.isCanCreateIronGolem()) {
                cancelSpawnEvent(event, config.ironGolemSpawnDenied()
                        , "IRONGOLEM spawning is not allowed.");

            }
        } else if (entityType == EntityType.SNOWMAN &&
                spawnReason == CreatureSpawnEvent.SpawnReason.BUILD_SNOWMAN) {
            if (!config.isCanCreateSnowMan()) {
                cancelSpawnEvent(event, config.snowmanSpawnDenied()
                        , "SNOWMAN spawning is not allowed.");

            }
        } else if (entityType == EntityType.WITHER &&
                spawnReason == CreatureSpawnEvent.SpawnReason.BUILD_WITHER) {
            if (!config.isCanCreateWither()) {
                cancelSpawnEvent(event, config.witherSpawnDenied(),
                        "WITHER spawning is not allowed.");
            }
        } else if (entityType == EntityType.VILLAGER &&
                spawnReason == CreatureSpawnEvent.SpawnReason.CURED) {
            if (!config.isCanCureVillager()) {
                cancelSpawnEvent(event, config.villagerCureDenied(),
                        "VILLAGER curing is not allowed.");
            }
        } else if (entityType == EntityType.VILLAGER &&
                spawnReason == CreatureSpawnEvent.SpawnReason.INFECTION) {
            if (!config.isCanInfectVillager()) {
                cancelSpawnEvent(event, config.villagerInfectionDenied(),
                        "VILLAGER infection is not allowed.");
            }
        } else if (entityType == EntityType.SILVERFISH && spawnReason == CreatureSpawnEvent.SpawnReason.SILVERFISH_BLOCK) {
            if (!config.isCanSpawnSilverFishOnBlocks()) {
                cancelSpawnEvent(event, config.silverfishSpawnDenied(),
                        "Spawning silverfigh in block  is not allowed.");
            }
        } else if (spawnReason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG) {
            spawnFromEggs(event);
        } else {
            naturalMobSpawn(event);
        }
    }

    /**
     * Handles spawn od mobs from eggs<br>
     * each world has its own {@link List<String> mobs}
     *
     * @param event The CreatureSpawnEvent triggered when a creature is spawned.
     * @see SpawnFromEggsException
     */
    private void spawnFromEggs(CreatureSpawnEvent event) {
        EntityType entityType = event.getEntityType();
        List<SpawnFromEggsException> exceptions = config.getSpawnFromEggsExceptions();
        for (SpawnFromEggsException se : exceptions) {
            String worldName = se.getWorld();
            List<String> mobs = se.getMobs();
            boolean isEnabled = se.isEnabled();

            // Check if the event's world matches the world specified in the exception
            if (event.getLocation().getWorld().getName().equals(worldName)) {
                // Check if the mob spawning is enabled for this world
                if (isEnabled) {
                    // Check if the spawned entity is in the list of exceptions for this world
                    if (mobs.contains(entityType.name())) {
                        // Mob spawning is allowed for this world, but this specific mob is an exception
                        // allow mobs to spawn
                    } else {
                        // Mob from eggs is allowed for this world and this mob is not an exception
                        // stop mobs from spawning from eggs
                        event.setCancelled(true);
                        cancelSpawnEvent(event,
                                config.spawnFromEggsDenied(),
                                "Spawning " + entityType + " from eggs is not allowed in world " + event.getLocation().getWorld().getName());
                        if (config.isBroadcastMessagesToPlayers()) {
                            event.getEntity().getWorld().getPlayers().forEach(player -> player.sendMessage(config.spawnFromEggsDenied()));
                        }
                        if (config.logMessagesToConsole()) {
                            Bukkit.getLogger().info("Spawning " + entityType + " from eggs is not allowed in world " + event.getLocation().getWorld().getName());
                        }
                    }
                } else {
                    // Mob spawning from eggs is not enabled for this world
                    return;
                }
            }
        }
    }


    /**
     * Handles natural mob spawning<br>
     * each world has its own {@link List<String> mobs}
     * @see SpawnFromEggsException
     * @param event The CreatureSpawnEvent triggered when a creature is spawned.
     */
    private void naturalMobSpawn(CreatureSpawnEvent event) {
        EntityType entityType = event.getEntityType();
        List<NaturalMobSpawnException> exceptions = config.getNaturalMobSpawnExceptions();

        for (NaturalMobSpawnException nt : exceptions) {
            String worldName = nt.getWorld();
            List<String> mobs = nt.getMobs();
            boolean isEnabled = nt.isEnabled();

            // Check if the event's world matches the world specified in the exception
            if (event.getLocation().getWorld().getName().equals(worldName)) {
                // Check if the mob spawning is enabled for this world
                if (isEnabled) {
                    // Check if the spawned entity is in the list of exceptions for this world
                    if (mobs.contains(entityType.name())) {
                        // Mob spawning is allowed for this world, but this specific mob is an exception
                        // allow mobs to spawn
                    } else {
                        // Mob spawning is allowed for this world and this mob is not an exception
                        // stop mobs from spawning
                        cancelSpawnEvent(event,
                                config.naturalSpawnDenied(),
                                "Spawning of " + entityType + " is not allowed in world " + event.getLocation().getWorld().getName());
                        if (config.isBroadcastMessagesToPlayers()) {
                            event.getEntity().getWorld().getPlayers().forEach(player -> player.sendMessage(config.naturalSpawnDenied()));
                        }
                        if (config.logMessagesToConsole()) {
                            Bukkit.getLogger().info("Spawning of " + entityType + " is not allowed in world " + event.getLocation().getWorld().getName());
                        }
                    }
                } else {
                    // Mob spawning is not enabled for this world
                    return;
                }
            }
        }
    }

    /**
     * Helper method to cancel events and send messages
     * @param event the event that will be canceled
     * @param broadcastMessage the message to broadcast to players
     * @param logMessage the log message
     */
    private void cancelSpawnEvent(CreatureSpawnEvent event, String broadcastMessage, String logMessage) {
        try {
            event.setCancelled(true);
            if (config.isBroadcastMessagesToPlayers()) {
                event.getEntity().getWorld().getPlayers().forEach(player -> player.sendMessage(broadcastMessage));
            }
            if (config.logMessagesToConsole()) {
                Bukkit.getLogger().info(logMessage);
            }

        } catch (Exception e) {
            // Handle the exception (e.g., log it or notify the player/admin)
            Bukkit.getLogger().warning("An error occurred while handling a spawn event: " + e.getMessage());
        }
    }
}
