package dev.sussolino.juicypractice.utils.config;

import dev.sussolino.juicypractice.Juicy;
import dev.sussolino.juicyapi.color.ColorUtils;
import dev.sussolino.juicypractice.Bnyx;

import java.util.List;

public enum ConfigUtils implements Bnyx {

    PREFIX,

    SCOREBOARD_TITLE,
    SCOREBOARD_LINES,

    TABLIST_HEADER,
    TABLIST_FOOTER,

    ERROR_COMMAND,
    ERROR_OFFLINE,

    SPAWN_SET,
    SPAWN_TP,

    SPAWN_LOCATION_X,
    SPAWN_LOCATION_Y,
    SPAWN_LOCATION_Z,
    SPAWN_LOCATION_PITCH,
    SPAWN_LOCATION_YAW,
    SPAWN_LOCATION_WORLD,

    SPAWN_MESSAGES_ERROR,
    SPAWN_MESSAGES_SET,

    STATS_ERROR,
    STATS_HELP,
    STATS_SET,
    STATS_RESET,
    STATS_INCREMENT,

    INVENTORY,

    DUELS_TITLE,
    DUELS_SCOREBOARD_TITLE,
    DUELS_SCOREBOARD_LINES,

    PARTY_CREATE,
    PARTY_JOIN,
    PARTY_DISBAND,
    PARTY_INVITE,
    PARTY_INVITED,
    PARTY_LEAVE,

    DUELS_TITLE_WINNER,
    DUELS_SUBTITLE_WINNER,

    DUELS_TITLE_LOSER,
    DUELS_SUBTITLE_LOSER,

    ZONES,

    TRAIN_ZOMBIE_NAME,
    TRAIN_ZOMBIE_MESSAGES_SPAWN,

    TRAIN_TOTEM_DURATION,

    RTP_WORLD,
    RTP_COOLDOWN,

    PARTY_CHAT_FORMAT,
    PARTY_CHAT_STATE,

    PARTY_HELP,

    PARTY_ERROR_PARTY,
    PARTY_ERROR_STATE,
    PARTY_ERROR_REQUIRED,
    PARTY_ERROR_PERMISSIONS;


    public final String path;

    ConfigUtils() {
        this.path = name().toLowerCase().replace('_', '.');
    }

    public static void setDouble(String path, double doppio) {
        Juicy.SETTINGS.getConfig().set(path, doppio);
        Juicy.SETTINGS.reload();
    }

    public static void setString(String path, String name) {
        Juicy.SETTINGS.getConfig().set(path, name);
        Juicy.SETTINGS.reload();
    }

    public String getString() {
        if (LANGUAGE_YML.getConfig().getString(path) == null) return "null";
        return ColorUtils.color(LANGUAGE_YML.getConfig().getString(path));
    }

    public List<String> getStringList() {
        return LANGUAGE_YML.getConfig().getStringList(path);
    }
    public int getInt() {
        return LANGUAGE_YML.getConfig().getInt(path);
    }
    public double getDouble() {
        return LANGUAGE_YML.getConfig().getDouble(path);
    }
    public boolean getBoolean() {
        return LANGUAGE_YML.getConfig().getBoolean(path);
    }
}
