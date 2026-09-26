package com.zero7wrath.clientbase.modules.features;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;
public class LongJump extends Module { private final Minecraft mc=Minecraft.getMinecraft(); public LongJump(){super("LongJump","Boosts jump distance.",Category.Blatant);} public void onUpdate(){if(mc.player==null)return; if(mc.player.onGround&&mc.player.moveForward!=0.0F){mc.player.jump(); boost(1.6D);} else boost(1.12D);} private void boost(double m){double x=mc.player.motionX,z=mc.player.motionZ,l=Math.sqrt(x*x+z*z);if(l>0.001D){mc.player.motionX=x/l*Math.min(.75D,l*m);mc.player.motionZ=z/l*Math.min(.75D,l*m);}} }