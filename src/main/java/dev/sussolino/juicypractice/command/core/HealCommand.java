package dev.sussolino.juicypractice.command.core;

import dev.sussolino.juicyapi.reflection.abstracts.PlayerCommand;
import dev.sussolino.juicypractice.utils.config.ConfigUtils;
import dev.sussolino.juicyapi.reflection.annotations.AntiSocial;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@AntiSocial
public class HealCommand extends PlayerCommand {

    @Override
    protected void execute(Player p, String[] args) {
        if (args.length == 0) {
            p.setHealth(p.getMaxHealth());
            p.setFoodLevel(20);
            //TODO HEAL MESSAGE
        }
        else if (args.length == 1) {
            Player t = Bukkit.getPlayerExact(args[0]);

            if (t == null) {
                p.sendMessage(ConfigUtils.ERROR_OFFLINE.getString());
                return;
            }
            t.setHealth(t.getMaxHealth());
            t.setFoodLevel(20);
            //TODO HEAL MESSAGE
        }
    }
}
