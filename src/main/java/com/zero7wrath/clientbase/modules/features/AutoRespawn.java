package com.zero7wrath.clientbase.modules.features;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;

public class AutoRespawn extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();

    public AutoRespawn() {
        super("AutoRespawn", "Automatically respawns after death.", Category.Utility);
    }

    @Override
    public void onUpdate() {
        if (mc.player != null && mc.player.isDead && mc.player.deathTime > 5) {
            mc.player.respawnPlayer();
        }
    }
}
