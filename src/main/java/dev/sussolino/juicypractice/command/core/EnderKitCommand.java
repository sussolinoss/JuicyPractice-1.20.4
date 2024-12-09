package dev.sussolino.juicypractice.command.core;

import dev.sussolino.juicyapi.reflection.abstracts.PlayerCommand;
import dev.sussolino.juicyapi.reflection.annotations.AntiSocial;
import org.bukkit.entity.Player;

@AntiSocial

public class EnderKitCommand extends PlayerCommand {

    //kit save/delete/view [nome]

    @Override
    protected void execute(Player p, String[] args) {
        if (args.length < 3) {
            //TODO KIT_HELP MESSAGE
            return;
        }
        if (args.length == 3) {

        }
    }
}
