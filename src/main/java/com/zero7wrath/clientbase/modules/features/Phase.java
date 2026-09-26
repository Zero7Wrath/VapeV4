package com.zero7wrath.clientbase.modules.features;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;

public class Phase extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();

    public Phase() {
        super("Phase", "Disables collision with blocks while enabled.", Category.Blatant);
    }

    @Override
    public void onEnable() {
        if (mc.player != null) mc.player.noClip = true;
    }

    @Override
    public void onDisable() {
        if (mc.player != null) mc.player.noClip = false;
    }

    @Override
    public void onUpdate() {
        if (mc.player != null) mc.player.noClip = true;
    }
}
