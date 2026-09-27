package com.zero7wrath.clientbase.modules.features.blatant;
import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
public class TargetStrafe extends Module {
 private final Minecraft mc=Minecraft.getMinecraft();
 public TargetStrafe(){super("TargetStrafe","Circles a nearby player.",Category.Blatant);}
 public void onUpdate(){if(mc.player==null||mc.world==null)return;EntityPlayer t=null;double best=4.5D;for(Entity e:mc.world.loadedEntityList)if(e instanceof EntityPlayer&&e!=mc.player&&!e.isDead&&mc.player.getDistance(e.posX, e.posY, e.posZ)<best){best=mc.player.getDistance(e.posX, e.posY, e.posZ);t=(EntityPlayer)e;}if(t!=null){double a=mc.player.ticksExisted*.18D,x=t.posX+Math.cos(a)*2.5D,z=t.posZ+Math.sin(a)*2.5D;mc.player.motionX=(x-mc.player.posX)*.25D;mc.player.motionZ=(z-mc.player.posZ)*.25D;}}
}