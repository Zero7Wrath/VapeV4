package com.zero7wrath.clientbase.modules.features.world;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class AutoTool extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();

    public AutoTool() {
        super("AutoTool", "Selects the strongest hotbar tool for the block under your cursor.", Category.World);
    }

    @Override
    public void onUpdate() {
        if (mc.player == null || mc.objectMouseOver == null || mc.objectMouseOver.getBlockPos() == null) return;
        BlockPos pos = mc.objectMouseOver.getBlockPos();
        Block block = mc.world.getBlockState(pos).getBlock();
        int bestSlot = mc.player.inventory.currentItem;
        float bestSpeed = 1.0F;
        for (int slot = 0; slot < 9; slot++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(slot);
            if (stack.func_190926_b()) continue;
            float speed = stack.getStrVsBlock(mc.world.getBlockState(pos));
            if (speed > bestSpeed) { bestSpeed = speed; bestSlot = slot; }
        }
        mc.player.inventory.currentItem = bestSlot;
    }
}
