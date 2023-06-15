package org.example.test.antimobbuild.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.example.test.antimobbuild.listeners.BuildListener;

import java.io.File;

public class Commands implements CommandExecutor {
    private final File configFile;
    private final File messageFile;
    private final BuildListener buildListener;

    public Commands(File configFile, File messageFile, BuildListener buildListener) {
        this.configFile = configFile;
        this.messageFile = messageFile;
        this.buildListener = buildListener;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("amb") && args.length == 1 && args[0].equalsIgnoreCase("reload") && sender.hasPermission("antimobbuild.reload")) {
            // Reload the configuration files
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(configFile);
            FileConfiguration customMessages = YamlConfiguration.loadConfiguration(messageFile);

            try {
                customConfig.load(configFile);
                customMessages.load(messageFile);
                buildListener.updateConfig(customConfig, customMessages);
                sender.sendMessage("Configuration reloaded successfully.");
            } catch (Exception e) {
                sender.sendMessage("Failed to reload configuration.");
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
}