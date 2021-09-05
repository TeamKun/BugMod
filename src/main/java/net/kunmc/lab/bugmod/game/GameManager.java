package net.kunmc.lab.bugmod.game;

import net.kunmc.lab.bugmod.BugMod;
import net.kunmc.lab.bugmod.networking.ServerNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.MessageType;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameManager {
    public static final String redScreenName = "redscreen";
    public static final String garbledCharName = "garbledchar";
    public static final String breakScreenName = "breakscreen";
    public static final String breakTextureName = "breaktexture";
    public static final String breakSkinName = "breakskin";

    public static final int redScreenMaxLevel = 16;
    public static final int garbledCharMaxLevel = 15;
    public static final int breakScreenMaxLevel = 30;
    public static final int breakTextureMaxLevel = 5;
    public static final int breakSkinMaxLevel = 5;

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

    // Gameの実行・停止状態の管理用
    public static GameMode runningMode = GameMode.MODE_NEUTRAL;
    public static boolean recoveryMode = true;


    public static void resetGame(){
        redScreenLevel = 0;
        garbledCharLevel = 0;
        breakScreenLevel = 0;
        breakTextureLevel = 0;
        breakSkinLevel = 0;
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

    public static String[] getAllBugName() {
        String[] name = {GameManager.redScreenName, GameManager.breakScreenName,
                GameManager.breakSkinName, GameManager.breakTextureName,
                GameManager.garbledCharName};
        return name;
    }

    public static int[] getAllBugLevel() {
        int[] level = {GameManager.redScreenLevel, GameManager.breakScreenLevel,
                GameManager.breakSkinLevel, GameManager.breakTextureLevel,
                GameManager.garbledCharLevel};
        return level;
    }

    public static String getBugRandom() {
        String[] bugName = getAllBugName();
        int[] bugLevel = getAllBugLevel();
        List<String> name = new ArrayList<String>();
        for (int i=0; i < getAllBugLevel().length; i++){
            if (bugLevel[i]>0){
                name.add(bugName[i] + " " + bugLevel[i]);
            }
        }
        if (name.size() == 0)
            return "";

        Random rand = new Random();
        return name.get(rand.nextInt(name.size()));
    }

    // サーバ側のレベル更新 & Clinetへのレベル転送
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
        }
    }

    public static void recoverLevel(String name, int level, String playerName){
        switch (name){
            case redScreenName:
                if (shouldDownLevel(level)) {
                    GameManager.redScreenLevel = level - 1;
                    ServerNetworking.sendRecoveryLevel(GameManager.redScreenName, level, playerName);
                }
                break;
            case garbledCharName:
                if (shouldDownLevel(level)) {
                    GameManager.garbledCharLevel = level - 1;
                    ServerNetworking.sendRecoveryLevel(GameManager.garbledCharName, level, playerName);
                }
                break;
            case breakScreenName:
                if (shouldDownLevel(level)) {
                    GameManager.breakScreenLevel = level - 1;
                    ServerNetworking.sendRecoveryLevel(GameManager.breakScreenName, level, playerName);
                }
                break;
            case breakTextureName:
                if (shouldDownLevel(level)) {
                    GameManager.breakTextureLevel = level - 1;
                    ServerNetworking.sendRecoveryLevel(GameManager.breakTextureName, level, playerName);
                }
                break;
            case breakSkinName:
                if (shouldDownLevel(level)) {
                    GameManager.breakSkinLevel = level - 1;
                    ServerNetworking.sendRecoveryLevel(GameManager.breakSkinName, level, playerName);
                }
                break;
        }
    }

    /**
     * 意図しない値のレベルに更新されないようにチェック
     * @param currentLevel
     * @param level
     * @return
     */
    private static boolean shouldUpdateLevel(int currentLevel, int level, int maxLevel){
        return currentLevel < level && maxLevel >= level && GameManager.runningMode == GameMode.MODE_START;
    }
    private static boolean shouldDownLevel(int level){
        return level > 0 && GameManager.runningMode == GameMode.MODE_START;
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