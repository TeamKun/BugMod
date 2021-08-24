package net.kunmc.lab.bugmod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

import static com.mojang.brigadier.arguments.StringArgumentType.word;

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
                    .then(CommandManager.literal("config")
                            .executes(context -> {
                                context.getSource().sendFeedback(new LiteralText("バグの進行が停止した"), true);
                                return 1;
                            }))
                    .then(CommandManager.literal("config-set")
                            .then(CommandManager.argument(GameManager.redScreenName+"Level", IntegerArgumentType.integer(0,GameManager.redScreenMaxLevel))
                                    .executes(context -> {
                                        String name = GameManager.redScreenName + "Level";
                                        int value = IntegerArgumentType.getInteger(context, name);
                                        context.getSource().sendFeedback(new LiteralText(String.format("%sを%dに設定しました", name, value)), true);
                                        return 1;
                                    }))
                            .then(CommandManager.argument(GameManager.breakTextureName+"Level", IntegerArgumentType.integer(0,GameManager.breakTextureMaxLevel))
                                    .executes(context -> {
                                        String name = GameManager.breakTextureName + "Level";
                                        int value = IntegerArgumentType.getInteger(context, name);
                                        context.getSource().sendFeedback(new LiteralText(String.format("%sを%dに設定しました", name, value)), true);
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