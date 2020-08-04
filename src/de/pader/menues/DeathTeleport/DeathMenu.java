package de.pader.menues.DeathTeleport;

import de.pader.menues.MainMenu;
import de.pader.utils.Menu;
import de.pader.utils.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class DeathMenu extends Menu {

    private final int eventIndex = 4;

    public DeathMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Dein letzter Tod";
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        switch (e.getCurrentItem().getType()){
            case PLAYER_HEAD:
                Player p = playerMenuUtility.getOwner();
                Location target = playerMenuUtility.getOwnerLocation();
                p.teleport(target);
                playerMenuUtility.setOwnerLocation(null);
                e.getWhoClicked().sendMessage(ChatColor.GREEN + "Du hast dich zu deinem letzten Tod teleportier!");
                break;
            case BARRIER:
                new MainMenu(playerMenuUtility).open();
                break;
        }
    }

    @Override
    public void setMenuItems() {

        setCustomMenuBorder();

        if (playerMenuUtility.getOwnerLocation() == null) {
            ItemStack noDeath = new ItemStack(Material.BARRIER, 1);
            ItemMeta noDeathMeta = noDeath.getItemMeta();
            noDeathMeta.setDisplayName(ChatColor.RED + "Kein Tod aufgezeichnet");
            noDeath.setItemMeta(noDeathMeta);
            inventory.setItem(eventIndex,noDeath);

        }
        else{
            int x = (int)playerMenuUtility.getOwnerLocation().getX();
            int y = (int)playerMenuUtility.getOwnerLocation().getY();
            int z = (int)playerMenuUtility.getOwnerLocation().getZ();

            ItemStack playerItem = new ItemStack(Material.PLAYER_HEAD, 1);
            ItemMeta playerMeta = playerItem.getItemMeta();
            playerMeta.setDisplayName(ChatColor.RED + "Letzter Tod");

            ArrayList<String> itemLore = new ArrayList<>();
            itemLore.add("World: " + playerMenuUtility.getOwnerLocation().getWorld().getName());
            itemLore.add("Koordinaten: " + x + " " + y + " " + z);
            playerMeta.setLore(itemLore);

            playerItem.setItemMeta(playerMeta);

            inventory.setItem(eventIndex,playerItem);
        }
    }

    private void setCustomMenuBorder() {
        for (int i = 0; i < getSlots(); i++)
        {
            if (i == eventIndex) { continue; }
            inventory.setItem(i, super.FILLER_GLASS);
        }

    }
}
