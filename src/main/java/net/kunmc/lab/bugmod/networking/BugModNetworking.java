package net.kunmc.lab.bugmod.networking;

import net.kunmc.lab.bugmod.BugMod;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.util.Identifier;
import org.lwjgl.system.CallbackI;

public class BugModNetworking {
    // ゲームモード転送用
    public static final String gameMode = "gamemode";
    // 全レベル転送用(サーバ・クライアントの同期用)
    public static final String allLevel = "all";
    // 通常のレベル転送用(レベルがダウンすることを考慮しない)
    public static final String level = "level";
    // 強制的にレベル更新(設定更新時など意図的にレベルを下げる時用)
    public static final String forceLevel = "forcelevel";
    public static Identifier identifierFactory(String name) {
        switch (name) {
            case gameMode:
                return new Identifier(BugMod.MODID, gameMode);
            case allLevel:
                return new Identifier(BugMod.MODID, allLevel);
            case level:
                return new Identifier(BugMod.MODID, level);
            case forceLevel:
                return new Identifier(BugMod.MODID, forceLevel);
        }
        return null;
    }
}
