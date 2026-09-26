package com.zero7wrath.clientbase.modules.features;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer; import net.minecraft.util.EnumHand;
public class TriggerBot extends Module { private final Minecraft mc=Minecraft.getMinecraft(); public TriggerBot(){super("TriggerBot","Attacks the entity under the crosshair.",Category.Combat);} public void onUpdate(){if(mc.player==null||mc.objectMouseOver==null)return; if(mc.objectMouseOver.entityHit instanceof EntityPlayer&&mc.objectMouseOver.entityHit!=mc.player&&mc.player.getCooledAttackStrength(0.0F)>=.9F){mc.playerController.attackEntity(mc.player,mc.objectMouseOver.entityHit);mc.player.swingArm(EnumHand.MAIN_HAND);}} }