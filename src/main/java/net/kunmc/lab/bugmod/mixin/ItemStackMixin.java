package net.kunmc.lab.bugmod.mixin;

import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow
    public abstract Item getItem();

    @ Inject(at = @At("TAIL"), method = "onCraft", cancellable = true)
    public void craft(World world, PlayerEntity player, int amount, CallbackInfo info) {
        if (getItem() instanceof PickaxeItem) {
            System.out.println("breakSkin");
            GameManager.updateLevel(GameManager.breakSkinName, GameManager.breakSkinLevel + 1);
        }
    }
}
