package com.zero7wrath.clientbase.modules.features.blatant;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;
public class HighJump extends Module { private final Minecraft mc=Minecraft.getMinecraft(); public HighJump(){super("HighJump","Increases jump height.",Category.Blatant);} public void onUpdate(){if(mc.player!=null&&mc.player.onGround&&mc.player.moveForward!=0.0F)mc.player.motionY=0.75D;} }