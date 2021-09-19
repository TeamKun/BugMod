package net.kunmc.lab.bugmod.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kunmc.lab.bugmod.game.GameManager;
import net.kunmc.lab.bugmod.shader.ShaderManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.MessageType;
import net.minecraft.text.Text;

import static net.kunmc.lab.bugmod.networking.ClientNetworking.sendUpdateSkin;

@Environment(EnvType.CLIENT)
public class UpdateClientLevelManager {
    public static void updateLevel(String targetName, int targetLevel, String playerName, boolean useEffect, boolean recovery) {

        // レベルアップ時のエフェクト
        if (useEffect) {
            switch (targetName) {
                case GameManager.breakBlockName:
                case GameManager.garbledCharName:
                case GameManager.breakSkinName:
                case GameManager.redScreenName:
                case GameManager.breakScreenName:
                case GameManager.breakMobTextureName:
                    if (ShaderManager.glitchTime <= 0)
                        ShaderManager.runGlitch(30);
                    break;
            }
        }
        // レベルアップ実行
        switch (targetName) {
            case GameManager.breakBlockName:
                GameManager.breakBlockLevel = targetLevel;
                break;
            case GameManager.garbledCharName:
                GameManager.garbledCharLevel = targetLevel;
                break;
            case GameManager.breakSkinName:
                if (targetLevel != GameManager.breakSkinLevel) {
                    GameManager.breakSkinLevel = targetLevel;
                    sendUpdateSkin();
                }
                break;
            case GameManager.redScreenName:
                GameManager.redScreenLevel = targetLevel;
                break;
            case GameManager.breakScreenName:
                GameManager.breakScreenLevel = targetLevel;
                break;
            case GameManager.breakMobTextureName:
                GameManager.breakMobTextureLevel = targetLevel;
                break;
        }
        // メッセージ送信
        if (GameManager.showUpdateLevelMessage)
            sendUpdateLevelMessage(targetName, targetLevel, playerName, recovery);
    }

    public static void sendUpdateLevelMessage(String targetName, int targetLevel, String playerName, boolean recovery) {
        if (playerName.isEmpty()) return;

        String sub = "";
        String message = "";
        if (!recovery) {
            switch (targetName) {
                case GameManager.breakBlockName:
                    sub = subMessage(targetLevel, GameManager.breakBlockMaxLevel);
                    message = String.format("%sの行動で%sブロックがバグるようになった", playerName, sub);
                    if (targetLevel == 3) message = String.format("%sの行動でバグったブロックから虫の声が聞こえるようになった", playerName, sub);
                    if (targetLevel == 4) message = String.format("%sの行動でバグったブロックから虫が発生するようになった", playerName, sub);
                    if (targetLevel == 5) message = String.format("%sの行動でバグったブロックからさらに虫が発生するようになった", playerName, sub);
                    MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, Text.of(message), MinecraftClient.getInstance().player.getUuid());
                    break;
                case GameManager.garbledCharName:
                    sub = subMessage(targetLevel, GameManager.garbledCharMaxLevel);
                    message = String.format("%sの行動で%sチャットがバグるようになった", playerName, sub);
                    MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, Text.of(message), MinecraftClient.getInstance().player.getUuid());
                    break;
                case GameManager.breakSkinName:
                    sub = subMessage(targetLevel, GameManager.breakSkinMaxLevel);
                    message = String.format("%sの行動で%sスキンがバグった", playerName, sub);
                    MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, Text.of(message), MinecraftClient.getInstance().player.getUuid());
                    break;
                case GameManager.redScreenName:
                    sub = subMessage(targetLevel, GameManager.redScreenMaxLevel);
                    message = String.format("%sの行動で視界が%s赤く染まった", playerName, sub);
                    MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, Text.of(message), MinecraftClient.getInstance().player.getUuid());
                    break;
                case GameManager.breakScreenName:
                    sub = subMessage(targetLevel, GameManager.breakScreenMaxLevel);
                    message = String.format("%sの行動で視界が%s削れた", playerName, sub);
                    MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, Text.of(message), MinecraftClient.getInstance().player.getUuid());
                    break;
                case GameManager.breakMobTextureName:
                    message = String.format("%sの行動で一部のMobがバグった", playerName, sub);
                    if (targetLevel >= 2) message = String.format("%sの行動でさらにMobがバグった", playerName, sub);
                    MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, Text.of(message), MinecraftClient.getInstance().player.getUuid());
                    if (targetLevel == 6) {
                        MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, Text.of("これ以上Mobはバグらないようだ"), MinecraftClient.getInstance().player.getUuid());
                    }
                    break;
            }
        } else {
            switch (targetName) {
                case GameManager.breakBlockName:
                    message = String.format("%sの行動でブロックがバグりにくくなった", playerName, sub);
                    if (targetLevel == 0) message = String.format("%sの行動でブロックがバグらなくなった", playerName, sub);
                    if (targetLevel == 2) message = String.format("%sの行動でバグったブロックから虫の声が止んだ", playerName, sub);
                    if (targetLevel >= 3) message = String.format("%sの行動でバグったブロックから少し虫の声が止んだ", playerName, sub);
                    MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, Text.of(message), MinecraftClient.getInstance().player.getUuid());
                    break;
                case GameManager.garbledCharName:
                    message = String.format("%sの行動でチャットがバグりにくくなった", playerName, sub);
                    if (targetLevel == 0) message = String.format("%sの行動でチャットがバグらなくなった", playerName, sub);
                    MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, Text.of(message), MinecraftClient.getInstance().player.getUuid());
                    break;
                case GameManager.breakSkinName:
                    message = String.format("%sの行動でスキンのバグが少し治った", playerName, sub);
                    if (targetLevel == 0) message = String.format("%sの行動でスキンのバグが治った", playerName, sub);
                    MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, Text.of(message), MinecraftClient.getInstance().player.getUuid());
                    break;
                case GameManager.redScreenName:
                    sub = subMessage(targetLevel, GameManager.redScreenMaxLevel);
                    message = String.format("%sの行動で視界の赤が薄まった", playerName, sub);
                    if (targetLevel == 0) message = String.format("%sの行動で視界が赤くなくなった", playerName, sub);
                    MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, Text.of(message), MinecraftClient.getInstance().player.getUuid());
                    break;
                case GameManager.breakScreenName:
                    sub = subMessage(targetLevel, GameManager.breakScreenMaxLevel);
                    message = String.format("%sの行動で視界の削れが少し治った", playerName, sub);
                    if (targetLevel == 0) message = String.format("%sの行動で視界の削れが治った", playerName, sub);
                    MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, Text.of(message), MinecraftClient.getInstance().player.getUuid());
                    break;
                case GameManager.breakMobTextureName:
                    sub = subMessage(targetLevel, GameManager.breakScreenMaxLevel);
                    message = String.format("%sの行動で一部Mobのバグが治った", playerName, sub);
                    MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, Text.of(message), MinecraftClient.getInstance().player.getUuid());
                    break;
            }
        }
    }

    private static String subMessage(int targetLevel, int targetMaxLevel) {
        String message = "";
        if (targetLevel > 0) message = "少し";
        if (targetLevel > 1) message = "より";
        if (targetLevel >= targetMaxLevel) message = "最大まで";
        return message;
    }
}