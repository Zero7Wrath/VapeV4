package com.zero7wrath.clientbase.modules.features.core;

import com.zero7wrath.clientbase.Category;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;

public final class BulkModuleLogic {
    private BulkModuleLogic() {}

    public static void tick(Minecraft mc, Category category, int id) {
        if (mc == null || mc.player == null) return;
        switch (category) {
            case Combat: combat(mc, id); break;
            case Blatant: blatant(mc, id); break;
            case Render: render(mc, id); break;
            case Utility: utility(mc, id); break;
            case World: world(mc, id); break;
            case Inventory: inventory(mc, id); break;
            case Legit: legit(mc, id); break;
            case Client: client(mc, id); break;
            default: break;
        }
    }

    private static EntityLivingBase target(Minecraft mc, double range) {
        if (mc.world == null) return null;
        EntityLivingBase best = null;
        double bestDistance = range;
        for (Object object : mc.world.loadedEntityList) {
            if (!(object instanceof EntityLivingBase)) continue;
            EntityLivingBase entity = (EntityLivingBase)object;
            if (entity == mc.player || entity.isDead || entity.getHealth() <= 0.0F) continue;
            double distance = entity.getDistanceToEntity(mc.player);
            if (distance < bestDistance) {
                bestDistance = distance;
                best = entity;
            }
        }
        return best;
    }

    private static void face(Minecraft mc, EntityLivingBase entity, boolean pitch) {
        double dx = entity.posX - mc.player.posX;
        double dz = entity.posZ - mc.player.posZ;
        double dy = entity.posY + entity.getEyeHeight() - (mc.player.posY + mc.player.getEyeHeight());
        float yaw = (float)(Math.atan2(dz, dx) * 180.0D / Math.PI) - 90.0F;
        float targetPitch = (float)(-(Math.atan2(dy, Math.sqrt(dx * dx + dz * dz)) * 180.0D / Math.PI));
        mc.player.rotationYaw += clampAngle(yaw - mc.player.rotationYaw) * 0.35F;
        if (pitch) mc.player.rotationPitch += clampAngle(targetPitch - mc.player.rotationPitch) * 0.35F;
    }

    private static float clampAngle(float angle) {
        while (angle > 180.0F) angle -= 360.0F;
        while (angle < -180.0F) angle += 360.0F;
        return angle;
    }

    private static void combat(Minecraft mc, int id) {
        EntityLivingBase t = target(mc, 6.0D + (id % 5));
        if (t == null) return;
        switch (id % 7) {
            case 0: face(mc, t, false); break;
            case 1: face(mc, t, true); break;
            case 2: mc.player.setSprinting(true); break;
            case 3: if (mc.player.onGround && mc.player.getDistanceToEntity(t) < 4.0D) mc.player.jump(); break;
            case 4: face(mc, t, true); mc.player.setSprinting(true); break;
            case 5: mc.player.moveStrafing = mc.player.moveStrafing == 0.0F ? 0.25F : mc.player.moveStrafing; break;
            case 6: if (mc.player.ticksExisted % 4 == 0) face(mc, t, false); break;
        }
    }

    private static void blatant(Minecraft mc, int id) {
        switch (id % 10) {
            case 0: if (mc.player.onGround && mc.player.moveForward != 0.0F) mc.player.jump(); break;
            case 1: if (mc.player.moveForward != 0.0F || mc.player.moveStrafing != 0.0F) mc.player.setSprinting(true); break;
            case 2: if (mc.player.onGround && mc.player.moveForward != 0.0F) mc.player.motionY = 0.42D; break;
            case 3: if (!mc.player.onGround && mc.player.motionY < 0.0D) mc.player.motionY = -0.08D; break;
            case 4: if (mc.player.onGround && mc.player.moveForward != 0.0F) mc.player.motionY = 0.55D; break;
            case 5: mc.player.capabilities.isFlying = true; break;
            case 6: mc.player.motionX *= 1.02D; mc.player.motionZ *= 1.02D; break;
            case 7: if (mc.player.onGround) mc.player.motionY = Math.max(mc.player.motionY, 0.30D); break;
            case 8: mc.player.setSprinting(mc.player.moveForward != 0.0F); break;
            case 9: if (mc.player.onGround && mc.player.ticksExisted % 5 == 0) mc.player.jump(); break;
        }
    }

