package net.kunmc.lab.bugmod.event;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
            System.out.println("BBB");
            if (item.getItem() == Items.TORCH){
                GameManager.redScreenLevel += 1;
                System.out.println("AAA" + GameManager.redScreenLevel);
            }
            return ActionResult.PASS;
        });

    }
}
