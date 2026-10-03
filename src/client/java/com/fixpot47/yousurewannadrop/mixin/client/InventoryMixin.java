package com.fixpot47.yousurewannadrop.mixin.client;

import com.fixpot47.yousurewannadrop.DropApprovalState;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Inventory.class)
public abstract class InventoryMixin {
    @Inject(method = "setSelectedSlot", at = @At("HEAD"))
    private void youSureWannaDrop$clearApprovalWhenSlotChanges(int selected, CallbackInfo ci) {
        Inventory inventory = (Inventory) (Object) this;
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player != null
                && inventory.player == minecraft.player
                && inventory.getSelectedSlot() != selected) {
            DropApprovalState.clear();
        }
    }
}
