package com.zero7wrath.clientbase.modules.features.blatant;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;
public class Spider extends Module { private final Minecraft mc=Minecraft.getMinecraft(); public Spider(){super("Spider","Climbs horizontal surfaces.",Category.Blatant);} public void onUpdate(){if(mc.player!=null&&mc.player.isCollidedHorizontally&&!mc.player.isSneaking())mc.player.motionY=0.20D;} }