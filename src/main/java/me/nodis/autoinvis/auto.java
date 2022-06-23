package main.java.me.nodis.autoinvis;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


import org.bukkit.event.EventHandler;



public class AutoInvis extends JavaPlugin implements Listener {

    private Logger log;
    PluginDescriptionFile pdffile = getDescription();
    
    public String latestversion;
    public String version = pdffile.getVersion();
    public String nombre = ChatColor.YELLOW+"["+ChatColor.BLUE+pdffile.getName()+ChatColor.YELLOW+"]";
    
    
    public String getVersion() {
    	return this.version;
   	}
    
    public String getLastestVersion() {
    	return this.latestversion;
    }

    public void onEnable() {
		Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+" AutoInvis esta activado!");
        log = this.getLogger();
        getServer().getPluginManager().registerEvents(this, this);
        getConfig().options().copyDefaults(true);
        getConfig().addDefault("potioneffect", true);
        saveConfig();
        updateChecker();
    }
    
    public void updateChecker(){		  
		  try {
			  HttpURLConnection con = (HttpURLConnection) new URL(
	                  "https://api.spigotmc.org/legacy/update.php?resource=100410").openConnection();
	          int timed_out = 1250;
	          con.setConnectTimeout(timed_out);
	          con.setReadTimeout(timed_out);
	          latestversion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
	          if (latestversion.length() <= 7) {
	        	  if(!version.equals(latestversion)){
	        		  Bukkit.getConsoleSender().sendMessage(ChatColor.RED +"Hay una nueva version disponible. "+ChatColor.YELLOW+
	        				  "("+ChatColor.GRAY+latestversion+ChatColor.YELLOW+")");
	        		  Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Puedes descargarlo en: "+ChatColor.WHITE+"https://www.spigotmc.org/resources/100410/");
	        		  
	        	  }      	  	        	  
	            }
	      } catch (Exception ex) {
	    	  Bukkit.getConsoleSender().sendMessage(nombre + ChatColor.RED +"Error al comprobar la actualización.");
	      }
	  }
    

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPermission("autoinvis.bypass")) {
            if (getConfig().getBoolean("potioneffect")) {
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000*20, 0));
            } else {
                for (Player pl : this.getServer().getOnlinePlayers()) {
                    if (!pl.hasPermission("autoinvis.bypass")) {
                        pl.hidePlayer(event.getPlayer());
                        event.getPlayer().hidePlayer(pl);
                    }
                }
            }
            log.info(event.getPlayer().getName() + " se ah dado la invisibilidad");
        } else {
            log.info(event.getPlayer().getName() + " tiene el permiso de anulación.");
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        if (!event.getPlayer().hasPermission("autoinvis.bypass")) {
            if (getConfig().getBoolean("potioneffect")) {
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000*20, 0));
            } else {
                for (Player pl : this.getServer().getOnlinePlayers()) {
                    if (!pl.hasPermission("autoinvis.bypass")) {
                        pl.hidePlayer(event.getPlayer());
                        event.getPlayer().hidePlayer(pl);
                    }                    
                }
            }
            log.info(event.getPlayer().getName() + " given se ah dado la invisibilidad");
        } else {
            log.info(event.getPlayer().getName() + " tiene el permiso de anulación.");
        }
    }

}
