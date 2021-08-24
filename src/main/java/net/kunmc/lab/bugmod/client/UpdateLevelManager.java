package net.kunmc.lab.bugmod.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kunmc.lab.bugmod.game.GameManager;
import net.kunmc.lab.bugmod.networking.ServerNetworking;
import net.kunmc.lab.bugmod.shader.ShaderManager;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public class UpdateLevelManager {
    private static boolean isUpdating = false;
    public static boolean isBlackOut = false;
    private static int time = 0;
    private static String name = "";
    private static int level = 0;

    public static void startUpdateLevel(String targetName, int targetLevel){
        isUpdating = true;
        name = targetName;
        level = targetLevel;
    }

    public static void updateTimer(){
        if (!isUpdating) return;

        time++;
        if (time > 40){
            time = 0;
            isUpdating = false;
            name = "";
            level = 0;
        }
    }

    public static void updateLevel() {
        if (!shouldUpdatedLevel()) return;
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
        }
    }

    private static boolean shouldUpdatedLevel() {
        return GameManager.runningMode == GameManager.GameMode.MODE_START && !isUpdating;
    }

    private static void updateLevelWithGlitch() {
        if ( time == 0 || time == 25) {
            ShaderManager.runGlitch(10);
        }

        if (time == 25) {
            switch (name) {
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
            }
        }
    }
}