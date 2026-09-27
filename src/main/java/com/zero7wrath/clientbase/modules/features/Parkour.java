package com.zero7wrath.clientbase.modules.features;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;

public class Parkour extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();

    public Parkour() {
        super("Parkour", "Automatically jumps near the edge of blocks.", Category.World);
    }

    @Override
    public void onUpdate() {
        if (mc.player == null || !mc.player.onGround || mc.player.isSneaking()) return;
        if (mc.player.moveForward == 0.0F && mc.player.moveStrafing == 0.0F) return;
        double aheadX = mc.player.posX + mc.player.motionX * 2.0D;
        double aheadZ = mc.player.posZ + mc.player.motionZ * 2.0D;
        BlockPos below = new BlockPos(aheadX, mc.player.posY - 1.0D, aheadZ);
        if (mc.world.isAirBlock(below)) mc.player.jump();
    }
}
