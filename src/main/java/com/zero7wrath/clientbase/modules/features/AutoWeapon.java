package com.zero7wrath.clientbase.modules.features;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class AutoWeapon extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();

    public AutoWeapon() {
        super("AutoWeapon", "Selects the strongest sword in the hotbar before attacking.", Category.Combat);
    }

    @Override
    public void onUpdate() {
        if (mc.player == null || mc.objectMouseOver == null || mc.objectMouseOver.entityHit == null) return;
        int best = mc.player.inventory.currentItem;
        float bestDamage = -1.0F;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack.func_190926_b()) continue;
            Item item = stack.getItem();
            float damage = item instanceof ItemSword ? ((ItemSword)item).getAttackDamage(mc.player) : 0.0F;
            if (damage > bestDamage) {
                bestDamage = damage;
                best = i;
            }
        }
        if (bestDamage >= 0.0F) mc.player.inventory.currentItem = best;
    }
}
