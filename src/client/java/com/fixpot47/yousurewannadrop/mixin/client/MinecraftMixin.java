package com.fixpot47.yousurewannadrop.mixin.client;

import com.fixpot47.yousurewannadrop.DropApprovalState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Redirect(
            method = "handleKeybinds",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;dropItem(Lnet/minecraft/client/player/LocalPlayer;Z)V"
            )
    )
    private void youSureWannaDrop$confirmBeforeDropping(
            MultiPlayerGameMode gameMode,
            LocalPlayer player,
            boolean dropEntireStack
    ) {
        ItemStack selectedStack = player.getInventory().getSelectedItem();

        if (selectedStack.isEmpty()) {
            gameMode.dropItem(player, dropEntireStack);
            return;
        }

        int selectedSlot = player.getInventory().getSelectedSlot();

        if (DropApprovalState.isApproved(selectedSlot, selectedStack)) {
            gameMode.dropItem(player, dropEntireStack);
            return;
        }

        Minecraft minecraft = (Minecraft) (Object) this;
        ItemStack requestedItem = selectedStack.copyWithCount(1);

        minecraft.gui.setScreen(new ConfirmScreen(
                confirmed -> {
                    minecraft.gui.setScreen(null);

                    if (!confirmed || minecraft.player == null || minecraft.gameMode == null) {
                        return;
                    }

                    LocalPlayer currentPlayer = minecraft.player;
                    int currentSlot = currentPlayer.getInventory().getSelectedSlot();
                    ItemStack currentStack = currentPlayer.getInventory().getSelectedItem();

                    if (currentSlot != selectedSlot
                            || currentStack.isEmpty()
                            || !ItemStack.isSameItemSameComponents(requestedItem, currentStack)) {
                        return;
                    }

                    DropApprovalState.approve(currentSlot, currentStack);
                    minecraft.gameMode.dropItem(currentPlayer, dropEntireStack);
                },
                Component.translatable("screen.you_sure_wanna_drop.title"),
                Component.empty(),
                Component.translatable("screen.you_sure_wanna_drop.drop"),
                Component.translatable("screen.you_sure_wanna_drop.cancel")
        ));
    }
}
