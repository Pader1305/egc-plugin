package de.pader.listener;

import de.pader.main.Main;
import de.pader.utils.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;


public class DeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e)
    {
        Entity ent = e.getEntity();
        Location deathLocation = ent.getLocation();
        EntityDamageEvent ede = ent.getLastDamageCause();
        DamageCause dc = ede.getCause();

        if (ent instanceof Player)
        {
            Player p = (Player)ent;
            if (dc == DamageCause.VOID)
            {
                p.sendMessage(ChatColor.RED + "Tod konnte nicht aufgezeichnet werden.");

            }
            if (dc == DamageCause.LAVA){
                p.sendMessage(ChatColor.RED + "Du bist in Lava gestorben. Dein Tod wurde nicht aufgezeichnet.");
                return;
            }

            PlayerMenuUtility playerMenuUtility = Main.getPlayerMenuUtility((Player)ent);
            playerMenuUtility.setOwnerLocation(deathLocation);
        }

    }

}
