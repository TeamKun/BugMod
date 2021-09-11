package net.kunmc.lab.bugmod.networking;

import net.kunmc.lab.bugmod.BugMod;
import net.minecraft.util.Identifier;

public class BugModNetworking {
    // ゲームモード転送用
    public static final String gameMode = "gamemode";
    // 全レベル転送用(サーバ・クライアントの同期用)
    public static final String all = "all";
    // 通常のレベル転送用(レベルがダウンすることを考慮しない)
    public static final String level = "level";
    // 強制的にレベル更新(設定更新時など管理者がレベルを下げる時に使用)
    public static final String forceLevel = "forcelevel";
    // レベル回復（減少）時に使用
    public static final String recoverLevel = "recoverlevel";

    public static Identifier identifierFactory(String name) {
        switch (name) {
            case gameMode:
                return new Identifier(BugMod.MODID, gameMode);
            case all:
                return new Identifier(BugMod.MODID, all);
            case level:
                return new Identifier(BugMod.MODID, level);
            case forceLevel:
                return new Identifier(BugMod.MODID, forceLevel);
            case recoverLevel:
                return new Identifier(BugMod.MODID, recoverLevel);
        }
        return null;
    }
}
