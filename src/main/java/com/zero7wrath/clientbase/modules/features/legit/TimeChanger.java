package com.zero7wrath.clientbase.modules.features.legit;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;
public class TimeChanger extends Module { private final Minecraft mc=Minecraft.getMinecraft(); public TimeChanger(){super("TimeChanger","Changes client world time.",Category.Legit);} public void onUpdate(){if(mc.world!=null)mc.world.setWorldTime(6000L);} }