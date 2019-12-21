package de.jonasfrey.jfchatcolors;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.Arrays;

public class CollisionCommand extends CommandBase {
    
    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] params) throws CommandException {
        
        if (!(sender instanceof EntityPlayerMP)) {
            sender.sendMessage(new TextComponentString("The collision command cannot be executed by a non-player."));
            return;
        }
        EntityPlayerMP player = (EntityPlayerMP) sender;
        ScorePlayerTeam team = player.getWorldScoreboard().getPlayersTeam(player.getName());
        
        if (team == null) {
            player.sendMessage(new TextComponentString("You are not in any team."));
            return;
        }
        
        if (params.length == 0) {
            Team.CollisionRule rule = team.getCollisionRule();
            TextComponentString message = new TextComponentString("Collision rule for team " + team.getName() +
                    " is currently " + rule.name + ".");
            player.sendMessage(message);
        } else if (params.length == 2 && params[0].equalsIgnoreCase("set")) {
            String rule = params[1].trim();
            if (!(new ArrayList<>(Arrays.asList("always", "never", "pushOtherTeams", "pushOwnTeam"))).contains(rule)) {
                sender.sendMessage(new TextComponentString(rule + " is not a valid rule. Accepted values are " +
                        "always, never, pushOtherTeams, pushOwnTeam."));
            }
    
            JFChatColors.logger.info("Changing collision rule for team " + team.getName() + " to " + rule);
    
            Team.CollisionRule newRule = Team.CollisionRule.getByName(rule.toLowerCase());
            if (newRule == null) {
                player.sendMessage(new TextComponentString("Error setting rule " + rule));
                return;
            }
            team.setCollisionRule(newRule);
    
            TextComponentString message = new TextComponentString("Collision rule for team " + team.getName() +
                    " is now " + rule + ".");
            player.sendMessage(message);
        } else {
            player.sendMessage(new TextComponentString("Please specify the collision mode. Accepted values are" +
                    "always, never, pushOtherTeams, pushOwnTeam."));
            player.sendMessage(new TextComponentString("Usage: " + getUsage(sender)));
        }
    }
    
    @Override
    public String getName() {
        return "collision";
    }
    
    @Override
    public String getUsage(ICommandSender sender) {
        return "/" + getName() + " [set <rule>]";
    }
    
    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }
}
