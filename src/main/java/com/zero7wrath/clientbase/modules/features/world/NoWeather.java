package com.zero7wrath.clientbase.modules.features.world;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;

public class NoWeather extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();

    public NoWeather() {
        super("NoWeather", "Removes rain and thunder client-side.", Category.Render);
    }

    @Override
    public void onUpdate() {
        if (mc.world != null) {
            mc.world.setRainStrength(0.0F);
            mc.world.setThunderStrength(0.0F);
        }
    }
}
