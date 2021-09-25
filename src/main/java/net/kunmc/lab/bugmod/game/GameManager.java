package net.kunmc.lab.bugmod.game;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kunmc.lab.bugmod.BugMod;
import net.kunmc.lab.bugmod.networking.BugModNetworking;
import net.kunmc.lab.bugmod.networking.ServerNetworking;
import net.kunmc.lab.bugmod.texture.ReloadTexture;
import net.minecraft.network.PacketByteBuf;
import org.lwjgl.system.CallbackI;

import java.util.*;

public class GameManager {
    public static final String redScreenName = "redscreen";
    public static final String garbledCharName = "garbledchar";
    public static final String breakScreenName = "breakscreen";
    public static final String breakBlockName = "breakblock";
    public static final String breakSkinName = "breakskin";
    public static final String breakMobTextureName = "breakmobtexture";

    // 共通設定取得を便利にするための変数
    public static final String commonPlayerName = "bugModCommonPlayerSetting";

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
        PlayerGameManager.playersBugLevel.clear();
        runningMode = GameMode.MODE_NEUTRAL;
    }

    public static void resetUpdateLevelProbability() {
        redScreenUpdateLevelProbability = 1.0;
        garbledCharUpdateLevelProbability = 1.0;
        breakScreenUpdateLevelProbability = 1.0;
        breakBlockUpdateLevelProbability = 0.01;
        breakSkinUpdateLevelProbability = 1.0;
        breakMobTextureUpdateLevelProbability = 1.0;
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

    public static int[] getAllBugLevel(String playerName) {
        int[] level = {getPlayerBugLevel(playerName, GameManager.redScreenName),
                getPlayerBugLevel(playerName, GameManager.breakScreenName),
                getPlayerBugLevel(playerName, GameManager.breakSkinName),
                getPlayerBugLevel(GameManager.commonPlayerName, GameManager.breakBlockName),
                getPlayerBugLevel(playerName, GameManager.garbledCharName),
                getPlayerBugLevel(playerName, GameManager.breakMobTextureName)};
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

    public static String getBugRandom(String playerName) {
        String[] bugName = getAllBugName();
        int[] bugLevel = getAllBugLevel(playerName);
        List<String> name = new ArrayList();
        for (int i = 0; i < getAllBugLevel(playerName).length; i++) {
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
    public static void updateLevelDispatcher(String bugName, int level, String playerName) {
        int currentLevel = 0;
        if (PlayerGameManager.playersBugLevel.get(playerName).get(bugName) != null) {
            currentLevel = PlayerGameManager.playersBugLevel.get(playerName).get(bugName);
        }

        int maxLevel = getBugMaxLevel(bugName);
        double updateLevelProbability = getUpdateLevelProbability(bugName);
        if (updateLevelProbability > rand.nextDouble() && shouldUpdateLevel(currentLevel, level, maxLevel)) {
            // 全員でレベルが共通のバグ
            if (isCommonLevelBug(bugName)) {
                updateAllPlayerLevel(bugName, level);
            } else {
                updateLevel(playerName, bugName, level);
            }
            ServerNetworking.sendLevel(bugName, level, playerName);
        }
    }

    public static void recoverLevel(String bugName, int level, String playerName) {
        if (shouldDownLevel(level)) {
            if (isCommonLevelBug(bugName)) {
                updateAllPlayerLevel(bugName, level-1);
            } else {
                updateLevel(playerName, bugName, level-1);
            }
            ServerNetworking.sendRecoveryLevel(bugName, level-1, playerName);
        }
    }

    public static boolean isCommonLevelBug(String bugName){
        return bugName.equals(GameManager.breakBlockName) ? true : false;
    }

    public static int getPlayerBugLevel(String playerName, String bugName){
        if (PlayerGameManager.playersBugLevel.get(playerName) == null) {
            PlayerGameManager.playersBugLevel.put(playerName, new HashMap<>());
        }
        if (PlayerGameManager.playersBugLevel.get(playerName).get(bugName) == null) {
            PlayerGameManager.playersBugLevel.get(playerName).put(bugName, 0);
        }
        return PlayerGameManager.playersBugLevel.get(playerName).get(bugName);
    }

    private static int getBugMaxLevel (String bugName) {
        switch (bugName) {
            case redScreenName:
                return GameManager.redScreenMaxLevel;
            case garbledCharName:
                return GameManager.garbledCharMaxLevel;
            case breakScreenName:
                return GameManager.breakScreenMaxLevel;
            case breakBlockName:
                return GameManager.breakBlockMaxLevel;
            case breakSkinName:
                return GameManager.breakSkinMaxLevel;
            case breakMobTextureName:
                return GameManager.breakMobTextureMaxLevel;
        }
        // 想定されないreturn
        return 0;
    }

    private static double getUpdateLevelProbability (String bugName) {
        switch (bugName) {
            case redScreenName:
                return GameManager.redScreenUpdateLevelProbability;
            case garbledCharName:
                return GameManager.garbledCharUpdateLevelProbability;
            case breakScreenName:
                return GameManager.breakScreenUpdateLevelProbability;
            case breakBlockName:
                return GameManager.breakBlockUpdateLevelProbability;
            case breakSkinName:
                return GameManager.breakSkinUpdateLevelProbability;
            case breakMobTextureName:
                return GameManager.breakMobTextureUpdateLevelProbability;
        }
        // 想定されないreturn
        return 0;
    }


    public static void updateLevel(String playerName, String bugName, int bugLevel){
        if (PlayerGameManager.playersBugLevel.get(playerName) == null){
            PlayerGameManager.playersBugLevel.put(playerName, new HashMap());
        }
        PlayerGameManager.playersBugLevel.get(playerName).put(bugName, bugLevel);
    }

    public static void updateAllPlayerLevel(String bugName, int bugLevel){
        // 適当なkeyで取得できると便利などで設定しておく
        updateLevel(commonPlayerName, bugName, bugLevel);

        // レベル設定
        BugMod.minecraftServerInstance.getPlayerManager().getPlayerList().forEach(player -> {
            updateLevel(player.getEntityName(), bugName, bugLevel);
        });
    }

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
