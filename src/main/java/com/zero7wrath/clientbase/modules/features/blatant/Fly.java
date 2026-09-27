package com.zero7wrath.clientbase.modules.features.blatant;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import com.zero7wrath.clientbase.settings.Setting;
import net.minecraft.client.Minecraft;

public class Fly extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();

    public final Setting.NumberSetting horizontal =
            new Setting.NumberSetting("Horizontal", 0.60D, 0.10D, 2.00D, 0.05D);
    public final Setting.NumberSetting vertical =
            new Setting.NumberSetting("Vertical", 0.42D, 0.10D, 1.00D, 0.01D);

    public Fly() {
        super("Fly", "Client-side flight movement.", Category.Blatant);
        settings.add(horizontal);
        settings.add(vertical);
    }

    @Override
    public void onEnable() {
        if (mc.player != null) {
            mc.player.fallDistance = 0.0F;
            mc.player.capabilities.isFlying = true;
        }
    }

    @Override
    public void onDisable() {
        if (mc.player != null) {
            mc.player.capabilities.isFlying = false;
            mc.player.motionY = 0.0D;
        }
    }

    @Override
    public void onUpdate() {
        if (mc.player == null) return;

        mc.player.capabilities.isFlying = true;
        mc.player.fallDistance = 0.0F;

        float forward = mc.player.moveForward;
        float strafe = mc.player.moveStrafing;
        float yaw = (float) Math.toRadians(mc.player.rotationYaw);

        double speed = horizontal.getValue();
        double mx = -Math.sin(yaw) * forward + Math.cos(yaw) * strafe;
        double mz = Math.cos(yaw) * forward + Math.sin(yaw) * strafe;
        double length = Math.sqrt(mx * mx + mz * mz);

        if (length > 0.001D) {
            mx /= length;
            mz /= length;
            mc.player.motionX = mx * speed;
            mc.player.motionZ = mz * speed;
        } else {
            mc.player.motionX = 0.0D;
            mc.player.motionZ = 0.0D;
        }

        if (mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.player.motionY = vertical.getValue();
        } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            mc.player.motionY = -vertical.getValue();
        } else {
            mc.player.motionY = 0.0D;
        }
    }
}
