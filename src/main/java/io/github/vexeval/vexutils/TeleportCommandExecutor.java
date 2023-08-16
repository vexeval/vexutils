package io.github.vexeval.vexutils;

import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class TeleportCommandExecutor implements CommandExecutor {

	private final VexUtils plugin;

    public TeleportCommandExecutor(VexUtils plugin) {
        this.plugin = plugin;
    }
	
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("tpa")) {
            if (sender instanceof Player && sender.hasPermission("tpa.use")) {
                Player requester = (Player) sender;
                Player target = plugin.getServer().getPlayerExact(args[0]);

                if (target != null) {
                    TeleportRequest request = new TeleportRequest(requester.getUniqueId(), target.getUniqueId());

                    if (!plugin.getTeleportRequests().containsKey(request)) {
                        plugin.getTeleportRequests().put(request, request.getExpirationTime());
                        target.sendMessage(VexUtils.mainColor + requester.getName() + VexUtils.secondaryColor+" wants to teleport to you. Type "+VexUtils.mainColor+"'/tpaccept'"+VexUtils.secondaryColor+" to accept or "+VexUtils.mainColor+"'/tpadeny'"+VexUtils.secondaryColor+" to deny.");
                        requester.sendMessage("Teleport request sent to " + VexUtils.mainColor + target.getName());
                    } else {
                        requester.sendMessage(VexUtils.secondaryColor +"You already have a pending request for " + VexUtils.mainColor + target.getName());
                    }
                } else {
                    sender.sendMessage(VexUtils.secondaryColor + "Player not found.");
                }
            }
            else {
            	sender.sendMessage(ChatColor.RED + "Unable to execute command");
            }
            
        } else if (command.getName().equalsIgnoreCase("tpaccept")) {
            if (sender instanceof Player && sender.hasPermission("tpa.accept")) {
                Player target = (Player) sender;
                TeleportRequest request = getValidTeleportRequest(target);

                if (request != null) {
                    Player requester = plugin.getServer().getPlayer(request.getRequester());

                    if (requester != null) {
                        target.teleport(requester);
                        requester.sendMessage(VexUtils.mainColor + target.getName() + VexUtils.secondaryColor + " has accepted your teleport request.");
                        target.sendMessage("Teleported to " + requester.getName());
                    }

                    plugin.getTeleportRequests().remove(request);
                } else {
                    target.sendMessage(VexUtils.secondaryColor + "No active teleport requests.");
                }
            }
        } else if (command.getName().equalsIgnoreCase("tpadeny")) {
            if (sender instanceof Player && sender.hasPermission("tpa.deny")) {
                Player target = (Player) sender;
                TeleportRequest request = getValidTeleportRequest(target);

                if (request != null) {
                    Player requester = plugin.getServer().getPlayer(request.getRequester());

                    if (requester != null) {
                        requester.sendMessage(VexUtils.mainColor + target.getName() + VexUtils.secondaryColor + " has denied your teleport request.");
                    }

                    plugin.getTeleportRequests().remove(request);
                } else {
                    target.sendMessage(VexUtils.secondaryColor + "No active teleport requests.");
                }
            }
        } else if (command.getName().equalsIgnoreCase("tpacancel")) {
            if (sender instanceof Player && sender.hasPermission("tpa.cancel")) {
                Player requester = (Player) sender;
                TeleportRequest request = getValidTeleportRequest(requester);

                if (request != null) {
                    Player target = plugin.getServer().getPlayer(request.getTarget());

                    if (target != null) {
                        target.sendMessage(VexUtils.mainColor + requester.getName() + VexUtils.secondaryColor +  " has canceled the teleport request.");
                    }

                    plugin.getTeleportRequests().remove(request);
                } else {
                    requester.sendMessage(VexUtils.secondaryColor + "No active teleport requests.");
                }
            }
        }
        return true;
    }
    
    private TeleportRequest getValidTeleportRequest(Player player) {
        for (Map.Entry<TeleportRequest, Long> entry : plugin.getTeleportRequests().entrySet()) {
            TeleportRequest request = entry.getKey();
            if (request.getTarget().equals(player.getUniqueId()) && !request.isExpired()) {
                return request;
            }
        }
        return null;
    }
}
