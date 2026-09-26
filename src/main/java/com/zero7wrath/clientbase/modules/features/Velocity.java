package com.zero7wrath.clientbase.modules.features;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import com.zero7wrath.clientbase.settings.Setting;
import net.minecraft.client.Minecraft;

public class Velocity extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();
    public final Setting.NumberSetting horizontal =
            new Setting.NumberSetting("Horizontal", 0.0D, 0.0D, 1.0D, 0.05D);
    public final Setting.NumberSetting vertical =
            new Setting.NumberSetting("Vertical", 0.0D, 0.0D, 1.0D, 0.05D);

    public Velocity() {
        super("Velocity", "Scales incoming knockback.", Category.Blatant);
        settings.add(horizontal);
        settings.add(vertical);
    }

    @Override
    public void onUpdate() {
        if (mc.player == null || mc.player.hurtTime <= 0) return;
        mc.player.motionX *= horizontal.getValue();
        mc.player.motionY *= vertical.getValue();
        mc.player.motionZ *= horizontal.getValue();
    }
}
