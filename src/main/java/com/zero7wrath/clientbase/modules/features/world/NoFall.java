package com.zero7wrath.clientbase.modules.features.world;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;

public class NoFall extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();

    public NoFall() {
        super("NoFall", "Prevents fall-distance damage from accumulating.", Category.Blatant);
    }

    @Override
    public void onUpdate() {
        if (mc.player != null) mc.player.fallDistance = 0.0F;
    }
}
