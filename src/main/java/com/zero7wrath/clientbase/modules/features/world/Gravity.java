package com.zero7wrath.clientbase.modules.features.world;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;
public class Gravity extends Module { private final Minecraft mc=Minecraft.getMinecraft(); public Gravity(){super("Gravity","Reduces effective gravity.",Category.World);} public void onUpdate(){if(mc.player!=null&&!mc.player.onGround&&!mc.player.capabilities.isFlying)mc.player.motionY+=0.03D;} }