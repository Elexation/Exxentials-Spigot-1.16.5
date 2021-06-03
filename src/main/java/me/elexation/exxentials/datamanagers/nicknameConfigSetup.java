package me.elexation.exxentials.datamanagers;

public class nicknameConfigSetup {

    public static void setup() {
        nicknameFile.setup();
        nicknameFile.getConfig().addDefault("players", null);
        nicknameFile.getConfig().options().copyDefaults(true);
        nicknameFile.save();
    }
}
