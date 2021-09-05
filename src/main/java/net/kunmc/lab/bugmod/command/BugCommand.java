package net.kunmc.lab.bugmod.command;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.kunmc.lab.bugmod.game.GameManager;
import net.kunmc.lab.bugmod.util.DecolationConst;
import net.minecraft.network.MessageType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

public class BugCommand {
    public static void register() {
        String br = System.getProperty("line.separator");
        CommandRegistrationCallback.EVENT.register((dispatcher, b) -> {
            LiteralCommandNode<ServerCommandSource> rootNode = CommandManager
                    .literal("bug")
                    .executes(context -> {
                        String usage = "Usage: " + br;
                        usage += String.format("  /bug start%s", br);
                        usage += String.format("    start game%s", br);
                        usage += String.format("  /bug stop%s", br);
                        usage += String.format("    stop game(reset parameters)%s", br);
                        usage += String.format("  /bug showParam%s", br);
                        usage += String.format("    show game parameters%s", br);
                        usage += String.format("  /bug setParam <parameterName> <value>%s", br);
                        usage += String.format("    set parameter value%s", br);
                        context.getSource().sendFeedback(new LiteralText(usage), false);
                        return 1;
                    })
                    .requires(source -> source.hasPermissionLevel(2))
                    .then(CommandManager.literal("start")
                            .executes(context -> {
                                if (GameManager.runningMode == GameManager.GameMode.MODE_START) {
                                    context.getSource().sendFeedback(new LiteralText(DecolationConst.AQUA + "既に開始しています"), false);
                                    return 1;
                                }

                                GameManager.controller(GameManager.GameMode.MODE_START);
                                context.getSource().getMinecraftServer().getPlayerManager()
                                        .broadcastChatMessage(new LiteralText( DecolationConst.GREEN + "世界がバグるようになった"), MessageType.CHAT, context.getSource().getPlayer().getUuid());
                                return 1;
                            }))
                    .then(CommandManager.literal("stop")
                            .executes(context -> {
                                if (GameManager.runningMode == GameManager.GameMode.MODE_NEUTRAL) {
                                    context.getSource().sendFeedback(new LiteralText(DecolationConst.AQUA + "開始されていません"), false);
                                    return 1;
                                }

                                GameManager.resetGame();
                                GameManager.controller(GameManager.GameMode.MODE_NEUTRAL);
                                context.getSource().getMinecraftServer().getPlayerManager()
                                        .broadcastChatMessage(new LiteralText( DecolationConst.GREEN + "世界のバグが止まった"), MessageType.CHAT, context.getSource().getPlayer().getUuid());

                                return 1;
                            }))
                    .then(CommandManager.literal("showParam")
                            .executes(context -> {
                                String[] name = GameManager.getAllBugName();
                                int[] level = GameManager.getAllBugLevel();

                                String currentConfig = "Parameters:" + br;
                                for (int i=0; i< name.length; i++) {
                                    currentConfig += String.format("  %sLevel: %d%s", name[i], level[i], br);
                                }
                                currentConfig += String.format("  recoveryMode: %b", GameManager.canRecovery);
                                context.getSource().sendFeedback(new LiteralText(currentConfig), false);
                                return 1;
                            }))
                    .then(CommandManager.literal("setParam")
                            .then(CommandManager.literal(GameManager.redScreenName+"Level")
                                    .then(CommandManager.argument("num", IntegerArgumentType.integer(0,GameManager.redScreenMaxLevel))
                                            .executes(context -> {
                                                String name = GameManager.redScreenName + "Level";
                                                int value = IntegerArgumentType.getInteger(context, "num");
                                                context.getSource().sendFeedback(new LiteralText(String.format(DecolationConst.GREEN + "%sを%dに設定しました", name, value)), true);
                                                GameManager.redScreenLevel = value;
                                                return 1;
                                            })))
                            .then(CommandManager.literal(GameManager.breakBlockName +"Level")
                                    .then(CommandManager.argument("num", IntegerArgumentType.integer(0,GameManager.breakBlockMaxLevel))
                                            .executes(context -> {
                                                String name = GameManager.breakBlockName + "Level";
                                                int value = IntegerArgumentType.getInteger(context, "num");
                                                context.getSource().sendFeedback(new LiteralText(String.format(DecolationConst.GREEN + "%sを%dに設定しました", name, value)), true);
                                                GameManager.breakBlockLevel = value;
                                                return 1;
                                            })))
                            .then(CommandManager.literal(GameManager.breakScreenName+"Level")
                                    .then(CommandManager.argument("num", IntegerArgumentType.integer(0,GameManager.breakScreenMaxLevel))
                                            .executes(context -> {
                                                String name = GameManager.breakScreenName + "Level";
                                                int value = IntegerArgumentType.getInteger(context, "num");
                                                context.getSource().sendFeedback(new LiteralText(String.format(DecolationConst.GREEN + "%sを%dに設定しました", name, value)), true);
                                                GameManager.breakScreenLevel = value;
                                                return 1;
                                            })))
                            .then(CommandManager.literal(GameManager.breakSkinName+"Level")
                                    .then(CommandManager.argument("num", IntegerArgumentType.integer(0,GameManager.breakSkinMaxLevel))
                                            .executes(context -> {
                                                String name = GameManager.breakSkinName + "Level";
                                                int value = IntegerArgumentType.getInteger(context, "num");
                                                context.getSource().sendFeedback(new LiteralText(String.format(DecolationConst.GREEN + "%sを%dに設定しました", name, value)), true);
                                                GameManager.breakSkinLevel = value;
                                                return 1;
                                            })))
                            .then(CommandManager.literal(GameManager.garbledCharName+"Level")
                                    .then(CommandManager.argument("num", IntegerArgumentType.integer(0,GameManager.garbledCharMaxLevel))
                                            .executes(context -> {
                                                String name = GameManager.garbledCharName + "Level";
                                                int value = IntegerArgumentType.getInteger(context, "num");
                                                context.getSource().sendFeedback(new LiteralText(String.format(DecolationConst.GREEN + "%sを%dに設定しました", name, value)), true);
                                                GameManager.garbledCharLevel = value;
                                                return 1;
                                            })))
                            .then(CommandManager.literal("recoveryMode")
                                    .then(CommandManager.argument("mode", BoolArgumentType.bool())
                                            .executes(context -> {
                                                boolean value = BoolArgumentType.getBool(context, "mode");
                                                context.getSource().sendFeedback(new LiteralText(String.format(DecolationConst.GREEN + "recoveryModeを%bに設定しました", value)), true);
                                                GameManager.canRecovery = value;
                                                return 1;
                                            })))
                    ).build();
            dispatcher.getRoot().addChild(rootNode);
        });
    }
}