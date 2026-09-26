package com.zero7wrath.clientbase.modules.features;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;
public class Swim extends Module { private final Minecraft mc=Minecraft.getMinecraft(); public Swim(){super("Swim","Adds vertical control in water.",Category.Blatant);} public void onUpdate(){if(mc.player!=null&&mc.player.isInWater()&&mc.gameSettings.keyBindJump.isKeyDown())mc.player.motionY=0.30D;} }