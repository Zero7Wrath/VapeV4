package com.zero7wrath.clientbase.modules.features;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;

public class NoPush extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();

    public NoPush() {
        super("NoPush", "Prevents normal player/entity push effects.", Category.Utility);
    }

    @Override
    public void onUpdate() {
        if (mc.player != null) {
            mc.player.isAirBorne = mc.player.isAirBorne;
        }
    }
}
