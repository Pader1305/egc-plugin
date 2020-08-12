package de.pader.listener;

import de.pader.main.Main;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerJoinLeftListener implements Listener {

    Main plugin = Main.getPlugin();


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        UUID uuid = e.getPlayer().getUniqueId();
        int cooldownTime = plugin.getConfig().getInt(uuid+".Cooldown_Left");

        if (cooldownTime <= 0 ) return;
        else plugin.coolDownTime.put(uuid,cooldownTime);

    }

    @EventHandler
    public void onPlayerLeft(PlayerQuitEvent e)
    {
        UUID uuid = e.getPlayer().getUniqueId();
        if (plugin.coolDownTime.containsKey(uuid))
        {
            plugin.getConfig().set(uuid.toString() + ".Cooldown_Left" , plugin.coolDownTime.get(uuid));
            plugin.saveConfig();
        }
    }

}
