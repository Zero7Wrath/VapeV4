package com.zero7wrath.clientbase.modules.features;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class AutoWeapon extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();

    public AutoWeapon() {
        super("AutoWeapon", "Selects the strongest weapon in the hotbar before attacking.", Category.Combat);
    }

    @Override
    public void onUpdate() {
        if (mc.player == null || mc.objectMouseOver == null || mc.objectMouseOver.entityHit == null) return;
        int best = mc.player.inventory.currentItem;
        float bestDamage = -1.0F;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack.isEmpty()) continue;
            float damage = stack.getMaxDamage() > 0 ? 1.0F : 0.0F;
            if (stack.getItem().getItemUseAction(stack) == net.minecraft.item.EnumAction.BLOCK) damage = 0.5F;
            if (damage > bestDamage) { bestDamage = damage; best = i; }
        }
        mc.player.inventory.currentItem = best;
    }
}
