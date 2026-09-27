package com.zero7wrath.clientbase.modules.features.render;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;
public class FOV extends Module { private final Minecraft mc=Minecraft.getMinecraft(); private float old; public FOV(){super("FOV","Changes field of view.",Category.Legit);} public void onEnable(){old=mc.gameSettings.fovSetting;} public void onUpdate(){mc.gameSettings.fovSetting=110.0F;} public void onDisable(){mc.gameSettings.fovSetting=old;} }