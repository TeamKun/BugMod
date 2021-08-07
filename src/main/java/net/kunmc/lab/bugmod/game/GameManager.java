package net.kunmc.lab.bugmod.game;

import net.minecraft.client.RunArgs;

public class GameManager {
    // 画面が赤くなる
    private static int redScreenLevel;
    // 文字化けする
    private static int garbledCharLevel;
    // 画面が壊れる
    private static int breakScreenLevel;
    // テクスチャがバグる
    private static int breakTextureLevel;
    // スキンがバグる
    private static int breakSkinLevel;
    // 音がバグる
    private static int helpSoundLevel;
    // 虫が走る
    private static int bugRunLevel;

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


    public enum GameMode {
        // 開始前状態
        MODE_NEUTRAL,
        // 開始状態
        MODE_START,
        // 一時停止状態(resetGameせずに停止・再開ができる)
        MODE_PAUSE
    }
}