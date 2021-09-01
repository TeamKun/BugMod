package net.kunmc.lab.bugmod.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.kunmc.lab.bugmod.game.GameManager;
import net.kunmc.lab.bugmod.shader.ShaderManager;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public class UpdateClientLevelManager {
    private static boolean shouldUpdate = true;
    public static boolean isBlackOut = false;
    public static boolean runBugs = false;
    public static int time = 0;
    private static String name = "";
    private static int level = 0;

    public static void register(){
        ClientTickEvents.START_CLIENT_TICK.register(m -> {
            UpdateClientLevelManager.updateLevel();
            UpdateClientLevelManager.updateTimer();
        });
    }

    public static void startUpdateLevel(String targetName, int targetLevel){
        if (!shouldUpdatedLevel()) return;
        name = targetName;
        level = targetLevel;
        shouldUpdate = false;
    }

    public static void updateTimer(){
        if (name.isEmpty()) return;

        time++;
        if (time > 30){
            time = 0;
            name = "";
            level = 0;
            shouldUpdate = true;
        }
    }

    public static void updateLevel() {
        if (name.isEmpty()) return;
        switch (name) {
            case GameManager.breakTextureName:
            case GameManager.garbledCharName:
            case GameManager.breakSkinName:
            case GameManager.redScreenName:
            case GameManager.breakScreenName:
                updateLevelWithGlitch();
                break;
            case GameManager.spiderSoundName:
                updateLevelWithBugs();
                break;
        }
    }

    public static boolean shouldUpdatedLevel() {
        return GameManager.runningMode == GameManager.GameMode.MODE_START && shouldUpdate;
    }

    private static void updateLevelWithGlitch() {
        if ( time == 0) {
            ShaderManager.runGlitch(15);
        }

        if (time == 5) {
            switch (name) {
                case GameManager.breakTextureName:
                    GameManager.breakTextureLevel = level;
                    break;
                case GameManager.garbledCharName:
                    GameManager.garbledCharLevel = level;
                    break;
                case GameManager.breakSkinName:
                    GameManager.breakSkinLevel = level;
                    MinecraftClient.getInstance().getSkinProvider().loadSkin(MinecraftClient.getInstance().player.getGameProfile(), (type, id, texture) -> {}, true);
                    break;
                case GameManager.redScreenName:
                    GameManager.redScreenLevel = level;
                    break;
                case GameManager.breakScreenName:
                    GameManager.breakScreenLevel = level;
                    break;
            }
        }
    }

    /**
     * 没案、画面をブラックアウトさせる。
     *   画面が一時的に見えなくなるのでテンポが悪くなり、画面上の表示をコントールすることが難しかったので没
     */
    //private static void updateLevelWithBlack() {
    //    if (time == 0) {
    //        isBlackOut = true;
    //    }

    //    if (time == 15) {
    //        isBlackOut = false;
    //    }
    //    if (time == 0) {
    //        switch (name) {
    //            case GameManager.redScreenName:
    //                GameManager.redScreenLevel = level;
    //                break;
    //            case GameManager.breakScreenName:
    //                GameManager.breakScreenLevel = level;
    //                break;
    //        }
    //    }
    //}
    private static void updateLevelWithBugs() {
        if (time == 0) {
            runBugs = true;
            GameManager.spiderSoundLevel = level;
            BugsHUD.startBugs();
        }

        if (time == 20) {
            runBugs = false;
            BugsHUD.endBugs();
        }
    }
}