package io.github.vexeval.vexutils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class HomeCommandExecutor implements CommandExecutor {

	public static final String COMMAND_USE_HOME = "home";
	public static final String COMMAND_SAVE_HOME = "sethome";
    public static final String COMMAND_DELETE_HOME = "delhome";	
    public static final String DIR_HOME_CONF = "plugins/VexUtils/homes";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	// CREATE
        if (command.getName().equalsIgnoreCase(COMMAND_SAVE_HOME)) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 1) {
                    String homeName = args[0];

                    File playerFile = new File(DIR_HOME_CONF + File.separator + player.getUniqueId() + ".yml");
                    FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

                    // Save home location to the player's config
                    playerConfig.set("homes." + homeName + ".world", player.getLocation().getWorld().getName());
                    playerConfig.set("homes." + homeName + ".x", player.getLocation().getX());
                    playerConfig.set("homes." + homeName + ".y", player.getLocation().getY());
                    playerConfig.set("homes." + homeName + ".z", player.getLocation().getZ());
                    playerConfig.set("homes." + homeName + ".yaw", player.getLocation().getYaw());
                    playerConfig.set("homes." + homeName + ".pitch", player.getLocation().getPitch());

                    try {
                        playerConfig.save(playerFile);
                        player.sendMessage("Home '" + VexUtils.mainColor + homeName + VexUtils.secondaryColor + "' saved!");
                    } catch (IOException e) {
                        e.printStackTrace();
                        player.sendMessage(ChatColor.RED + "An error occurred while saving the home. Please report if the issue persists.");
                    }
                } else {
                    player.sendMessage("Usage: /" + COMMAND_SAVE_HOME + " <name>");
                }
            }
        }
        
        // USE
        if (command.getName().equalsIgnoreCase(COMMAND_USE_HOME)) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 1) {
                    String homeName = args[0];

                    File playerFile = new File(DIR_HOME_CONF + File.separator + player.getUniqueId() + ".yml");
//                    player.sendMessage("Home dir " + DIR_HOME_CONF + " Loaded");
                    FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

                    if (playerConfig.contains("homes." + homeName)) {
                        // Teleport the player to the home location
                        World world = Bukkit.getWorld(playerConfig.getString("homes." + homeName + ".world"));
                        double x = playerConfig.getDouble("homes." + homeName + ".x");
                        double y = playerConfig.getDouble("homes." + homeName + ".y");
                        double z = playerConfig.getDouble("homes." + homeName + ".z");
                        float yaw = (float) playerConfig.getDouble("homes." + homeName + ".yaw");
                        float pitch = (float) playerConfig.getDouble("homes." + homeName + ".pitch");

                        Location homeLocation = new Location(world, x, y, z, yaw, pitch);
                        player.teleport(homeLocation);
                        player.sendMessage("Teleported to home '" + VexUtils.mainColor + homeName + VexUtils.secondaryColor + "'!");
                    } else {
                        player.sendMessage("Home '" + VexUtils.mainColor + homeName + VexUtils.secondaryColor + "' doesn't exist.");
                    }
                } else {
                    player.sendMessage("Usage: /" + COMMAND_USE_HOME + " <name>");
                }
            }
        }
        
        // DELETE
        if (command.getName().equalsIgnoreCase(COMMAND_DELETE_HOME)) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 1) {
                    String homeName = args[0];

                    File playerFile = new File(DIR_HOME_CONF + File.separator + player.getUniqueId() + ".yml");
                    FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

                    if (playerConfig.contains("homes." + homeName)) {
                        // Delete the home location
                        playerConfig.set("homes." + homeName, null);
                        try {
                            playerConfig.save(playerFile);
                            player.sendMessage("Home '" + VexUtils.mainColor + homeName + VexUtils.secondaryColor + "' deleted!");
                        } catch (IOException e) {
                            e.printStackTrace();
                            player.sendMessage(ChatColor.RED+"An error occurred while deleting the home. Please report if the issue persists.");
                        }
                    } else {
                        player.sendMessage("Home '" + VexUtils.mainColor + homeName + VexUtils.secondaryColor + "' doesn't exist.");
                    }
                } else {
                    player.sendMessage("Usage: /" + COMMAND_DELETE_HOME + " <name>");
                }
            }
        }
        return true;
    }
}
