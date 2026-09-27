package com.zero7wrath.clientbase.modules.features;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import com.zero7wrath.clientbase.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

public class AimAssist extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();
    public final Setting.NumberSetting range = new Setting.NumberSetting("Range", 4.5D, 2.0D, 8.0D, 0.1D);
    public final Setting.NumberSetting strength = new Setting.NumberSetting("Strength", 0.35D, 0.05D, 1.0D, 0.05D);

    public AimAssist() {
        super("AimAssist", "Smoothly nudges your aim toward the nearest player.", Category.Combat);
        settings.add(range);
        settings.add(strength);
    }

    @Override
    public void onUpdate() {
        if (mc.player == null || mc.world == null) return;
        EntityPlayer target = null;
        double best = range.getValue();
        for (Entity entity : mc.world.loadedEntityList) {
            if (!(entity instanceof EntityPlayer) || entity == mc.player || entity.isDead) continue;
            double distance = mc.player.getDistance(entity.posX, entity.posY, entity.posZ);
            if (distance < best) { best = distance; target = (EntityPlayer) entity; }
        }
        if (target == null) return;
        double dx = target.posX - mc.player.posX;
        double dz = target.posZ - mc.player.posZ;
        double dy = target.posY + target.getEyeHeight() - (mc.player.posY + mc.player.getEyeHeight());
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        float wantedYaw = (float)(Math.toDegrees(Math.atan2(dz, dx)) - 90.0D);
        float wantedPitch = (float)(-Math.toDegrees(Math.atan2(dy, horizontal)));
        float s = strength.getValue().floatValue();
        mc.player.rotationYaw += MathHelper.wrapDegrees(wantedYaw - mc.player.rotationYaw) * s;
        mc.player.rotationPitch += MathHelper.wrapDegrees(wantedPitch - mc.player.rotationPitch) * s;
    }
}
