package de.pader.main;

import de.pader.commands.InventoryGUICommand;
import de.pader.listener.DeathListener;
import de.pader.listener.MenuListener;
import de.pader.listener.PlayerJoinLeftListener;
import de.pader.utils.PlayerMenuUtility;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Main extends JavaPlugin {


    private static Main plugin;
    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<Player, PlayerMenuUtility>();
    public HashMap<UUID, Integer> coolDownTime = new HashMap<UUID, Integer>();
    public final int masterTime = 300;

    @Override
    public void onEnable() {

        plugin = this;
        loadConfig();
        loadPlayerCooldown();

        getCommand("egc").setExecutor(new InventoryGUICommand());

        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new DeathListener(),this);
        getServer().getPluginManager().registerEvents(new PlayerJoinLeftListener(), this);

        coolDownRunnable();

    }

    private void loadPlayerCooldown()
    {
        for (String key : getConfig().getKeys(false)) {
            ConfigurationSection uuid = getConfig().getConfigurationSection(key);
            int time = uuid.getInt(".Cooldown_Left");

            System.out.println("uuid" + uuid.getName() + " time: " +  time);

            if (time <= 0 ) return;
            else coolDownTime.put(UUID.fromString(uuid.getName()), time);

        }
    }

    private void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public static PlayerMenuUtility getPlayerMenuUtility(Player p) {
        PlayerMenuUtility playerMenuUtility;
        if (!(playerMenuUtilityMap.containsKey(p))) { //See if the player has a playermenuutility "saved" for them

            //This player doesn't. Make one for them add add it to the hashmap
            playerMenuUtility = new PlayerMenuUtility(p);
            playerMenuUtilityMap.put(p, playerMenuUtility);

            return playerMenuUtility;
        } else {
            return playerMenuUtilityMap.get(p); //Return the object by using the provided player
        }
    }

    public static Main getPlugin()
    {
        return plugin;
    }

    public void coolDownRunnable(){
        new BukkitRunnable(){

            @Override
            public void run() {

                if (coolDownTime.isEmpty()) return;

                for (UUID uuid : coolDownTime.keySet())
                {
                    int timeleft = coolDownTime.get(uuid);

                    if (coolDownTime.get(uuid) <= 0)
                    {
                        plugin.getConfig().set(uuid.toString(), null);
                        plugin.saveConfig();
                        coolDownTime.remove(uuid);
                    }
                    else
                        coolDownTime.put(uuid, timeleft-1);
                }
            }
        }.runTaskTimer(this,0, 20);
    }


}
