package com.zero7wrath.clientbase.modules.features.blatant;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;
public class AntiFall extends Module { private final Minecraft mc=Minecraft.getMinecraft(); public AntiFall(){super("AntiFall","Prevents fall distance from building.",Category.Blatant);} public void onUpdate(){if(mc.player!=null)mc.player.fallDistance=0.0F;} }