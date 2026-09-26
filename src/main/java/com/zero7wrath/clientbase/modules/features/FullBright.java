package com.zero7wrath.clientbase.modules.features;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import com.zero7wrath.clientbase.settings.Setting;
import net.minecraft.client.Minecraft;

public class FullBright extends Module {

    public final Setting.NumberSetting gamma =
            new Setting.NumberSetting("Gamma", 1000.0, 1.0, 1000.0, 1.0);

    private float previousGamma = 1.0f;

    public FullBright() {
        super("Fullbright", "Changes the brightness of the world.", Category.Render);
        settings.add(gamma);
    }

    @Override
    public void onEnable() {
        Minecraft mc = Minecraft.getMinecraft();
        previousGamma = mc.gameSettings.gammaSetting;
        applyGamma();
    }

    @Override
    public void onDisable() {
        Minecraft.getMinecraft().gameSettings.gammaSetting = previousGamma;
    }

    @Override
    public void onUpdate() {
        applyGamma();
    }

    private void applyGamma() {
        Minecraft.getMinecraft().gameSettings.gammaSetting =
                gamma.getValue().floatValue();
    }
}
