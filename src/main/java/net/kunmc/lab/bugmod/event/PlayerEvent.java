package net.kunmc.lab.bugmod.event;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.kunmc.lab.bugmod.game.GameManager;
import net.kunmc.lab.bugmod.networking.ClientNetworking;
import net.kunmc.lab.bugmod.networking.ServerNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.RunArgs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

public class PlayerEvent {
    /**
     * 松明をおく
     */
    public static void register() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) ->
        {
            ItemStack item = player.getStackInHand(hand);
            if (item.getItem() == Items.TORCH){
                System.out.println(GameManager.redScreenLevel);
                ClientNetworking.sendRedScreenLevel();
            }
            return ActionResult.PASS;
        });

    }
}
