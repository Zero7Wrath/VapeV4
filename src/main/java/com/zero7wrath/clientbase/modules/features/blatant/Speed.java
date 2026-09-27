package com.zero7wrath.clientbase.modules.features.blatant;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import com.zero7wrath.clientbase.settings.Setting;
import net.minecraft.client.Minecraft;

public class Speed extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();
    public final Setting.NumberSetting multiplier =
            new Setting.NumberSetting("Multiplier", 1.35D, 1.0D, 2.5D, 0.05D);

    public Speed() {
        super("Speed", "Increases horizontal movement speed.", Category.Blatant);
        settings.add(multiplier);
    }

    @Override
    public void onUpdate() {
        if (mc.player == null || !mc.player.onGround || mc.player.moveForward == 0.0F) return;
        mc.player.jump();
        double x = mc.player.motionX;
        double z = mc.player.motionZ;
        mc.player.motionX = x * multiplier.getValue();
        mc.player.motionZ = z * multiplier.getValue();
    }
}
