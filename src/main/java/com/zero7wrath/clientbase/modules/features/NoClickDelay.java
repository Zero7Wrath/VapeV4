package com.zero7wrath.clientbase.modules.features;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;

public class NoClickDelay extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();

    public NoClickDelay() {
        super("NoClickDelay", "Removes the client-side right-click delay.", Category.Combat);
    }

    @Override
    public void onUpdate() {
        if (mc.rightClickDelayTimer > 0) mc.rightClickDelayTimer = 0;
    }
}
