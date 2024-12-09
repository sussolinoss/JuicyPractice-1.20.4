package dev.sussolino.juicypractice;

import dev.sussolino.juicyapi.JuicyAPI;
import dev.sussolino.juicypractice.config.SettingsYml;
import net.luckperms.api.LuckPerms;

public interface Bnyx {

    Juicy INSTANCE = Juicy.INSTANCE;
    SettingsYml LANGUAGE_YML = Juicy.SETTINGS;
    LuckPerms LUCKPERMS = JuicyAPI.LUCKPERMS;
}
