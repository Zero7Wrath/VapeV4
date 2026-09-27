package com.zero7wrath.clientbase.modules.features.utility;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;

public class SafeWalk extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();

    public SafeWalk() {
        super("SafeWalk", "Stops horizontal movement at block edges.", Category.World);
    }

    @Override
    public void onUpdate() {
        if (mc.player == null || !mc.player.onGround || mc.player.isSneaking()) return;
        double nx = mc.player.posX + mc.player.motionX;
        double nz = mc.player.posZ + mc.player.motionZ;
        if (mc.world.isAirBlock(new BlockPos(nx, mc.player.posY - 1.0D, nz))) {
            mc.player.motionX = 0.0D;
            mc.player.motionZ = 0.0D;
        }
    }
}
