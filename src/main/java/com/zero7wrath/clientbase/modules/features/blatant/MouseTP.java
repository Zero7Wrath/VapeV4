package com.zero7wrath.clientbase.modules.features.blatant;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
public class MouseTP extends Module { private final Minecraft mc=Minecraft.getMinecraft(); public MouseTP(){super("MouseTP","Teleports to the block under the cursor.",Category.Blatant);} public void onUpdate(){if(mc.player==null||mc.objectMouseOver==null||mc.objectMouseOver.getBlockPos()==null||mc.player.ticksExisted%5!=0)return;BlockPos p=mc.objectMouseOver.getBlockPos();mc.player.setPosition(p.getX()+.5D,p.getY()+1D,p.getZ()+.5D);} }