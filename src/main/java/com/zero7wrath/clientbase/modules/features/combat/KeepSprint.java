package com.zero7wrath.clientbase.modules.features.combat;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;

public class KeepSprint extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();

    public KeepSprint() {
        super("KeepSprint", "Keeps sprinting while moving forward.", Category.Combat);
    }

    @Override
    public void onUpdate() {
        if (mc.player != null && !mc.player.isSneaking() && mc.player.moveForward > 0.0F) {
            mc.player.setSprinting(true);
        }
    }
}
