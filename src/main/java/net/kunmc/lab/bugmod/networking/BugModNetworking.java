package net.kunmc.lab.bugmod.networking;

import net.kunmc.lab.bugmod.BugMod;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.util.Identifier;

public class BugModNetworking {
    private static final String ID = "bugmod";
    public static Identifier identifierFactory(String name) {
        switch (name) {
            case GameManager.redScreenName:
                return new Identifier(ID, GameManager.redScreenName);
            case GameManager.garbledCharName:
                return new Identifier(ID, GameManager.garbledCharName);
            case GameManager.breakScreenName:
                return new Identifier(ID, GameManager.breakScreenName);
            case GameManager.breakSkinName:
                return new Identifier(ID, GameManager.breakSkinName);
            case GameManager.breakTextureName:
                return new Identifier(ID, GameManager.breakTextureName);
            case GameManager.helpSoundName:
                return new Identifier(ID, GameManager.helpSoundName);
            case GameManager.bugRunName:
                return new Identifier(ID, GameManager.bugRunName);
        }
        return null;
    }
}
