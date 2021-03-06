package net.kunmc.lab.bugmod.command;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.kunmc.lab.bugmod.BugMod;
import net.kunmc.lab.bugmod.game.GameManager;
import net.kunmc.lab.bugmod.game.PlayerGameManager;
import net.kunmc.lab.bugmod.util.DecolationConst;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.MessageType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

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
                        usage += String.format("  /bug showParam <playerName>%s", br);
                        usage += String.format("    show player's parameters(bugLevel)%s", br);
                        usage += String.format("  /bug setParam <parameterName> <value>%s", br);
                        usage += String.format("    set parameter value%s", br);
                        usage += String.format("  /bug setParam <parameterName(bugLevel)> <playerName> <value>%s", br);
                        usage += String.format("    set player's paramete(bugLevel) value%s", br);
                        usage += String.format("  /bug setParam resetUpdateLevelProbability%s", br);
                        usage += String.format("    reset all probability values%s", br);
                        context.getSource().sendFeedback(new LiteralText(usage), false);
                        return 1;
                    })
                    .requires(source -> source.hasPermissionLevel(2))
                    .then(CommandManager.literal("start")
                            .executes(context -> {
                                if (GameManager.runningMode == GameManager.GameMode.MODE_START) {
                                    context.getSource().sendFeedback(new LiteralText(DecolationConst.AQUA + "???????????????????????????"), false);
                                    return 1;
                                }

                                GameManager.controller(GameManager.GameMode.MODE_START);
                                context.getSource().getMinecraftServer().getPlayerManager()
                                        .broadcastChatMessage(new LiteralText(DecolationConst.GREEN + "????????????????????????????????????"), MessageType.SYSTEM, context.getSource().getPlayer().getUuid());
                                return 1;
                            }))
                    .then(CommandManager.literal("stop")
                            .executes(context -> {
                                if (GameManager.runningMode == GameManager.GameMode.MODE_NEUTRAL) {
                                    context.getSource().sendFeedback(new LiteralText(DecolationConst.AQUA + "???????????????????????????"), false);
                                    return 1;
                                }

                                GameManager.resetGame();
                                GameManager.controller(GameManager.GameMode.MODE_NEUTRAL);
                                context.getSource().getMinecraftServer().getPlayerManager()
                                        .broadcastChatMessage(new LiteralText(DecolationConst.GREEN + "??????????????????????????????"), MessageType.SYSTEM, context.getSource().getPlayer().getUuid());

                                return 1;
                            }))
                    .then(CommandManager.literal("showParam")
                            .executes(context -> {
                                String[] name = GameManager.getAllBugName();
                                double[] prob = GameManager.getAllBugProbability();

                                List<String> message = new ArrayList();
                                message.add("Parameters:");
                                for (int i = 0; i < name.length; i++) {
                                    message.add(String.format("  %sLevelProbability: %.2f", name[i], prob[i]));
                                }
                                message.add(String.format("  recoveryMode: %b", GameManager.canRecovery));
                                message.add(String.format("  showUpdateLevelMessage: %b", GameManager.showUpdateLevelMessage));
                                message.add(String.format("  BreakSkinItem: %s", GameManager.BreakSkinItem));
                                context.getSource().sendFeedback(new LiteralText(String.join(br, message)), false);
                                return 1;
                            })
                            .then(CommandManager.argument("player", EntityArgumentType.player())
                                    .executes(context -> {
                                                List<String> message = new ArrayList();
                                                String[] name = GameManager.getAllBugName();
                                                PlayerEntity p = EntityArgumentType.getPlayer(context, "player");
                                                message.add("Parameters:");
                                                for (int i = 0; i < name.length; i++) {
                                                    String playerName = GameManager.isCommonLevelBug(name[i]) ? GameManager.commonPlayerName : p.getEntityName();
                                                    message.add(String.format("  %sLevel: %d", name[i], GameManager.getPlayerBugLevel(playerName, name[i])));
                                                }
                                                context.getSource().sendFeedback(new LiteralText(String.join(br, message)), false);
                                                return 1;
                                            }
                                    )))
                    .then(CommandManager.literal("setParam")
                            .then(CommandManager.literal(GameManager.redScreenName + "Level")
                                    .then(CommandManager.argument("players", EntityArgumentType.players())
                                            .then(CommandManager.argument("num", IntegerArgumentType.integer(0, GameManager.redScreenMaxLevel))
                                                    .executes(context -> {
                                                        EntityArgumentType.getPlayers(context, "players").forEach((player) -> {
                                                            setLevel(context, player.getEntityName(), GameManager.redScreenName);
                                                        });
                                                        return 1;
                                                    }))))
                            .then(CommandManager.literal(GameManager.breakBlockName + "Level")
                                    .then(CommandManager.argument("num", IntegerArgumentType.integer(0, GameManager.breakBlockMaxLevel))
                                            .executes(context -> {
                                                BugMod.minecraftServerInstance.getPlayerManager().getPlayerList().forEach((player) -> {
                                                    setLevel(context, player.getEntityName(), GameManager.breakBlockName);
                                                });
                                                return 1;
                                            })))
                            .then(CommandManager.literal(GameManager.breakScreenName + "Level")
                                    .then(CommandManager.argument("players", EntityArgumentType.players())
                                            .then(CommandManager.argument("num", IntegerArgumentType.integer(0, GameManager.breakScreenMaxLevel))
                                                    .executes(context -> {
                                                        EntityArgumentType.getPlayers(context, "players").forEach((player) -> {
                                                            setLevel(context, player.getEntityName(), GameManager.breakScreenName);
                                                        });
                                                        return 1;
                                                    }))))
                            .then(CommandManager.literal(GameManager.breakSkinName + "Level")
                                    .then(CommandManager.argument("num", IntegerArgumentType.integer(0, GameManager.breakSkinMaxLevel))
                                            .executes(context -> {
                                                BugMod.minecraftServerInstance.getPlayerManager().getPlayerList().forEach((player) -> {
                                                    setLevel(context, player.getEntityName(), GameManager.breakSkinName);
                                                });
                                                return 1;
                                            })))
                            .then(CommandManager.literal(GameManager.garbledCharName + "Level")
                                    .then(CommandManager.argument("players", EntityArgumentType.players())
                                            .then(CommandManager.argument("num", IntegerArgumentType.integer(0, GameManager.garbledCharMaxLevel))
                                                    .executes(context -> {
                                                        EntityArgumentType.getPlayers(context, "players").forEach((player) -> {
                                                            setLevel(context, player.getEntityName(), GameManager.garbledCharName);
                                                        });
                                                        return 1;
                                                    }))))
                            .then(CommandManager.literal(GameManager.breakMobTextureName + "Level")
                                    .then(CommandManager.argument("num", IntegerArgumentType.integer(0, GameManager.breakMobTextureMaxLevel))
                                            .executes(context -> {
                                                BugMod.minecraftServerInstance.getPlayerManager().getPlayerList().forEach((player) -> {
                                                    setLevel(context, player.getEntityName(), GameManager.breakMobTextureName);
                                                });
                                                return 1;
                                            })))
                            .then(CommandManager.literal("recoveryMode")
                                    .then(CommandManager.argument("boolean", BoolArgumentType.bool())
                                            .executes(context -> {
                                                boolean value = BoolArgumentType.getBool(context, "boolean");
                                                GameManager.canRecovery = value;
                                                context.getSource().sendFeedback(new LiteralText(String.format(DecolationConst.GREEN + "recoveryMode???%b?????????????????????", value)), true);
                                                return 1;
                                            })))
                            .then(CommandManager.literal("showUpdateLevelMessage")
                                    .then(CommandManager.argument("boolean", BoolArgumentType.bool())
                                            .executes(context -> {
                                                boolean value = BoolArgumentType.getBool(context, "boolean");
                                                GameManager.showUpdateLevelMessage = value;
                                                context.getSource().sendFeedback(new LiteralText(String.format(DecolationConst.GREEN + "showUpdateLevelMessage???%b?????????????????????", value)), true);
                                                return 1;
                                            })))
                            .then(CommandManager.literal(GameManager.redScreenName + "Probability")
                                    .then(CommandManager.argument("num", DoubleArgumentType.doubleArg(0, 1.0))
                                            .executes(context -> {
                                                String name = GameManager.redScreenName + "Probability";
                                                double value = DoubleArgumentType.getDouble(context, "num");
                                                GameManager.redScreenUpdateLevelProbability = value;
                                                context.getSource().sendFeedback(new LiteralText(String.format(DecolationConst.GREEN + "%s???%.2f?????????????????????", name, value)), true);
                                                return 1;
                                            })))
                            .then(CommandManager.literal(GameManager.breakBlockName + "Probability")
                                    .then(CommandManager.argument("num", DoubleArgumentType.doubleArg(0, 1.0))
                                            .executes(context -> {
                                                String name = GameManager.breakBlockName + "Probability";
                                                double value = DoubleArgumentType.getDouble(context, "num");
                                                GameManager.breakBlockUpdateLevelProbability = value;
                                                context.getSource().sendFeedback(new LiteralText(String.format(DecolationConst.GREEN + "%s???%.2f?????????????????????", name, value)), true);
                                                return 1;
                                            })))
                            .then(CommandManager.literal(GameManager.breakScreenName + "Probability")
                                    .then(CommandManager.argument("num", DoubleArgumentType.doubleArg(0, 1.0))
                                            .executes(context -> {
                                                String name = GameManager.breakScreenName + "Probability";
                                                double value = DoubleArgumentType.getDouble(context, "num");
                                                GameManager.breakScreenUpdateLevelProbability = value;
                                                context.getSource().sendFeedback(new LiteralText(String.format(DecolationConst.GREEN + "%s???%.2f?????????????????????", name, value)), true);
                                                return 1;
                                            })))
                            .then(CommandManager.literal(GameManager.breakSkinName + "Probability")
                                    .then(CommandManager.argument("num", DoubleArgumentType.doubleArg(0, 1.0))
                                            .executes(context -> {
                                                String name = GameManager.breakSkinName + "Probability";
                                                double value = DoubleArgumentType.getDouble(context, "num");
                                                GameManager.breakSkinUpdateLevelProbability = value;
                                                context.getSource().sendFeedback(new LiteralText(String.format(DecolationConst.GREEN + "%s???%.2f?????????????????????", name, value)), true);
                                                return 1;
                                            })))
                            .then(CommandManager.literal(GameManager.garbledCharName + "Probability")
                                    .then(CommandManager.argument("num", DoubleArgumentType.doubleArg(0, 1.0))
                                            .executes(context -> {
                                                String name = GameManager.garbledCharName + "Probability";
                                                double value = DoubleArgumentType.getDouble(context, "num");
                                                GameManager.garbledCharUpdateLevelProbability = value;
                                                context.getSource().sendFeedback(new LiteralText(String.format(DecolationConst.GREEN + "%s???%.2f?????????????????????", name, value)), true);
                                                return 1;
                                            })))
                            .then(CommandManager.literal(GameManager.breakMobTextureName + "Probability")
                                    .then(CommandManager.argument("num", DoubleArgumentType.doubleArg(0, 1.0))
                                            .executes(context -> {
                                                String name = GameManager.breakMobTextureName + "Probability";
                                                double value = DoubleArgumentType.getDouble(context, "num");
                                                GameManager.breakMobTextureUpdateLevelProbability = value;
                                                context.getSource().sendFeedback(new LiteralText(String.format(DecolationConst.GREEN + "%s???%.2f?????????????????????", name, value)), true);
                                                return 1;
                                            })))

                            .then(CommandManager.literal("addBreakSkinItem")
                                    .then(CommandManager.argument("item", ItemStackArgumentType.itemStack())
                                            .executes(context -> {
                                                Identifier identifier = Registry.ITEM.getId(ItemStackArgumentType.getItemStackArgument(context, "item").getItem());
                                                GameManager.BreakSkinItem.add(identifier);
                                                context.getSource().sendFeedback(new LiteralText(String.format(DecolationConst.GREEN + "%s??????????????????????????????????????????????????????", identifier)), true);
                                                return 1;
                                            })))
                            .then(CommandManager.literal("removeBreakSkinItem")
                                    .then(CommandManager.argument("item", ItemStackArgumentType.itemStack())
                                            .executes(context -> {
                                                Identifier identifier = Registry.ITEM.getId(ItemStackArgumentType.getItemStackArgument(context, "item").getItem());
                                                if (!GameManager.BreakSkinItem.contains(identifier)){
                                                    context.getSource().sendFeedback(new LiteralText(String.format(DecolationConst.RED + "???????????????????????????????????????????????????%s??????????????????????????????", GameManager.BreakSkinItem)), true);
                                                    return 1;
                                                }
                                                GameManager.BreakSkinItem.remove(identifier);
                                                context.getSource().sendFeedback(new LiteralText(String.format(DecolationConst.GREEN + "%s?????????????????????????????????????????????????????????", identifier)), true);
                                                return 1;
                                            })))
                            .then(CommandManager.literal("resetUpdateLevelProbability")
                                    .executes(context -> {
                                        GameManager.resetUpdateLevelProbability();
                                        String[] name = GameManager.getAllBugName();
                                        double[] prob = GameManager.getAllBugProbability();

                                        List<String> message = new ArrayList();
                                        message.add("Parameters:");
                                        for (int i = 0; i < name.length; i++) {
                                            message.add(String.format("  %sLevelProbability: %.2f", name[i], prob[i]));
                                        }
                                        context.getSource().sendFeedback(new LiteralText(String.format(DecolationConst.GREEN + "???????????????????????????????????????????????????????????????")), true);
                                        context.getSource().sendFeedback(new LiteralText(String.join(br, message)), false);
                                        return 1;
                                    }))
                    ).build();
            dispatcher.getRoot().addChild(rootNode);
        });
    }

    private static void setLevel(CommandContext<ServerCommandSource> context, String playerName, String bugName) {
        String targetBugName = bugName + "Level";
        int value = IntegerArgumentType.getInteger(context, "num");
        PlayerGameManager.playersBugLevel.get(playerName).put(bugName, value);
        if (GameManager.isCommonLevelBug(bugName)) {
            PlayerGameManager.playersBugLevel.get(GameManager.commonPlayerName).put(bugName, value);
        }
        context.getSource().sendFeedback(new LiteralText(String.format(DecolationConst.GREEN + "%s???%s???%d?????????????????????", playerName, targetBugName, value)), true);
    }
}