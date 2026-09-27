package com.zero7wrath.clientbase.modules.features.blatant;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;
public class Wallhop extends Module { private final Minecraft mc=Minecraft.getMinecraft(); public Wallhop(){super("Wallhop","Jumps while touching walls.",Category.World);} public void onUpdate(){if(mc.player!=null&&mc.player.isCollidedHorizontally){mc.player.motionY=0.42D;mc.player.motionX*=1.1D;mc.player.motionZ*=1.1D;}} }