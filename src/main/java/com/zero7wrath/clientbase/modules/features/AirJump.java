package com.zero7wrath.clientbase.modules.features;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;

public class AirJump extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();

    public AirJump() {
        super("AirJump", "Allows a normal jump while airborne.", Category.Blatant);
    }

    @Override
    public void onUpdate() {
        if (mc.player == null) return;
        if (mc.gameSettings.keyBindJump.isKeyDown() && mc.player.ticksExisted % 6 == 0) {
            mc.player.motionY = 0.42D;
        }
    }
}
