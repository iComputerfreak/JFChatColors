package de.jonasfrey.jfchatcolors;

import net.minecraftforge.common.config.Config;

@Config(modid = JFChatColors.MODID)
public class JFConfiguration {

    @Config.Comment({
            "Whether to prefix all chat messages with the user's /scoreboard team"
    })
    public static boolean useTeamPrefix = true;
    
    @Config.Comment({
            "The format to use when adding the group prefix.",
            "Use %TEAM% as variable for the scoreboard team name."
    })
    public static String prefixFormat = "§8[%TEAM%§8]§r ";
    
    @Config.Comment({
            "Whether to modify the chat format using the format below."
    })
    public static boolean modifyChatFormat = true;
    
    @Config.Comment({
            "The format to use when modifying the chat format.",
            "Use %NAME% as variable for the scoreboard team name."
    })
    public static String chatFormat = "§7%NAME%§r: ";

}
