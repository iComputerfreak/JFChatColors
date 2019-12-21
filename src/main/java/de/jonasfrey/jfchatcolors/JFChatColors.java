package de.jonasfrey.jfchatcolors;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Mod(modid = JFChatColors.MODID, name = JFChatColors.NAME, version = JFChatColors.VERSION)
public class JFChatColors {
    
    public static final String MODID = "jfchatcolors";
    public static final String NAME = "JFChatColors";
    public static final String VERSION = "1.1";
    static Logger logger;
    
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }
    
    @Mod.EventHandler
    public void init(FMLServerStartingEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        event.registerServerCommand(new FriendlyFireCommand());
        event.registerServerCommand(new CollisionCommand());
    }
    
    @SubscribeEvent
    public void onServerChat(ServerChatEvent e) {
        EntityPlayerMP player = e.getPlayer();
        Scoreboard scoreboard = player.getWorldScoreboard();
        ScorePlayerTeam team = scoreboard.getPlayersTeam(player.getName());
        // Modify the chat message
        ITextComponent component = e.getComponent();
        List<ITextComponent> siblings = component.getSiblings();
        String message = e.getMessage();
        
        String prefix = "";
        if (team != null && JFConfiguration.useTeamPrefix) {
            prefix += JFConfiguration.prefixFormat.replaceAll("%TEAM%", team.getColor() + team.getName());
        }
        if (JFConfiguration.modifyChatFormat) {
            prefix += JFConfiguration.chatFormat.replaceAll("%NAME%", player.getName());
        } else {
            // Use default format
            prefix += "<" + player.getDisplayNameString() + "> ";
        }
        
        ITextComponent newComponent = new TextComponentString(prefix + message);
        for (ITextComponent s : siblings) {
            newComponent.appendSibling(s);
        }
        
        e.setComponent(newComponent);
    }
    
}
