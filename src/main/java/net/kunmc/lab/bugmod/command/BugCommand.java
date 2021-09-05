package net.kunmc.lab.bugmod.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.kunmc.lab.bugmod.game.GameManager;
import net.kunmc.lab.bugmod.networking.ServerNetworking;
import net.minecraft.client.RunArgs;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

public class BugCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, b) -> {
            LiteralCommandNode<ServerCommandSource> rootNode = CommandManager
                    .literal("bug")
                    .requires(source -> source.hasPermissionLevel(2))
                    .then(CommandManager.literal("start")
                            .executes(context -> {
                                GameManager.resetGame();
                                GameManager.controller(GameManager.GameMode.MODE_START);
                                context.getSource().sendFeedback(new LiteralText("ワールドがバグるようになった"), true);
                                return 1;
                            }))
                    .then(CommandManager.literal("stop")
                            .executes(context -> {
                                GameManager.resetGame();
                                GameManager.controller(GameManager.GameMode.MODE_NEUTRAL);
                                context.getSource().sendFeedback(new LiteralText("ワールドのバグが停止した"), true);
                                return 1;
                            }))
                    .then(CommandManager.literal("pause")
                            .executes(context -> {
                                GameManager.controller(GameManager.GameMode.MODE_PAUSE);
                                context.getSource().sendFeedback(new LiteralText("バグの進行が停止した"), true);
                                return 1;
                            }))
                    .then(CommandManager.literal("showParam")
                            .executes(context -> {
                                String br = System.getProperty("line.separator");
                                String[] name = GameManager.getAllBugName();
                                int[] level = GameManager.getAllBugLevel();

                                String currentConfig = "Parameters: %s" + br;
                                for (int i=0; i< name.length; i++) {
                                    currentConfig += String.format("  %sLevel: %d", name[i], level[i]);
                                }
                                currentConfig += String.format("  mode: %s", GameManager.runningMode);
                                context.getSource().sendFeedback(new LiteralText(currentConfig), true);
                                return 1;
                            }))
                    .then(CommandManager.literal("setParam")
                            .then(CommandManager.literal(GameManager.redScreenName+"Level")
                                    .then(CommandManager.argument("num", IntegerArgumentType.integer(0,GameManager.redScreenMaxLevel))
                                            .executes(context -> {
                                                String name = GameManager.redScreenName + "Level";
                                                int value = IntegerArgumentType.getInteger(context, "num");
                                                context.getSource().sendFeedback(new LiteralText(String.format("%sを%dに設定しました", name, value)), true);
                                                GameManager.redScreenLevel = value;
                                                return 1;
                                            })))
                            .then(CommandManager.argument(GameManager.breakTextureName+"Level", IntegerArgumentType.integer(0,GameManager.breakTextureMaxLevel))
                                    .executes(context -> {
                                        String name = GameManager.breakTextureName + "Level";
                                        int value = IntegerArgumentType.getInteger(context, name);
                                        context.getSource().sendFeedback(new LiteralText(String.format("%sを%dに設定しました", name, value)), true);
                                        GameManager.breakTextureLevel = value;
                                        return 1;
                                    }))
                            .then(CommandManager.argument(GameManager.breakSkinName+"Level", IntegerArgumentType.integer(0,GameManager.breakSkinMaxLevel))
                                    .executes(context -> {
                                        String name = GameManager.breakSkinName + "Level";
                                        int value = IntegerArgumentType.getInteger(context, name);
                                        context.getSource().sendFeedback(new LiteralText(String.format("%sを%dに設定しました", name, value)), true);
                                        return 1;
                                    }))
                            .then(CommandManager.argument(GameManager.garbledCharName+"Level", IntegerArgumentType.integer(0,GameManager.garbledCharMaxLevel))
                                    .executes(context -> {
                                        String name = GameManager.garbledCharName + "Level";
                                        int value = IntegerArgumentType.getInteger(context, name);
                                        context.getSource().sendFeedback(new LiteralText(String.format("%sを%dに設定しました", name, value)), true);
                                        return 1;
                                    }))
                            .then(CommandManager.argument(GameManager.helpSoundName+"Level", IntegerArgumentType.integer(0,GameManager.helpSoundLevel))
                                    .executes(context -> {
                                        String name = GameManager.helpSoundName + "Level";
                                        int value = IntegerArgumentType.getInteger(context, name);
                                        context.getSource().sendFeedback(new LiteralText(String.format("%sを%dに設定しました", name, value)), true);
                                        return 1;
                                    }))
                    ).build();

            dispatcher.getRoot().addChild(rootNode);
        });
    }
}