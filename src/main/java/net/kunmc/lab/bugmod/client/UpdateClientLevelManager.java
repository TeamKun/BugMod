package net.kunmc.lab.bugmod.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kunmc.lab.bugmod.game.GameManager;
import net.kunmc.lab.bugmod.shader.ShaderManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.MessageType;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class UpdateClientLevelManager {
    public static void updateLevel(String targetName, int targetLevel, String playerName, boolean useEffect, boolean recovery) {
        // レベルアップ時のエフェクト
        if (useEffect) {
            switch (targetName) {
                case GameManager.breakTextureName:
                case GameManager.garbledCharName:
                case GameManager.breakSkinName:
                case GameManager.redScreenName:
                case GameManager.breakScreenName:
                    if (ShaderManager.glitchTime <= 0) ;
                    ShaderManager.runGlitch(30);
                    break;
                case GameManager.spiderSoundName:
                    if (BugsHUD.time <= 0)
                        BugsHUD.startBugs(30);
                    break;
            }
        }
        // レベルアップ実行
        switch (targetName) {
            case GameManager.breakTextureName:
                GameManager.breakTextureLevel = targetLevel;
                break;
            case GameManager.garbledCharName:
                GameManager.garbledCharLevel = targetLevel;
                break;
            case GameManager.breakSkinName:
                GameManager.breakSkinLevel = targetLevel;
                MinecraftClient.getInstance().getSkinProvider().loadSkin(MinecraftClient.getInstance().player.getGameProfile(), (type, id, texture) -> {
                }, true);
                break;
            case GameManager.redScreenName:
                GameManager.redScreenLevel = targetLevel;
                break;
            case GameManager.breakScreenName:
                GameManager.breakScreenLevel = targetLevel;
                break;
            case GameManager.spiderSoundName:
                GameManager.spiderSoundLevel = targetLevel;
                break;
        }
        // メッセージ送信
        sendUpdateLevelMessage(targetName, targetLevel, playerName, recovery);
    }

    public static void sendUpdateLevelMessage(String targetName, int targetLevel, String playerName, boolean recovery) {
        if (playerName.isEmpty()) return;

        String sub = "";
        String message = "";
        if (!recovery) {
            switch (targetName) {
                case GameManager.breakTextureName:
                    sub = subMessage(targetLevel, GameManager.breakTextureMaxLevel);
                    message = String.format("%sの行動で%sブロックがバグるようになった", playerName, sub);
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
                    message = String.format("%sの行動で画面が%s赤く染まった", playerName, sub);
                    MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, Text.of(message), MinecraftClient.getInstance().player.getUuid());
                    break;
                case GameManager.breakScreenName:
                    sub = subMessage(targetLevel, GameManager.breakScreenMaxLevel);
                    message = String.format("%sの行動で画面が%s削れた", playerName, sub);
                    MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, Text.of(message), MinecraftClient.getInstance().player.getUuid());
                    break;
                case GameManager.spiderSoundName:
                    message = playerName + "の行動がクモを呼んだ";
                    MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, Text.of(message), MinecraftClient.getInstance().player.getUuid());
                    if (targetLevel == 1) {
                        message = "ブロック設置でクモの幻聴が聞こえるようになった";
                    } else if (targetLevel >= 2) {
                        message = "歩くとクモの幻聴が聞こえるようになった";
                    }
                    MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, Text.of(message), MinecraftClient.getInstance().player.getUuid());
                    break;
            }
        } else {
            switch (targetName) {
                case GameManager.breakTextureName:
                    message = String.format("%sの行動でブロックがバグりにくくなった", playerName, sub);
                    if (targetLevel == 0) message = String.format("%sの行動でブロックがバグらなくなった", playerName, sub);
                    MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, Text.of(message), MinecraftClient.getInstance().player.getUuid());
                    break;
                case GameManager.garbledCharName:
                    sub = subMessage(targetLevel, GameManager.garbledCharMaxLevel);
                    message = String.format("%sの行動でチャットがバグりにくくなった", playerName, sub);
                    if (targetLevel == 0) message = String.format("%sの行動でチャットがバグらなくなった", playerName, sub);
                    MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, Text.of(message), MinecraftClient.getInstance().player.getUuid());
                    break;
                case GameManager.breakSkinName:
                    sub = subMessage(targetLevel, GameManager.breakSkinMaxLevel);
                    message = String.format("%sの行動でスキンのバグが治った", playerName, sub);
                    if (targetLevel == 0) message = String.format("%sの行動でスキンのバグが治った", playerName, sub);
                    MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, Text.of(message), MinecraftClient.getInstance().player.getUuid());
                    break;
                case GameManager.redScreenName:
                    sub = subMessage(targetLevel, GameManager.redScreenMaxLevel);
                    message = String.format("%sの行動で画面の赤が薄まった", playerName, sub);
                    if (targetLevel == 0) message = String.format("%sの行動で画面が赤くなくなった", playerName, sub);
                    MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, Text.of(message), MinecraftClient.getInstance().player.getUuid());
                    break;
                case GameManager.breakScreenName:
                    sub = subMessage(targetLevel, GameManager.breakScreenMaxLevel);
                    message = String.format("%sの行動で画面が%s削れた", playerName, sub);
                    if (targetLevel == 0) message = String.format("%sの行動で画面の削れが治った", playerName, sub);
                    MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, Text.of(message), MinecraftClient.getInstance().player.getUuid());
                    break;
                case GameManager.spiderSoundName:
                    if (targetLevel == 0) {
                        message = "ブロックからクモの幻聴が消えた";
                    } else if (targetLevel == 1) {
                        message = "足音からクモの幻聴が消えた";
                    }
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