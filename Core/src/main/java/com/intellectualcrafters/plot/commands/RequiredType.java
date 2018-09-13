package main.java.com.intellectualcrafters.plot.commands;

import main.java.com.plotsquared.general.commands.CommandCaller;

public enum RequiredType {
    CONSOLE, PLAYER, NONE;

    public boolean allows(CommandCaller player) {
        switch (this) {
            case NONE:
                return true;
            default:
                return this == player.getSuperCaller();
        }
    }
}
