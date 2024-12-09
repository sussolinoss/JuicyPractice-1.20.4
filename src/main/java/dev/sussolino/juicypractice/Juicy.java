package dev.sussolino.juicypractice;

import com.github.retrooper.packetevents.PacketEvents;
import dev.sussolino.juicypractice.config.SettingsYml;
import dev.sussolino.juicypractice.file.*;
import dev.sussolino.juicypractice.party.PartyManager;
import dev.sussolino.juicypractice.playerdata.ProfileManager;
import dev.sussolino.juicypractice.utils.reflection.ReflectionUtil;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Juicy extends JavaPlugin {

    @Override
    public void onLoad(){
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().getSettings().reEncodeByDefault(false)
                .checkForUpdates(true)
                .bStats(false);
        PacketEvents.getAPI().load();
    }


    public static Juicy INSTANCE;

    public static SettingsYml SETTINGS;
    public static PartyManager PARTY_MANAGER;
    public static ProfileManager PROFILE_MANAGER;

    @Override
    public void onEnable() {
        INSTANCE = this;

        SettingsYml.init();
        KitsYml.init();
        StatsYml.init();
        DuelsYml.init();
        WarpsYml.init();
        EloYml.init();

        PARTY_MANAGER = new PartyManager();
        PROFILE_MANAGER = new ProfileManager();

        ReflectionUtil.register("dev.sussolino.juicypractice", this);

        PacketEvents.getAPI().init();
    }

    //TODO
    // CONFIG - ADD SOME SHITTY MESSAGES IN THE CONFIG-UTILS CLASS && CONFIG.YML
    // KIT STUFF - | EC KITS | CHEST WITH INFINITE ITEMS (JUST A CUSTOM GUI THAT WILL OPENS WHEN THE PLAYER INTERACT WITH A CERTAIN BLOCK) |
    // TRAIN STUFF - | TOTEM |
    // RTP - | QUEUE |

    //TODO (SERVER SIDE)
    // FFA NETHPOT && CPVP (Survival Zones)
    // SWORD ZONES (2+1)

    //TODO (EXTERNAL
    // STAFF STUFF - ANOTHER PLUGIN

    @Override
    public void onDisable() {
        INSTANCE = null;

        PacketEvents.getAPI().terminate();
    }
}