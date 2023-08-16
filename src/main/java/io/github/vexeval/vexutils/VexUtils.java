package io.github.vexeval.vexutils;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class VexUtils extends JavaPlugin implements Listener {

	private static VexUtils instance;
	
	private Map<TeleportRequest, Long> teleportRequests = new HashMap<>();

    public Map<TeleportRequest, Long> getTeleportRequests() {
        return teleportRequests;
    }
    
    public static final ChatColor mainColor = ChatColor.AQUA;
    public static final ChatColor secondaryColor = ChatColor.WHITE;
	
	@Override
    public void onEnable() {
		instance = this;
		
		// Register event listener
		this.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getPluginManager().registerEvents(new SignInteractionListener(), this);
        
        // Homes
        this.getCommand(HomeCommandExecutor.COMMAND_SAVE_HOME).setExecutor(new HomeCommandExecutor());
        this.getCommand(HomeCommandExecutor.COMMAND_USE_HOME).setExecutor(new HomeCommandExecutor());
        this.getCommand(HomeCommandExecutor.COMMAND_DELETE_HOME).setExecutor(new HomeCommandExecutor());
        
        // TPAs
        getCommand("tpa").setExecutor(new TeleportCommandExecutor(this));
        getCommand("tpaccept").setExecutor(new TeleportCommandExecutor(this));
        getCommand("tpadeny").setExecutor(new TeleportCommandExecutor(this));
        getCommand("tpacancel").setExecutor(new TeleportCommandExecutor(this));


    }
    
	public static VexUtils getInstance() {
        return instance;
    }
}
