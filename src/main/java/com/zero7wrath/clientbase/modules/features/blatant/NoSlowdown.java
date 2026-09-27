package com.zero7wrath.clientbase.modules.features.blatant;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;

public class NoSlowdown extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();

    public NoSlowdown() {
        super("NoSlowdown", "Reduces movement slowdown while using items.", Category.Blatant);
    }

    @Override
    public void onUpdate() {
        if (mc.player == null || !mc.player.isHandActive()) return;
        if (mc.player.moveForward != 0.0F || mc.player.moveStrafing != 0.0F) {
            mc.player.motionX *= 1.20D;
            mc.player.motionZ *= 1.20D;
        }
    }
}
