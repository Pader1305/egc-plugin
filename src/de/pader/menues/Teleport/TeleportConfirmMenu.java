package de.pader.menues.Teleport;

import de.pader.utils.Menu;
import de.pader.utils.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class TeleportConfirmMenu extends Menu {

    public TeleportConfirmMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Teleport zu: " + playerMenuUtility.getTargetPlayer().getDisplayName();
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        switch (e.getCurrentItem().getType()){
            case EMERALD:
                e.getWhoClicked().closeInventory();

                Location targetLocation = playerMenuUtility.getTargetPlayer().getLocation();
                playerMenuUtility.getOwner().teleport(targetLocation);
                e.getWhoClicked().sendMessage(ChatColor.GREEN + "Du hast dich zum Spieler <" + playerMenuUtility.getTargetPlayer().getDisplayName() +"> teleportiert!");
                break;
            case BARRIER:
                new TeleportMenu(playerMenuUtility).open();
                break;
        }

    }

    @Override
    public void setMenuItems() {
        ItemStack yes = new ItemStack(Material.EMERALD, 1);
        ItemMeta yes_meta = yes.getItemMeta();
        yes_meta.setDisplayName(ChatColor.GREEN + "Ja");
        ArrayList<String> yes_lore = new ArrayList<>();
        yes_lore.add(ChatColor.AQUA + "Möchtest du dich zu");
        yes_lore.add(ChatColor.AQUA + "diesem Spieler teleportieren?");
        yes_meta.setLore(yes_lore);
        yes.setItemMeta(yes_meta);
        ItemStack no = new ItemStack(Material.BARRIER, 1);
        ItemMeta no_meta = no.getItemMeta();
        no_meta.setDisplayName(ChatColor.DARK_RED + "Nein");
        no.setItemMeta(no_meta);

        inventory.setItem(3, yes);
        inventory.setItem(5, no);

        setFillerGlass();

    }
}
