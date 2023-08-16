package io.github.vexeval.vexutils;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import net.md_5.bungee.api.ChatColor;

public class SignInteractionListener implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
	    Action action = event.getAction(); // Get the interaction action
	    Block block = event.getClickedBlock();

	    if (action == Action.RIGHT_CLICK_BLOCK && block != null && block.getState() instanceof Sign) {
	    	Sign sign = (Sign) block.getState();
	        String firstLine = sign.getLine(0); // TODO: Update getLine (deprecated)

	        if (firstLine.equalsIgnoreCase("[vsign]") && (player.hasPermission("vsign.use") || player.isOp())) {
	        	String secondLine = sign.getLine(1);
	        	String[] coordinates = secondLine.split(",");

	        	if (coordinates.length == 3) {
	        		try {
	            		double x = Double.parseDouble(coordinates[0]);
	            		double y = Double.parseDouble(coordinates[1]);
	            		double z = Double.parseDouble(coordinates[2]);
	            		Location teleportLocation = new Location(player.getWorld(), x, y, z);
	            		player.teleport(teleportLocation, TeleportCause.PLUGIN);
	            	} catch (NumberFormatException ignored) {
		                	player.sendMessage(ChatColor.RED+"Invalid Sign Data");
	                }
	            }
	        }
	    }
	}

}
