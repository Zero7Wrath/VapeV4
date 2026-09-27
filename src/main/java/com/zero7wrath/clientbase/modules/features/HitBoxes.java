package com.zero7wrath.clientbase.modules.features;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import com.zero7wrath.clientbase.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;

public class HitBoxes extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();
    public final Setting.NumberSetting expand = new Setting.NumberSetting("Expand", 0.20D, 0.0D, 1.0D, 0.05D);

    public HitBoxes() {
        super("HitBoxes", "Client-side target hitbox expansion for easier aiming.", Category.Combat);
        settings.add(expand);
    }

    public AxisAlignedBB getBox(Entity entity) {
        if (!(entity instanceof EntityPlayer) || entity == mc.player) return entity.getEntityBoundingBox();
        double e = expand.getValue();
        return entity.getEntityBoundingBox().expand(e, e, e);
    }
}
