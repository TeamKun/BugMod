package net.kunmc.lab.bugmod.game;

import net.kunmc.lab.bugmod.BugMod;
import net.kunmc.lab.bugmod.networking.ServerNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.MessageType;
import net.minecraft.server.MinecraftServer;

public class GameManager {
    public static final String redScreenName = "redscreen";
    public static final String garbledCharName = "garbledchar";
    public static final String breakScreenName = "breakscreen";
    public static final String breakTextureName = "breaktexture";
    public static final String breakSkinName = "breakskin";
    public static final String helpSoundName = "helpsound";
    public static final String spiderSoundName = "spidersound";

    public static final int redScreenMaxLevel = 5;
    public static final int garbledCharMaxLevel = 5;
    public static final int breakScreenMaxLevel = 30;
    public static final int breakTextureMaxLevel = 5;
    public static final int breakSkinMaxLevel = 5;
    public static final int helpSoundMaxLevel = 5;
    public static final int spiderSoundMaxLevel = 2;

    // 画面が赤くなる
    public static int redScreenLevel;
    // 文字化けする
    public static int garbledCharLevel;
    // 画面が壊れる
    public static int breakScreenLevel;
    // テクスチャがバグる
    public static int breakTextureLevel;
    // スキンがバグる
    public static int breakSkinLevel;
    // 音がバグる
    public static int helpSoundLevel;
    // 虫が走る
    public static int spiderSoundLevel;

    // Gameの実行・停止状態の管理用
    public static GameMode runningMode = GameMode.MODE_NEUTRAL;
    public static boolean recoveryMode = true;


    public static void resetGame(){
        redScreenLevel = 0;
        garbledCharLevel = 0;
        breakScreenLevel = 0;
        breakTextureLevel = 0;
        breakSkinLevel = 0;
        helpSoundLevel = 0;
        spiderSoundLevel = 0;
        runningMode = GameMode.MODE_NEUTRAL;
        recoveryMode = true;
    }

    public static void controller(GameMode runningMode) {
        // モードを設定

        switch (runningMode) {
            case MODE_START:
            case MODE_NEUTRAL:
                GameManager.runningMode = runningMode;
                break;
            case MODE_PAUSE:
                if (GameManager.runningMode == GameMode.MODE_PAUSE) {
                    GameManager.runningMode = GameMode.MODE_START;
                } else {
                    GameManager.runningMode = GameMode.MODE_PAUSE;
                }
                break;
        }
    }

    // サーバ側のレベル更新
    public static void updateLevel(String name, int level, String playerName){
        switch (name){
            case redScreenName:
                if (shouldUpdateLevel(GameManager.redScreenLevel, level, GameManager.redScreenMaxLevel)){
                    GameManager.redScreenLevel = level;
                    ServerNetworking.sendLevel(GameManager.redScreenName, level, playerName);
                }
                break;
            case garbledCharName:
                if (shouldUpdateLevel(GameManager.garbledCharLevel, level, GameManager.garbledCharMaxLevel)){
                    GameManager.garbledCharLevel = level;
                    ServerNetworking.sendLevel(GameManager.garbledCharName, level, playerName);
                }
                break;
            case breakScreenName:
                if (shouldUpdateLevel(GameManager.breakScreenLevel, level, GameManager.breakScreenMaxLevel)){
                    GameManager.breakScreenLevel = level;
                    ServerNetworking.sendLevel(GameManager.breakScreenName, level, playerName);
                }
                break;
            case breakTextureName:
                if (shouldUpdateLevel(GameManager.breakTextureLevel, level, GameManager.breakTextureMaxLevel)){
                    GameManager.breakTextureLevel = level;
                    ServerNetworking.sendLevel(GameManager.breakTextureName, level, playerName);
                }
                break;
            case breakSkinName:
                if (shouldUpdateLevel(GameManager.breakSkinLevel, level, GameManager.breakSkinMaxLevel)){
                    GameManager.breakSkinLevel = level;
                    ServerNetworking.sendLevel(GameManager.breakSkinName, level, playerName);
                }
                break;
            case helpSoundName:
                if (shouldUpdateLevel(GameManager.helpSoundLevel, level, GameManager.helpSoundMaxLevel)){
                    GameManager.helpSoundLevel = level;
                    ServerNetworking.sendLevel(GameManager.helpSoundName, level, playerName);
                }
                break;
            case spiderSoundName:
                if (shouldUpdateLevel(GameManager.spiderSoundLevel, level, GameManager.spiderSoundMaxLevel)){
                    GameManager.spiderSoundLevel = level;
                    ServerNetworking.sendLevel(GameManager.spiderSoundName, level, playerName);
                }
                break;
        }
    }

    /**
     * 不正にレベルが下がるようなことにならないようにチェック
     * @param currentLevel
     * @param level
     * @return
     */
    private static boolean shouldUpdateLevel(int currentLevel, int level, int maxLevel){
        return currentLevel < level && maxLevel >= level;
    }

    public enum GameMode {
        // 開始前状態
        MODE_NEUTRAL,
        // 開始状態
        MODE_START,
        // 一時停止状態(resetGameせずに停止・再開ができる)
        MODE_PAUSE
    }
}