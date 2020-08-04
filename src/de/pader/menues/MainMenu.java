package de.pader.menues;

import de.pader.menues.DeathTeleport.DeathMenu;
import de.pader.menues.Teleport.TeleportMenu;
import de.pader.utils.Menu;
import de.pader.utils.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MainMenu extends Menu {

    public MainMenu(PlayerMenuUtility playerMenuUtility)
    {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "EGC-Hauptmenü";
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        switch(e.getCurrentItem().getType()) {
            case PLAYER_HEAD:
                new TeleportMenu(playerMenuUtility).open();
                break;
            case SKELETON_SKULL:
                new DeathMenu(playerMenuUtility).open();
                break;
        }
    }

    @Override
    public void setMenuItems() {
        ItemStack teleport = new ItemStack(Material.PLAYER_HEAD,1);
        ItemMeta teleportMeta = teleport.getItemMeta();
        teleportMeta.setDisplayName("Teleportieren");
        teleport.setItemMeta(teleportMeta);
        inventory.setItem(1,teleport);

        ItemStack death = new ItemStack(Material.SKELETON_SKULL,1);
        ItemMeta deathMeta = death.getItemMeta();
        deathMeta.setDisplayName("Letzter Tod");
        death.setItemMeta(deathMeta);
        inventory.setItem(3, death);

        setFillerGlass();
    }
}
