package net.kunmc.lab.bugmod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.kunmc.lab.bugmod.game.GameManager;
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
                                context.getSource().sendFeedback(new LiteralText("世界の何かが少し変わり始めた"), true);
                                return 1;
                            }))
                    .then(CommandManager.literal("pause")
                            .executes(context -> {
                                GameManager.resetGame();
                                context.getSource().sendFeedback(new LiteralText("世界の何かが少し変わり始めた"), true);
                                return 1;
                            }))
                    .build();

            dispatcher.getRoot().addChild(rootNode);
        });
    }
}