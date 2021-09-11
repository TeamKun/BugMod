package net.kunmc.lab.bugmod.command;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.kunmc.lab.bugmod.game.GameManager;
import net.kunmc.lab.bugmod.util.DecolationConst;
import net.minecraft.network.MessageType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
                        usage += String.format("  /bug setParam resetUpdateLevelProbability%s", br);
                        usage += String.format("    reset all probability values%s", br);
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
                                double[] prob = GameManager.getAllBugProbability();

                                List<String> message = new ArrayList();
                                message.add("Parameters:");
                                for (int i=0; i< name.length; i++) {
                                    message.add(String.format("  %sLevel: %d", name[i], level[i]));
                                }
                                for (int i=0; i< name.length; i++) {
                                    message.add(String.format("  %sLevelProbability: %.2f", name[i], prob[i]));
                                }
                                message.add(String.format("  recoveryMode: %b", GameManager.canRecovery));
                                message.add(String.format("  showUpdateLevelMessage: %b", GameManager.showUpdateLevelMessage));
                                context.getSource().sendFeedback(new LiteralText(String.join(br, message)), false);
                                return 1;
                            }))
                    .then(CommandManager.literal("setParam")
                            .then(CommandManager.literal(GameManager.redScreenName+"Level")
                                    .then(CommandManager.argument("num", IntegerArgumentType.integer(0,GameManager.redScreenMaxLevel))
                                            .executes(context -> {
                                                String name = GameManager.redScreenName + "Level";
                                                int value = IntegerArgumentType.getInteger(context, "num");
                                                GameManager.redScreenLevel = value;
                                                context.getSource().sendFeedback(new LiteralText(String.format(DecolationConst.GREEN + "%sを%dに設定しました", name, value)), true);
                                                return 1;
                                            })))
                            .then(CommandManager.literal(GameManager.breakBlockName +"Level")
                                    .then(CommandManager.argument("num", IntegerArgumentType.integer(0,GameManager.breakBlockMaxLevel))
                                            .executes(context -> {
                                                String name = GameManager.breakBlockName + "Level";
                                                int value = IntegerArgumentType.getInteger(context, "num");
                                                GameManager.breakBlockLevel = value;
                                                context.getSource().sendFeedback(new LiteralText(String.format(DecolationConst.GREEN + "%sを%dに設定しました", name, value)), true);
                                                return 1;
                                            })))
                            .then(CommandManager.literal(GameManager.breakScreenName+"Level")
                                    .then(CommandManager.argument("num", IntegerArgumentType.integer(0,GameManager.breakScreenMaxLevel))
                                            .executes(context -> {
                                                String name = GameManager.breakScreenName + "Level";
                                                int value = IntegerArgumentType.getInteger(context, "num");
                                                GameManager.breakScreenLevel = value;
                                                context.getSource().sendFeedback(new LiteralText(String.format(DecolationConst.GREEN + "%sを%dに設定しました", name, value)), true);
                                                return 1;
                                            })))
                            .then(CommandManager.literal(GameManager.breakSkinName+"Level")
                                    .then(CommandManager.argument("num", IntegerArgumentType.integer(0,GameManager.breakSkinMaxLevel))
                                            .executes(context -> {
                                                String name = GameManager.breakSkinName + "Level";
                                                int value = IntegerArgumentType.getInteger(context, "num");
                                                GameManager.breakSkinLevel = value;
                                                context.getSource().sendFeedback(new LiteralText(String.format(DecolationConst.GREEN + "%sを%dに設定しました", name, value)), true);
                                                return 1;
                                            })))
                            .then(CommandManager.literal(GameManager.garbledCharName+"Level")
                                    .then(CommandManager.argument("num", IntegerArgumentType.integer(0,GameManager.garbledCharMaxLevel))
                                            .executes(context -> {
                                                String name = GameManager.garbledCharName + "Level";
                                                int value = IntegerArgumentType.getInteger(context, "num");
                                                GameManager.garbledCharLevel = value;
                                                context.getSource().sendFeedback(new LiteralText(String.format(DecolationConst.GREEN + "%sを%dに設定しました", name, value)), true);
                                                return 1;
                                            })))
                            .then(CommandManager.literal(GameManager.breakMobTextureName+"Level")
                                    .then(CommandManager.argument("num", IntegerArgumentType.integer(0,GameManager.breakMobTextureMaxLevel))
                                            .executes(context -> {
                                                String name = GameManager.breakMobTextureName + "Level";
                                                int value = IntegerArgumentType.getInteger(context, "num");
                                                GameManager.breakMobTextureLevel = value;
                                                context.getSource().sendFeedback(new LiteralText(String.format(DecolationConst.GREEN + "%sを%dに設定しました", name, value)), true);
                                                return 1;
                                            })))
                            .then(CommandManager.literal("recoveryMode")
                                    .then(CommandManager.argument("boolean", BoolArgumentType.bool())
                                            .executes(context -> {
                                                boolean value = BoolArgumentType.getBool(context, "boolean");
                                                GameManager.canRecovery = value;
                                                context.getSource().sendFeedback(new LiteralText(String.format(DecolationConst.GREEN + "recoveryModeを%bに設定しました", value)), true);
                                                return 1;
                                            })))
                            .then(CommandManager.literal("showUpdateLevelMessage")
                                    .then(CommandManager.argument("boolean", BoolArgumentType.bool())
                                            .executes(context -> {
                                                boolean value = BoolArgumentType.getBool(context, "boolean");
                                                GameManager.showUpdateLevelMessage = value;
                                                context.getSource().sendFeedback(new LiteralText(String.format(DecolationConst.GREEN + "showUpdateLevelMessageを%bに設定しました", value)), true);
                                                return 1;
                                            })))
                            .then(CommandManager.literal(GameManager.redScreenName+"Probability")
                                    .then(CommandManager.argument("num", DoubleArgumentType.doubleArg(0, 1.0))
                                            .executes(context -> {
                                                String name = GameManager.redScreenName + "Probability";
                                                double value = DoubleArgumentType.getDouble(context, "num");
                                                GameManager.redScreenUpdateLevelProbability = value;
                                                context.getSource().sendFeedback(new LiteralText(String.format(DecolationConst.GREEN + "%sを%.2fに設定しました", name, value)), true);
                                                return 1;
                                            })))
                            .then(CommandManager.literal(GameManager.breakBlockName+"Probability")
                                    .then(CommandManager.argument("num", DoubleArgumentType.doubleArg(0, 1.0))
                                            .executes(context -> {
                                                String name = GameManager.breakBlockName + "Probability";
                                                double value = DoubleArgumentType.getDouble(context, "num");
                                                GameManager.breakBlockUpdateLevelProbability = value;
                                                context.getSource().sendFeedback(new LiteralText(String.format(DecolationConst.GREEN + "%sを%.2fに設定しました", name, value)), true);
                                                return 1;
                                            })))
                            .then(CommandManager.literal(GameManager.breakScreenName+"Probability")
                                    .then(CommandManager.argument("num", DoubleArgumentType.doubleArg(0, 1.0))
                                            .executes(context -> {
                                                String name = GameManager.breakScreenName + "Probability";
                                                double value = DoubleArgumentType.getDouble(context, "num");
                                                GameManager.breakScreenUpdateLevelProbability = value;
                                                context.getSource().sendFeedback(new LiteralText(String.format(DecolationConst.GREEN + "%sを%.2fに設定しました", name, value)), true);
                                                return 1;
                                            })))
                            .then(CommandManager.literal(GameManager.breakSkinName+"Probability")
                                    .then(CommandManager.argument("num", DoubleArgumentType.doubleArg(0, 1.0))
                                            .executes(context -> {
                                                String name = GameManager.breakSkinName + "Probability";
                                                double value = DoubleArgumentType.getDouble(context, "num");
                                                GameManager.breakSkinUpdateLevelProbability = value;
                                                context.getSource().sendFeedback(new LiteralText(String.format(DecolationConst.GREEN + "%sを%.2fに設定しました", name, value)), true);
                                                return 1;
                                            })))
                            .then(CommandManager.literal(GameManager.garbledCharName+"Probability")
                                    .then(CommandManager.argument("num", DoubleArgumentType.doubleArg(0, 1.0))
                                            .executes(context -> {
                                                String name = GameManager.garbledCharName + "Probability";
                                                double value = DoubleArgumentType.getDouble(context, "num");
                                                GameManager.garbledCharUpdateLevelProbability = value;
                                                context.getSource().sendFeedback(new LiteralText(String.format(DecolationConst.GREEN + "%sを%.2fに設定しました", name, value)), true);
                                                return 1;
                                            })))
                            .then(CommandManager.literal(GameManager.breakMobTextureName+"Probability")
                                    .then(CommandManager.argument("num", DoubleArgumentType.doubleArg(0, 1.0))
                                            .executes(context -> {
                                                String name = GameManager.breakMobTextureName + "Probability";
                                                double value = DoubleArgumentType.getDouble(context, "num");
                                                GameManager.breakMobTextureUpdateLevelProbability = value;
                                                context.getSource().sendFeedback(new LiteralText(String.format(DecolationConst.GREEN + "%sを%.2fに設定しました", name, value)), true);
                                                return 1;
                                            })))
                            .then(CommandManager.literal("resetUpdateLevelProbability")
                                    .executes(context -> {
                                        GameManager.resetUpdateLevelProbability();
                                        String[] name = GameManager.getAllBugName();
                                        double[] prob = GameManager.getAllBugProbability();

                                        List<String> message = new ArrayList();
                                        message.add("Parameters:");
                                        for (int i=0; i< name.length; i++) {
                                            message.add(String.format("  %sLevelProbability: %.2f", name[i], prob[i]));
                                        }
                                        context.getSource().sendFeedback(new LiteralText(String.format(DecolationConst.GREEN + "レベル更新の確率をデフォルトに設定しました")), true);
                                        context.getSource().sendFeedback(new LiteralText(String.join(br, message)), false);
                                        return 1;
                                    }))
                    ).build();
            dispatcher.getRoot().addChild(rootNode);
        });
    }
}