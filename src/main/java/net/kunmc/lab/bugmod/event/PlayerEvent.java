package net.kunmc.lab.bugmod.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.kunmc.lab.bugmod.networking.ClientNetworking;
import net.kunmc.lab.bugmod.shader.ShaderManager;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;

@Environment(EnvType.CLIENT)
public class PlayerEvent {
    /**
     * 松明をおく
     */
    public static void register() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            ItemStack item = player.getStackInHand(hand);
            if (item.getItem() == Items.TORCH){
                ClientNetworking.sendRedScreenLevel();
            }
            return ActionResult.PASS;
        });
        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack item = player.getStackInHand(hand);
            if (item.getItem() == Items.BUCKET ||
                    item.getItem() == Items.WATER_BUCKET ||
                    item.getItem() == Items.LAVA_BUCKET){
                ClientNetworking.sendBreakScreenLevel();
            }
            return TypedActionResult.pass(player.getStackInHand(hand));
        });
        PlayerBlockBreakEvents.AFTER.register((world, player, blockPos, blockState, blockEntity) -> {
            if (blockState.getBlock() == Blocks.STONE && !player.isCreative()) {
                //world.removeBlock(player.getBlockPos(),false);
                //world.setBlockState(player.getBlockPos().add(0,-1,0), Blocks.STONE.getDefaultState());
            }
        });
    }
}