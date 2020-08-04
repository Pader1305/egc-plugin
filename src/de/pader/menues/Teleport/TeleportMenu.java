package de.pader.menues.Teleport;

import de.pader.main.Main;
import de.pader.menues.Teleport.TeleportConfirmMenu;
import de.pader.utils.PaginatedMenu;
import de.pader.utils.PlayerMenuUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.UUID;

import static org.bukkit.Bukkit.getServer;

public class TeleportMenu extends PaginatedMenu {


    public TeleportMenu(PlayerMenuUtility playerMenuUtility){
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Wähle einen Ziel-Spieler aus!";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        ArrayList<Player> players =  new ArrayList<Player>(getServer().getOnlinePlayers());

        if (e.getCurrentItem().getType().equals(Material.PLAYER_HEAD))
        {
            PlayerMenuUtility playerMenuUtility = Main.getPlayerMenuUtility(p);
            playerMenuUtility.setTargetPlayer(Bukkit.getPlayer(UUID.fromString(e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getPlugin(), "uuid"), PersistentDataType.STRING))));

            new TeleportConfirmMenu(playerMenuUtility).open();
        }
        else if (e.getCurrentItem().getType().equals(Material.BARRIER))
        {
            p.closeInventory();
        }
        else if(e.getCurrentItem().getType().equals(Material.DARK_OAK_BUTTON)){
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Left")){
                if (page == 0){
                    p.sendMessage(ChatColor.GRAY + "Du bist schon auf der ersten Seite.");
                }else{
                    page = page - 1;
                    super.open();
                }
            }else if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Right")){
                if (!((index + 1) >= players.size())){
                    page = page + 1;
                    super.open();
                }else{
                    p.sendMessage(ChatColor.GRAY + "Du bist schon auf der letzten Seite.");
                }
            }
        }
    }

    @Override
    public void setMenuItems() {
        addMenuBorder();
        ArrayList<Player> players = new ArrayList<Player>(getServer().getOnlinePlayers());

        removeOwnPlayer(players);


        if (players != null && !players.isEmpty())
        {
            for(int i = 0; i < getMaxItemsPerPage(); i++)
            {
                index = getMaxItemsPerPage() * page + i;
                if(index >= players.size()) break;
                if (players.get(index) != null)
                {
                    ItemStack playerItem = new ItemStack(Material.PLAYER_HEAD, 1);
                    ItemMeta playerMeta = playerItem.getItemMeta();
                    playerMeta.setDisplayName(ChatColor.RED + players.get(index).getDisplayName());
                    playerMeta.getPersistentDataContainer().set(new NamespacedKey(Main.getPlugin(), "uuid"), PersistentDataType.STRING, players.get(index).getUniqueId().toString());
                    playerItem.setItemMeta(playerMeta);

                    inventory.addItem(playerItem);

                }
            }

        }
    }

    private void removeOwnPlayer(ArrayList<Player> players)
    {
        UUID uuid;
        for (Player p : players)
        {
            uuid = p.getUniqueId();
            if (uuid == playerMenuUtility.getOwner().getUniqueId())
            {
                players.remove(p);
                break;
            }
        }
    }
}