    private static void render(Minecraft mc, int id) {
        switch (id % 10) {
            case 0: mc.gameSettings.fovSetting = 70.0F + (id % 6) * 5.0F; break;
            case 1: mc.gameSettings.gammaSetting = 1000.0F; break;
            case 2: mc.gameSettings.thirdPersonView = 1; break;
            case 3: mc.gameSettings.thirdPersonView = 0; break;
            case 4: mc.gameSettings.viewBobbing = false; break;
            case 5: mc.gameSettings.hideGUI = true; break;
            case 6: mc.gameSettings.fovSetting = 90.0F; break;
            case 7: mc.gameSettings.thirdPersonView = 2; break;
            case 8: mc.gameSettings.viewBobbing = true; break;
            case 9: mc.gameSettings.hideGUI = false; break;
        }
    }

    private static void utility(Minecraft mc, int id) {
        switch (id % 10) {
            case 0: mc.gameSettings.keyBindForward.pressed = true; break;
            case 1: if (!mc.player.isSneaking() && mc.player.moveForward != 0.0F) mc.player.setSprinting(true); break;
            case 2: if (mc.player.onGround && mc.player.moveForward != 0.0F) mc.player.jump(); break;
            case 3: mc.rightClickDelayTimer = 0; break;
            case 4: if (mc.player.isDead || mc.player.getHealth() <= 0.0F) mc.player.respawnPlayer(); break;
            case 5: if (mc.player.moveForward == 0.0F && mc.player.moveStrafing == 0.0F) mc.player.rotationPitch *= 0.98F; break;
            case 6: mc.player.rotationPitch = 0.0F; break;
            case 7: mc.player.rotationYaw = Math.round(mc.player.rotationYaw / 45.0F) * 45.0F; break;
            case 8: mc.player.setSneaking(false); break;
            case 9: mc.gameSettings.keyBindForward.pressed = true; break;
        }
    }

    private static void world(Minecraft mc, int id) {
        if (mc.world == null) return;
        switch (id % 10) {
            case 0: mc.world.setWorldTime(6000L); break;
            case 1: mc.world.setWorldTime(1000L); break;
            case 2: mc.world.setWorldTime(13000L); break;
            case 3: mc.world.setWorldTime(0L); break;
            case 4: mc.world.setThunderStrength(0.0F); break;
            case 5: mc.world.setRainStrength(0.0F); mc.world.setThunderStrength(0.0F); break;
            case 6: mc.world.setRainStrength(1.0F); break;
            case 7: if (mc.player.posY < 5.0D && mc.player.motionY < 0.0D) mc.player.motionY = -0.03D; break;
            case 8: if (!mc.player.onGround && mc.player.posY < 10.0D && mc.player.motionY < 0.0D) mc.player.motionY = -0.05D; break;
            case 9: if (mc.player.moveForward != 0.0F) mc.player.setSprinting(true); break;
        }
    }

    private static void inventory(Minecraft mc, int id) {
        int slot = id % 9;
        if (mc.player.ticksExisted % (5 + (id % 6)) == 0) mc.player.inventory.currentItem = slot;
    }

    private static void legit(Minecraft mc, int id) {
        switch (id % 10) {
            case 0: if (mc.player.moveForward > 0.0F && !mc.player.isSneaking()) mc.player.setSprinting(true); break;
            case 1: if (mc.player.onGround && mc.gameSettings.keyBindJump.isKeyDown()) mc.player.jump(); break;
            case 2: mc.gameSettings.fovSetting = 90.0F; break;
            case 3: mc.gameSettings.gammaSetting = 1.0F; break;
            case 4: mc.player.rotationPitch = Math.max(-90.0F, Math.min(90.0F, mc.player.rotationPitch)); break;
            case 5: if (mc.player.isSneaking()) mc.player.setSprinting(false); break;
            case 6: if (mc.player.moveStrafing != 0.0F) mc.player.setSprinting(true); break;
            case 7: mc.gameSettings.viewBobbing = true; break;
            case 8: mc.gameSettings.thirdPersonView = 0; break;
            case 9: if (mc.player.onGround) mc.player.motionY = Math.min(mc.player.motionY, 0.42D); break;
        }
    }

    private static void client(Minecraft mc, int id) {
        switch (id % 10) {
            case 0: if (mc.currentScreen == null && mc.player.ticksExisted % 20 == 0) mc.displayGuiScreen(new net.minecraft.client.gui.ClickGuiScreen()); break;
            case 1: com.zero7wrath.clientbase.gui.GuiTheme.setMode("RGB"); break;
            case 2: com.zero7wrath.clientbase.gui.GuiTheme.setMode("RAINBOW"); break;
            case 3: com.zero7wrath.clientbase.gui.GuiTheme.setMode("GREEN"); break;
            case 4: com.zero7wrath.clientbase.gui.GuiTheme.setSpeed(1.0F); break;
            case 5: com.zero7wrath.clientbase.gui.GuiTheme.setSpeed(0.15F); break;
            default: break;
        }
    }
}
