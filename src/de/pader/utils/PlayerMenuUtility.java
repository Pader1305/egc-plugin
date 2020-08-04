/*

Credits goes to kodysimpson
https://gitlab.com/kodysimpson/menu-manager-spigot

 */

package de.pader.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerMenuUtility {
    private Player owner;
    private Player target;
    private Location ownerLocation;
    private Location targetLocation;


    public PlayerMenuUtility(Player p) {
        this.owner = p;
    }

    public Player getOwner() {
        return owner;
    }

    public Player getTargetPlayer()
    {
        return target;
    }

    public void setTargetPlayer(Player targetPlayer)
    {
        this.target = targetPlayer;
    }


    public Location getOwnerLocation() {
        return ownerLocation;
    }

    public void setOwnerLocation(Location ownerLocation) {
        this.ownerLocation = ownerLocation;
    }

    public Location getTargetLocation() {
        return targetLocation;
    }

    public void setTargetLocation(Location targetLocation) {
        this.targetLocation = targetLocation;
    }
}
