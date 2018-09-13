package main.java.com.intellectualcrafters.plot.commands;

import com.intellectualcrafters.plot.object.PlotPlayer;
import main.java.com.plotsquared.general.commands.CommandDeclaration;
import me.totalfreedom.plotsquared.Service;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

@CommandDeclaration(
        command = "weanywhere",
        permission = "plots.worldedit.bypass",
        description = "Force bypass of WorldEdit",
        aliases = {"wea"},
        usage = "/plot weanywhere",
        requiredType = RequiredType.NONE,
        category = CommandCategory.ADMINISTRATION)
@Deprecated
public class WE_Anywhere extends SubCommand
{

    @Override
    public boolean onCommand(PlotPlayer player, String[] arguments) {
        if (!Service.isSuperAdmin(Bukkit.getPlayer(player.toString())))
        {
            player.sendMessage(ChatColor.RED + "You do not have permission to toggle this");
            return false;
        }
        MainCommand.getInstance().toggle.worldedit(this, player, new String[0], null, null);
        return true;
    }

}
