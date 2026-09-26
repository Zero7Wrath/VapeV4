package com.zero7wrath.clientbase.modules.features;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;
public class Invisible extends Module { private final Minecraft mc=Minecraft.getMinecraft(); public Invisible(){super("Invisible","Toggles local invisibility.",Category.Blatant);} public void onEnable(){if(mc.player!=null)mc.player.setInvisible(true);} public void onDisable(){if(mc.player!=null)mc.player.setInvisible(false);} }