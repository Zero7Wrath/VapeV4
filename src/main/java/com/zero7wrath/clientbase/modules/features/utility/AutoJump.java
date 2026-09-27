package com.zero7wrath.clientbase.modules.features.utility;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;

public class AutoJump extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();

    public AutoJump() {
        super("AutoJump", "Automatically jumps while moving.", Category.Legit);
    }

    @Override
    public void onUpdate() {
        if (mc.player != null && mc.player.onGround && mc.player.moveForward != 0.0F) {
            mc.player.jump();
        }
    }
}
