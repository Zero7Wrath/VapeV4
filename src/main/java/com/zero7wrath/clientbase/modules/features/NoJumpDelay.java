package com.zero7wrath.clientbase.modules.features;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;

public class NoJumpDelay extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();

    public NoJumpDelay() {
        super("NoJumpDelay", "Removes the delay between jumps.", Category.Blatant);
    }

    @Override
    public void onUpdate() {
        if (mc.player != null) mc.player.jumpTicks = 0;
    }
}
