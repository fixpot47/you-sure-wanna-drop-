package com.fixpot47.yousurewannadrop;

import net.minecraft.world.item.ItemStack;

public final class DropApprovalState {
    private static int approvedSlot = -1;
    private static ItemStack approvedItem = ItemStack.EMPTY;

    private DropApprovalState() {
    }

    public static boolean isApproved(int slot, ItemStack stack) {
        return approvedSlot == slot
                && !stack.isEmpty()
                && ItemStack.isSameItemSameComponents(approvedItem, stack);
    }

    public static void approve(int slot, ItemStack stack) {
        approvedSlot = slot;
        approvedItem = stack.copyWithCount(1);
    }

    public static void clear() {
        approvedSlot = -1;
        approvedItem = ItemStack.EMPTY;
    }
}
