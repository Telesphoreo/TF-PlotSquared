package main.java.com.intellectualcrafters.plot.commands;

import com.intellectualcrafters.plot.PS;
import com.intellectualcrafters.plot.config.C;
import com.intellectualcrafters.plot.object.Location;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotArea;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.intellectualcrafters.plot.util.MainUtil;
import main.java.com.plotsquared.general.commands.CommandDeclaration;
import me.totalfreedom.plotsquared.Service;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandDeclaration(
        usage = "/plot move <X;Z>",
        command = "move",
        description = "Move a plot",
        aliases = {"debugmove"},
        permission = "plots.move",
        category = CommandCategory.CLAIMING,
        requiredType = RequiredType.NONE)
public class Move extends SubCommand
{

    @Override
    public boolean onCommand(final PlotPlayer player, String[] args) {
        Player bukkitPlayer = Bukkit.getPlayer(player.toString());
        Location loc = player.getLocation();
        Plot plot1 = loc.getPlotAbs();
        if (plot1 == null) {
            return !MainUtil.sendMessage(player, C.NOT_IN_PLOT);
        }
        if (!plot1.isOwner(player.getUUID())) {
            MainUtil.sendMessage(player, C.NO_PLOT_PERMS);
            return false;
        }
        boolean override = false;
        if (args.length == 2 && args[1].equalsIgnoreCase("-f")) {
            if (!Service.isSuperAdmin(bukkitPlayer))
            {
                override = false;
                return true;
            }
            args = new String[]{ args[0] };
            override = true;
        }
        if (args.length != 1) {
            C.COMMAND_SYNTAX.send(player, getUsage());
            return false;
        }
        PlotArea area = PS.get().getPlotAreaByString(args[0]);
        Plot plot2;
        if (area == null) {
            plot2 = MainUtil.getPlotFromString(player, args[0], true);
            if (plot2 == null) {
                return false;
            }
        } else {
            plot2 = area.getPlotAbs(plot1.getId());
        }
        if (plot1.equals(plot2)) {
            MainUtil.sendMessage(player, C.NOT_VALID_PLOT_ID);
            MainUtil.sendMessage(player, C.COMMAND_SYNTAX, "/plot copy <X;Z>");
            return false;
        }
        if (!plot1.getArea().isCompatible(plot2.getArea()) && (!override || !Service.isSuperAdmin(bukkitPlayer))) {
            C.PLOTWORLD_INCOMPATIBLE.send(player);
            return false;
        }
        if (plot1.move(plot2, new Runnable() {
            @Override
            public void run() {
                MainUtil.sendMessage(player, C.MOVE_SUCCESS);
            }
        }, false)) {
            return true;
        } else {
            MainUtil.sendMessage(player, C.REQUIRES_UNOWNED);
            return false;
        }
    }

}
