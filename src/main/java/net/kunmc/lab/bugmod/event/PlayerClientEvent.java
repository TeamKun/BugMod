package net.kunmc.lab.bugmod.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.kunmc.lab.bugmod.networking.ClientNetworking;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

@Environment(EnvType.CLIENT)
public class PlayerClientEvent {
    public static void register() {
        //UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
        //   ItemStack item = player.getStackInHand(hand);

        //    if (item.getItem() == Items.TORCH){
        //        BlockPos pos = hitResult.getBlockPos();
        //        // 置けるならレベル更新
        //        if (Block.sideCoversSmallSquare(world, pos, Direction.UP)) {
        //            ClientNetworking.sendRedScreenLevel();
        //        }
        //    }
        //    return ActionResult.PASS;
        //});
        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack item = player.getStackInHand(hand);
            if (item.getItem() == Items.WATER_BUCKET ||
                    item.getItem() == Items.LAVA_BUCKET){
                ClientNetworking.sendBreakScreenLevel();
            }
            return TypedActionResult.pass(player.getStackInHand(hand));
        });
    }
}