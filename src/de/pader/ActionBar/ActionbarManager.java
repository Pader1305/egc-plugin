package de.pader.ActionBar;

import de.pader.main.Main;
import net.minecraft.server.v1_16_R1.IChatBaseComponent;
import net.minecraft.server.v1_16_R1.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class ActionbarManager {

    private Main plugin;

    public ActionbarManager(Main plugin){
        this.plugin = plugin;
    }

    private void sendActionbar(final Player player, final String message){

        IChatBaseComponent iChatBaseComponent = IChatBaseComponent.ChatSerializer
                .a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&',message) + "\"}");

        PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.ACTIONBAR, iChatBaseComponent);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);

    }

    public void updateTimeBar(){

        for (UUID uuid : plugin.availablePlayers.keySet())
        {
            long i = plugin.availablePlayers.get(uuid);

            sendActionbar(Bukkit.getPlayer(uuid), IntToTimestamp(i));

            long iNew = i + 1;
            plugin.availablePlayers.put(uuid, iNew);

        }

    }

    private String IntToTimestamp(long timeInSeconds){

        int stunden = (int)timeInSeconds / 3600;
        int minuten = (int)(timeInSeconds % 3600 ) / 60;
        int sekunden = (int)timeInSeconds % 60;

        return "§6§lSpielzeit: §3" + String.format("%02d:%02d:%02d",stunden, minuten, sekunden);

    }
}
