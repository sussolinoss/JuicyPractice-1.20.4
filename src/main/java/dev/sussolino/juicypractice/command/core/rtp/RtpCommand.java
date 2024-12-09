package dev.sussolino.juicypractice.command.core.rtp;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerPositionAndLook;
import dev.sussolino.juicyapi.reflection.abstracts.PlayerCommand;
import dev.sussolino.juicyapi.reflection.annotations.AntiSocial;
import dev.sussolino.juicypractice.utils.config.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Random;

@AntiSocial
public class RtpCommand extends PlayerCommand {

    @Override
    protected void execute(Player p, String[] args) {
        if (args.length == 0) {
           p.teleport(randomTP());
        }
    }

    protected static Location randomTP() {
        final World world = Bukkit.getWorld(ConfigUtils.RTP_WORLD.getString());

        final Random random = new Random();

        int minX = -30000;
        int maxX = 30000;
        int minZ = -30000;
        int maxZ = 30000;

        int x = random.nextInt(maxX - minX) + minX;
        int z = random.nextInt(maxZ - minZ) + minZ;

        assert world != null;

        int y = world.getHighestBlockYAt(x, z);

        return new Location(world, x, y, z, 0F, 0F);
    }
}