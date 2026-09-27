package com.zero7wrath.clientbase.modules.features.blatant;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;
public class Jesus extends Module { private final Minecraft mc=Minecraft.getMinecraft(); public Jesus(){super("Jesus","Keeps the player moving on water.",Category.Blatant);} public void onUpdate(){if(mc.player!=null&&mc.player.isInWater()&&!mc.player.isSneaking())mc.player.motionY=0.10D;} }