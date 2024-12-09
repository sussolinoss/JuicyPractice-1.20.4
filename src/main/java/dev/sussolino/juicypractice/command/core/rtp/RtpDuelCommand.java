package dev.sussolino.juicypractice.command.core.rtp;

import dev.sussolino.juicyapi.reflection.abstracts.PlayerCommand;
import dev.sussolino.juicyapi.reflection.annotations.AntiSocial;
import dev.sussolino.juicypractice.utils.config.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@AntiSocial
public class RtpDuelCommand extends PlayerCommand {

    @Override
    protected void execute(Player p, String[] args) {
        if (args.length == 1) {
            Player t = Bukkit.getPlayer(args[0]);

            if (t == null) {
                t.sendMessage(ConfigUtils.ERROR_OFFLINE.getString());
                return;
            }

            Location loc = RtpCommand.randomTP();

            loc = new Location(loc.getWorld(), loc.getBlockX() + 10, loc.getBlockY(), loc.getBlockZ());

            t.teleport(loc);
        }
    }
}