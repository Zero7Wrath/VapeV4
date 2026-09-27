package com.zero7wrath.clientbase.modules.features;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;

public class FastPlace extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();

    public FastPlace() {
        super("FastPlace", "Removes the delay between block placements.", Category.World);
    }

    @Override
    public void onUpdate() {
        if (mc.rightClickDelayTimer > 0) mc.rightClickDelayTimer = 0;
    }
}
