package de.jonasfrey.jfchatcolors;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;

public class FriendlyFireCommand extends CommandBase {
    
    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] params) throws CommandException {
        
        if (!(sender instanceof EntityPlayerMP)) {
            sender.sendMessage(new TextComponentString("The FriendlyFire command cannot be executed by a non-player."));
            return;
        }
        EntityPlayerMP player = (EntityPlayerMP) sender;
        ScorePlayerTeam team = player.getWorldScoreboard().getPlayersTeam(player.getName());
        
        if (team == null) {
            player.sendMessage(new TextComponentString("You are not in any team."));
            return;
        }
        
        if (params.length == 0) {
            // Query FF
            boolean ff = team.getAllowFriendlyFire();
            TextComponentString message = new TextComponentString("Friendly Fire for team " + team.getName() +
                    " is currently " + (team.getAllowFriendlyFire() ? "§cenabled" : "§2disabled") + "§r.");
            player.sendMessage(message);
        } else if (params.length == 1 && params[0].equalsIgnoreCase("toggle")) {
            // Toggle FF
            JFChatColors.logger.info("Toggling friendlyfire for team " + team.getName());
    
            team.setAllowFriendlyFire(!team.getAllowFriendlyFire());
    
            TextComponentString message = new TextComponentString("Friendly Fire for team " + team.getName() +
                    " is now " + (team.getAllowFriendlyFire() ? "§cenabled" : "§2disabled") + "§r.");
            player.sendMessage(message);
        } else {
            player.sendMessage(new TextComponentString("Usage: " + getUsage(sender)));
        }
    }
    
    @Override
    public String getName() {
        return "friendlyfire";
    }
    
    @Override
    public String getUsage(ICommandSender sender) {
        return "/" + getName() + " [toggle]";
    }
    
    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }
}
