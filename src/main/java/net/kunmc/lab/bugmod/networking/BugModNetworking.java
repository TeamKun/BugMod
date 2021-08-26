package net.kunmc.lab.bugmod.networking;

import net.kunmc.lab.bugmod.BugMod;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.util.Identifier;

public class BugModNetworking {
    public static Identifier identifierFactory(String name) {
        switch (name) {
            case GameManager.redScreenName:
                return new Identifier(BugMod.MODID, GameManager.redScreenName);
            case GameManager.garbledCharName:
                return new Identifier(BugMod.MODID, GameManager.garbledCharName);
            case GameManager.breakScreenName:
                return new Identifier(BugMod.MODID, GameManager.breakScreenName);
            case GameManager.breakSkinName:
                return new Identifier(BugMod.MODID, GameManager.breakSkinName);
            case GameManager.breakTextureName:
                return new Identifier(BugMod.MODID, GameManager.breakTextureName);
            case GameManager.helpSoundName:
                return new Identifier(BugMod.MODID, GameManager.helpSoundName);
            case GameManager.bugRunName:
                return new Identifier(BugMod.MODID, GameManager.bugRunName);
            case "ALL":
                return new Identifier(BugMod.MODID, "ALL");
        }
        return null;
    }
}
