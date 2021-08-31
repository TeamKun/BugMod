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
        if (time > 40){
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
                updateLevelWithGlitch();
                break;
            case GameManager.redScreenName:
            case GameManager.breakScreenName:
                updateLevelWithBlack();
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
        if ( time == 0 || time == 25) {
            ShaderManager.runGlitch(10);
        }

        if (time == 25) {
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
            }
        }
    }

    private static void updateLevelWithBlack() {
        if (time == 0 || time == 15) {
            isBlackOut = true;
        }

        if (time == 5 || time == 25) {
            isBlackOut = false;
        }
        if (time == 20) {
            switch (name) {
                case GameManager.redScreenName:
                    GameManager.redScreenLevel = level;
                    break;
                case GameManager.breakScreenName:
                    GameManager.breakScreenLevel = level;
                    break;
            }
        }
    }
    private static void updateLevelWithBugs() {
        if (time == 0) {
            runBugs = true;
            GameManager.spiderSoundLevel = level;
        }

        if (time == 40) {
            runBugs = false;
        }
    }
}