package com.zero7wrath.clientbase.modules.features.legit;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;

public class Sprint extends Module {

    public Sprint() {
        super("Sprint", "Automatically sprints while moving forward.", Category.Legit);
    }

    @Override
    public void onUpdate() {
        Minecraft mc = Minecraft.getMinecraft();

        if (mc.player == null || mc.player.isSneaking()) {
            return;
        }

        if (mc.gameSettings.keyBindForward.isKeyDown()) {
            mc.player.setSprinting(true);
        }
    }
}
