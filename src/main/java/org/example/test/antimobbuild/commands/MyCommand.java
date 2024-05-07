package org.example.test.antimobbuild.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.example.test.antimobbuild.Config;

import java.io.File;

public class MyCommand implements CommandExecutor {
    private final File configFile;
    private final File messageFile;

    public MyCommand(Config config) {
        this.configFile = config.getConfigFile();
        this.messageFile = config.getMessageFile();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            // sends command usage
            return false;
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload") && sender.hasPermission("antimobbuild.reload")) {
                // Reload the configuration files
                FileConfiguration customConfig = YamlConfiguration.loadConfiguration(configFile);
                FileConfiguration customMessages = YamlConfiguration.loadConfiguration(messageFile);

                try {
                    customConfig.load(configFile);
                    customMessages.load(messageFile);
                    sender.sendMessage("Configuration reloaded successfully.");
                } catch (Exception e) {
                    sender.sendMessage("Failed to reload configuration.");
                }
                return true;
            }
        }
        return false;
    }
}