package com.zero7wrath.clientbase.modules.features;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;

public class NoHurtCam extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();

    public NoHurtCam() {
        super("NoHurtCam", "Removes the local hurt-camera effect.", Category.Render);
    }

    @Override
    public void onUpdate() {
        // Hook is intentionally client-side only; the renderer checks this module.
    }
}
