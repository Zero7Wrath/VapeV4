package com.zero7wrath.clientbase.modules.features;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;
public class Fly extends Module { private final Minecraft mc=Minecraft.getMinecraft(); public Fly(){super("Fly","Creative-style flight.",Category.Blatant);} public void onEnable(){if(mc.player!=null)mc.player.capabilities.isFlying=true;} public void onDisable(){if(mc.player!=null)mc.player.capabilities.isFlying=false;} public void onUpdate(){if(mc.player==null)return; mc.player.capabilities.isFlying=true; mc.player.motionY=mc.gameSettings.keyBindJump.isKeyDown()?0.42D:(mc.gameSettings.keyBindSneak.isKeyDown()?-0.42D:0.0D);} }