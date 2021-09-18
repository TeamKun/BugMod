package net.kunmc.lab.bugmod.game;

import net.kunmc.lab.bugmod.BugMod;
import net.kunmc.lab.bugmod.networking.ServerNetworking;
import net.kunmc.lab.bugmod.texture.ReloadTexture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameManager {
    public static final String redScreenName = "redscreen";
    public static final String garbledCharName = "garbledchar";
    public static final String breakScreenName = "breakscreen";
    public static final String breakBlockName = "breakblock";
    public static final String breakSkinName = "breakskin";
    public static final String breakMobTextureName = "breakmobtexture";

    public static final int redScreenMaxLevel = 16;
    public static final int garbledCharMaxLevel = 15;
    public static final int breakScreenMaxLevel = 30;
    public static final int breakBlockMaxLevel = 5;
    public static final int breakSkinMaxLevel = 5;
    public static final int breakMobTextureMaxLevel = 6;

    public static double redScreenUpdateLevelProbability;
    public static double garbledCharUpdateLevelProbability;
    public static double breakScreenUpdateLevelProbability;
    public static double breakBlockUpdateLevelProbability;
    public static double breakSkinUpdateLevelProbability;
    public static double breakMobTextureUpdateLevelProbability;

    // 画面が赤くなる
    public static int redScreenLevel;
    // 文字化けする
    public static int garbledCharLevel;
    // 画面が壊れる
    public static int breakScreenLevel;
    // テクスチャがバグる(バグったブロックを発生させる)
    public static int breakBlockLevel;
    // プレイヤーのスキンがバグる
    public static int breakSkinLevel;
    // モブのスキンがバグる
    public static int breakMobTextureLevel;

    // Gameの実行・停止状態の管理用
    public static GameMode runningMode = GameMode.MODE_NEUTRAL;
    // 回復できるかどうか
    public static boolean canRecovery = true;
    // レベル更新時のメッセージ出力をするかどうか
    public static boolean showUpdateLevelMessage = true;

    public static Random rand = new Random();

    public static void resetGame() {
        redScreenLevel = 0;
        garbledCharLevel = 0;
        breakScreenLevel = 0;
        breakBlockLevel = 0;
        breakSkinLevel = 0;
        breakMobTextureLevel = 0;
        runningMode = GameMode.MODE_NEUTRAL;
    }

    public static void resetUpdateLevelProbability() {
        redScreenUpdateLevelProbability = 0.3;
        garbledCharUpdateLevelProbability = 1.0;
        breakScreenUpdateLevelProbability = 1.0;
        breakBlockUpdateLevelProbability = 0.01;
        breakSkinUpdateLevelProbability = 0.7;
        breakMobTextureUpdateLevelProbability = 0.5;
    }

    public static void controller(GameMode runningMode) {
        // モードを設定
        switch (runningMode) {
            case MODE_START:
            case MODE_NEUTRAL:
                GameManager.runningMode = runningMode;
                break;
        }
    }

    public static String[] getAllBugName() {
        String[] name = {GameManager.redScreenName, GameManager.breakScreenName,
                GameManager.breakSkinName, GameManager.breakBlockName,
                GameManager.garbledCharName, GameManager.breakMobTextureName};
        return name;
    }

    public static int[] getAllBugLevel() {
        int[] level = {GameManager.redScreenLevel, GameManager.breakScreenLevel,
                GameManager.breakSkinLevel, GameManager.breakBlockLevel,
                GameManager.garbledCharLevel, GameManager.breakMobTextureLevel};
        return level;
    }

    public static double[] getAllBugProbability() {
        double[] prob = {GameManager.redScreenUpdateLevelProbability,
                GameManager.breakScreenUpdateLevelProbability,
                GameManager.breakSkinUpdateLevelProbability,
                GameManager.breakBlockUpdateLevelProbability,
                GameManager.garbledCharUpdateLevelProbability,
                GameManager.breakMobTextureUpdateLevelProbability};
        return prob;
    }

    public static String getBugRandom() {
        String[] bugName = getAllBugName();
        int[] bugLevel = getAllBugLevel();
        List<String> name = new ArrayList<String>();
        for (int i = 0; i < getAllBugLevel().length; i++) {
            if (bugLevel[i] > 0) {
                name.add(bugName[i] + " " + bugLevel[i]);
            }
        }
        if (name.size() == 0)
            return "";

        Random rand = new Random();
        return name.get(rand.nextInt(name.size()));
    }

    // サーバ側のレベル更新 & Clientへのレベル転送
    public static void updateLevel(String name, int level, String playerName) {
        switch (name) {
            case redScreenName:
                if (GameManager.redScreenUpdateLevelProbability > rand.nextDouble()) {
                    if (shouldUpdateLevel(GameManager.redScreenLevel, level, GameManager.redScreenMaxLevel)) {
                        GameManager.redScreenLevel = level;
                        ServerNetworking.sendLevel(GameManager.redScreenName, level, playerName);
                    }
                }
                break;
            case garbledCharName:
                if (GameManager.garbledCharUpdateLevelProbability > rand.nextDouble()) {
                    if (shouldUpdateLevel(GameManager.garbledCharLevel, level, GameManager.garbledCharMaxLevel)) {
                        GameManager.garbledCharLevel = level;
                        ServerNetworking.sendLevel(GameManager.garbledCharName, level, playerName);
                    }
                }
                break;
            case breakScreenName:
                if (GameManager.breakScreenUpdateLevelProbability > rand.nextDouble()) {
                    if (shouldUpdateLevel(GameManager.breakScreenLevel, level, GameManager.breakScreenMaxLevel)) {
                        GameManager.breakScreenLevel = level;
                        ServerNetworking.sendLevel(GameManager.breakScreenName, level, playerName);
                    }
                }
                break;
            case breakBlockName:
                if (GameManager.breakBlockUpdateLevelProbability > rand.nextDouble()) {
                    if (shouldUpdateLevel(GameManager.breakBlockLevel, level, GameManager.breakBlockMaxLevel)) {
                        GameManager.breakBlockLevel = level;
                        ServerNetworking.sendLevel(GameManager.breakBlockName, level, playerName);
                    }
                }
                break;
            case breakSkinName:
                if (GameManager.breakSkinUpdateLevelProbability > rand.nextDouble()) {
                    if (shouldUpdateLevel(GameManager.breakSkinLevel, level, GameManager.breakSkinMaxLevel)) {
                        GameManager.breakSkinLevel = level;
                        ServerNetworking.sendLevel(GameManager.breakSkinName, level, playerName);
                    }
                    BugMod.minecraftServerInstance.getPlayerManager().getPlayerList().forEach(player -> {
                        ReloadTexture.reload(player);
                    });
                }
                break;
            case breakMobTextureName:
                if (GameManager.breakMobTextureUpdateLevelProbability > rand.nextDouble()) {
                    if (shouldUpdateLevel(GameManager.breakMobTextureLevel, level, GameManager.breakMobTextureMaxLevel)) {
                        GameManager.breakMobTextureLevel = level;
                        ServerNetworking.sendLevel(GameManager.breakMobTextureName, level, playerName);
                    }
                }
                break;
        }
    }

    public static void recoverLevel(String name, int level, String playerName) {
        switch (name) {
            case redScreenName:
                if (shouldDownLevel(level)) {
                    GameManager.redScreenLevel = level - 1;
                    ServerNetworking.sendRecoveryLevel(GameManager.redScreenName, GameManager.redScreenLevel, playerName);
                }
                break;
            case garbledCharName:
                if (shouldDownLevel(level)) {
                    GameManager.garbledCharLevel = level - 1;
                    ServerNetworking.sendRecoveryLevel(GameManager.garbledCharName, GameManager.garbledCharLevel, playerName);
                }
                break;
            case breakScreenName:
                if (shouldDownLevel(level)) {
                    GameManager.breakScreenLevel = level - 1;
                    ServerNetworking.sendRecoveryLevel(GameManager.breakScreenName, GameManager.breakScreenLevel, playerName);
                }
                break;
            case breakBlockName:
                if (shouldDownLevel(level)) {
                    GameManager.breakBlockLevel = level - 1;
                    ServerNetworking.sendRecoveryLevel(GameManager.breakBlockName, GameManager.breakBlockLevel, playerName);
                }
                break;
            case breakSkinName:
                if (shouldDownLevel(level)) {
                    GameManager.breakSkinLevel = level - 1;
                    ServerNetworking.sendRecoveryLevel(GameManager.breakSkinName, GameManager.breakSkinLevel, playerName);
                }
                break;
            case breakMobTextureName:
                if (shouldDownLevel(level)) {
                    GameManager.breakMobTextureLevel = level - 1;
                    ServerNetworking.sendRecoveryLevel(GameManager.breakMobTextureName, GameManager.breakMobTextureLevel, playerName);
                }
                break;
        }
    }

    /**
     * 意図しない値のレベルに更新されないようにチェック
     *
     * @param currentLevel
     * @param level
     * @return
     */
    private static boolean shouldUpdateLevel(int currentLevel, int level, int maxLevel) {
        return currentLevel < level && maxLevel >= level && GameManager.runningMode == GameMode.MODE_START;
    }

    private static boolean shouldDownLevel(int level) {
        return level > 0 && GameManager.runningMode == GameMode.MODE_START;
    }

    public enum GameMode {
        // 開始前状態
        MODE_NEUTRAL,
        // 開始状態
        MODE_START,
    }
}
