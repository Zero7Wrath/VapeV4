package com.zero7wrath.clientbase.modules.features.combat;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import com.zero7wrath.clientbase.settings.Setting;
import net.lax1dude.eaglercraft.Mouse;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public class AutoClicker extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();
    public final Setting.NumberSetting clicks =
            new Setting.NumberSetting("CPS", 10.0D, 1.0D, 20.0D, 1.0D);

    public AutoClicker() {
        super("AutoClicker", "Repeats left-click attacks while the button is held.", Category.Combat);
        settings.add(clicks);
    }

    @Override
    public void onUpdate() {
        if (mc.player == null || mc.playerController == null || !Mouse.isButtonDown(0)) return;

        int interval = Math.max(1, (int)(20.0D / clicks.getValue()));
        if (mc.player.ticksExisted % interval != 0) return;

        if (mc.objectMouseOver != null && mc.objectMouseOver.entityHit != null) {
            Entity target = mc.objectMouseOver.entityHit;
            if (target != mc.player) {
                mc.playerController.attackEntity(mc.player, target);
                mc.player.swingArm(net.minecraft.util.EnumHand.MAIN_HAND);
            }
        }
    }
}
