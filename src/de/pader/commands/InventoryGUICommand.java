package de.pader.commands;

import de.pader.main.Main;
import de.pader.menues.MainMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InventoryGUICommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player)
        {
            Player p = (Player)sender;

            new MainMenu(Main.getPlayerMenuUtility(p)).open();
        }
        else
            sender.sendMessage("Command nur als Spieler ausfuehrbar");


        return false;
    }
}
