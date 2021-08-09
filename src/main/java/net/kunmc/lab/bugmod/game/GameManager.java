package net.kunmc.lab.bugmod.game;

import net.kunmc.lab.bugmod.networking.ServerNetworking;

public class GameManager {
    public static final String redScreenName = "redscreen";
    public static final String garbledCharName = "garbledchar";
    public static final String breakScreenName = "breakscreen";
    public static final String breakTextureName = "breaktexture";
    public static final String breakSkinName = "breakskin";
    public static final String helpSoundName = "helpsound";
    public static final String bugRunName = "bugrun";

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
    public static int bugRunLevel;

    // Gameの実行・停止状態の管理用
    public static GameMode runningMode;

    public static void resetGame(){
        redScreenLevel = 0;
        garbledCharLevel = 0;
        breakScreenLevel = 0;
        breakTextureLevel = 0;
        breakSkinLevel = 0;
        helpSoundLevel = 0;
        bugRunLevel = 0;
        runningMode = GameMode.MODE_NEUTRAL;
    }

    public static void controller(GameMode runningMode) {
        // モードを設定

        switch (runningMode) {
            case MODE_START:
            case MODE_NEUTRAL:
                GameManager.runningMode = runningMode;
                resetGame();
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

    public static void updateLevel(String name, int level){
        switch (name){
            case redScreenName:
                if (isUpdatedLevel(GameManager.redScreenLevel, level)){
                    GameManager.redScreenLevel = level;
                    ServerNetworking.sendRedScreenLevel();
                }
                break;
        }
    }

    private static boolean isUpdatedLevel(int currentLevel, int level){
        return currentLevel < level;
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