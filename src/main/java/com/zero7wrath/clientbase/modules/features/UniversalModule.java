package com.zero7wrath.clientbase.modules.features;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.Client;
import com.zero7wrath.clientbase.modules.Module;
import com.zero7wrath.clientbase.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;

public class UniversalModule extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();

    public final Setting.NumberSetting speed = new Setting.NumberSetting("Speed", 1.25D, 1.0D, 3.0D, 0.05D);
    public final Setting.NumberSetting range = new Setting.NumberSetting("Range", 4.5D, 2.5D, 6.0D, 0.1D);
    public final Setting.NumberSetting height = new Setting.NumberSetting("Height", 0.42D, 0.1D, 1.0D, 0.01D);

    public UniversalModule(String name, Category category) {
        super(name, "Vape V4 " + name + " module.", category);
        if (isOneOf(name, "Speed", "Fly", "Killaura", "AimAssist", "Reach", "HighJump")) {
            settings.add(name.equals("Killaura") || name.equals("AimAssist") || name.equals("Reach") ? range : speed);
        }
    }

    public UniversalModule(String name, Category category, Setting<?>... settings) {
        this(name, category);
        if (settings != null) for (Setting<?> setting : settings) this.settings.add(setting);
    }

    @Override
    public void onEnable() {
        if (mc.player == null) return;
        if (getName().equalsIgnoreCase("Fly")) {
            mc.player.capabilities.isFlying = true;
            mc.player.motionY = 0.0D;
        }
    }

    @Override
    public void onDisable() {
        if (mc.player == null) return;
        if (getName().equalsIgnoreCase("Fly")) mc.player.capabilities.isFlying = false;
        if (getName().equalsIgnoreCase("FOV")) mc.gameSettings.fovSetting = 70.0F;
    }

    @Override
    public void onUpdate() {
        if (mc.player == null || mc.world == null) return;
        String n = getName();

        if (n.equalsIgnoreCase("Sprint")) {
            mc.player.setSprinting(mc.player.moveForward > 0.0F && !mc.player.isSneaking());
        } else if (n.equalsIgnoreCase("Speed")) {
            if (mc.player.onGround && mc.player.moveForward != 0.0F) mc.player.jump();
            multiplyHorizontal(speed.getValue().doubleValue());
        } else if (n.equalsIgnoreCase("Fly")) {
            mc.player.capabilities.isFlying = true;
            mc.player.motionY = mc.gameSettings.keyBindJump.isKeyDown() ? 0.42D :
                    (mc.gameSettings.keyBindSneak.isKeyDown() ? -0.42D : 0.0D);
            multiplyHorizontal(1.35D);
        } else if (n.equalsIgnoreCase("LongJump")) {
            if (mc.player.onGround && mc.player.moveForward != 0.0F) {
                mc.player.jump();
                multiplyHorizontal(1.65D);
            } else {
                multiplyHorizontal(1.15D);
            }
        } else if (n.equalsIgnoreCase("Wallhop")) {
            if (mc.player.isCollidedHorizontally) {
                mc.player.motionY = 0.42D;
                multiplyHorizontal(1.15D);
            }
        } else if (n.equalsIgnoreCase("Swim")) {
            if (mc.player.isInWater() && mc.gameSettings.keyBindJump.isKeyDown()) {
                mc.player.motionY = 0.30D;
            }
        } else if (n.equalsIgnoreCase("SpinBot")) {
            mc.player.rotationYaw += 45.0F;
            mc.player.rotationPitch = 0.0F;
        } else if (n.equalsIgnoreCase("Invisible")) {
            mc.player.setInvisible(true);
        } else if (n.equalsIgnoreCase("TargetStrafe")) {
            EntityPlayer target = findTarget(range.getValue().doubleValue());
            if (target != null) {
                double angle = mc.player.ticksExisted * 0.18D;
                double x = target.posX + Math.cos(angle) * 2.5D;
                double z = target.posZ + Math.sin(angle) * 2.5D;
                mc.player.motionX = (x - mc.player.posX) * 0.25D;
                mc.player.motionZ = (z - mc.player.posZ) * 0.25D;
                face(target);
            }
        } else if (n.equalsIgnoreCase("MouseTP")) {
            if (mc.objectMouseOver != null && mc.objectMouseOver.getBlockPos() != null
                    && mc.player.ticksExisted % 5 == 0) {
                net.minecraft.util.math.BlockPos pos = mc.objectMouseOver.getBlockPos();
                mc.player.setPosition(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D);
            }
        } else if (n.equalsIgnoreCase("HighJump")) {
            if (mc.player.onGround && mc.player.moveForward != 0.0F) mc.player.motionY = height.getValue().doubleValue();
        } else if (n.equalsIgnoreCase("AntiFall")) {
            mc.player.fallDistance = 0.0F;
        } else if (n.equalsIgnoreCase("Jesus")) {
            if (mc.player.isInWater() && !mc.player.isSneaking()) mc.player.motionY = 0.10D;
        } else if (n.equalsIgnoreCase("Spider")) {
            if (mc.player.isCollidedHorizontally && !mc.player.isSneaking()) mc.player.motionY = 0.20D;
        } else if (n.equalsIgnoreCase("Gravity")) {
            if (!mc.player.onGround && !mc.player.capabilities.isFlying) mc.player.motionY += 0.03D;
        } else if (n.equalsIgnoreCase("Killaura")) {
            EntityPlayer target = findTarget(range.getValue().doubleValue());
            if (target != null && mc.player.getCooledAttackStrength(0.0F) >= 0.9F) {
                face(target);
                mc.playerController.attackEntity(mc.player, target);
                mc.player.swingArm(EnumHand.MAIN_HAND);
            }
        } else if (n.equalsIgnoreCase("AimAssist")) {
            EntityPlayer target = findTarget(range.getValue().doubleValue());
            if (target != null) face(target);
        } else if (n.equalsIgnoreCase("TriggerBot")) {
            if (mc.objectMouseOver != null && mc.objectMouseOver.entityHit instanceof EntityPlayer
                    && mc.objectMouseOver.entityHit != mc.player
                    && mc.player.getCooledAttackStrength(0.0F) >= 0.9F) {
                mc.playerController.attackEntity(mc.player, mc.objectMouseOver.entityHit);
                mc.player.swingArm(EnumHand.MAIN_HAND);
            }
        } else if (n.equalsIgnoreCase("Anti-AFK")) {
            if (mc.player.ticksExisted % 80 == 0) mc.player.jump();
        } else if (n.equalsIgnoreCase("TimeChanger")) {
            mc.world.setWorldTime(6000L);
        } else if (n.equalsIgnoreCase("FOV")) {
            mc.gameSettings.fovSetting = 110.0F;
        } else if (n.equalsIgnoreCase("Panic")) {
            for (Module module : Client.manager.getModules()) if (module != this) module.setEnabled(false);
        }
    }

    private void multiplyHorizontal(double multiplier) {
        double x = mc.player.motionX, z = mc.player.motionZ;
        double length = Math.sqrt(x * x + z * z);
        if (length > 0.001D) {
            mc.player.motionX = x / length * Math.min(0.75D, length * multiplier);
            mc.player.motionZ = z / length * Math.min(0.75D, length * multiplier);
        }
    }

    private EntityPlayer findTarget(double maxRange) {
        EntityPlayer best = null;
        double bestDistance = maxRange;
        for (Entity entity : mc.world.loadedEntityList) {
            if (!(entity instanceof EntityPlayer) || entity == mc.player || entity.isDead) continue;
            double distance = mc.player.getDistance(entity);
            if (distance < bestDistance) {
                bestDistance = distance;
                best = (EntityPlayer) entity;
            }
        }
        return best;
    }

    private void face(Entity entity) {
        double dx = entity.posX - mc.player.posX, dz = entity.posZ - mc.player.posZ;
        double dy = entity.posY + entity.getEyeHeight() - (mc.player.posY + mc.player.getEyeHeight());
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        float yaw = (float) (Math.toDegrees(Math.atan2(dz, dx)) - 90.0D);
        float pitch = (float) (-Math.toDegrees(Math.atan2(dy, horizontal)));
        mc.player.rotationYaw = MathHelper.wrapDegrees(yaw);
        mc.player.rotationPitch = MathHelper.clamp(pitch, -90.0F, 90.0F);
    }

    private static boolean isOneOf(String value, String... values) {
        for (String v : values) if (v.equalsIgnoreCase(value)) return true;
        return false;
    }
}
