package com.zero7wrath.clientbase.modules.features.blatant;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;
public class SpinBot extends Module { private final Minecraft mc=Minecraft.getMinecraft(); public SpinBot(){super("SpinBot","Rotates the player's view.",Category.Blatant);} public void onUpdate(){if(mc.player!=null){mc.player.rotationYaw+=45.0F;mc.player.rotationPitch=0.0F;}} }