package com.zero7wrath.clientbase.modules.features.combat;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;

public class Criticals extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();

    public Criticals() {
        super("Criticals", "Adds a small client-side hop when an attack starts on the ground.", Category.Combat);
    }

    @Override
    public void onUpdate() {
        if (mc.player == null || mc.objectMouseOver == null || mc.objectMouseOver.entityHit == null) return;
        if (mc.player.onGround && mc.player.swingProgressInt > 0) mc.player.motionY = 0.08D;
    }
}
