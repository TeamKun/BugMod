package net.kunmc.lab.bugmod.mixin;

import net.kunmc.lab.bugmod.game.GameManager;
import net.kunmc.lab.bugmod.networking.ServerNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MilkBucketItem.class)
public class MilkBucketItemMixin {
    @Inject(at = @At("HEAD"), method = "finishUsing", cancellable = true)
    public void onUseItem(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        if (!world.isClient) {
            String bugName = GameManager.getBugRandom();
            if (!bugName.isEmpty()) {
                String[] array = bugName.split(" ");
                GameManager.recoverLevel(array[0], Integer.parseInt(array[1]), user.getEntityName());
            }
        }
    }
}