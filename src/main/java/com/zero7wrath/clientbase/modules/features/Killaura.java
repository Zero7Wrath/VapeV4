package com.zero7wrath.clientbase.modules.features;
import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
public class Killaura extends Module {
 private final Minecraft mc=Minecraft.getMinecraft();
 public Killaura(){super("Killaura","Attacks nearby players.",Category.Blatant);}
 public void onUpdate(){if(mc.player==null||mc.world==null)return; EntityPlayer t=null;double best=4.5D;
  for(Entity e:mc.world.loadedEntityList)if(e instanceof EntityPlayer&&e!=mc.player&&!e.isDead&&mc.player.getDistance(e.posX, e.posY, e.posZ)<best){best=mc.player.getDistance(e.posX, e.posY, e.posZ);t=(EntityPlayer)e;}
  if(t!=null&&mc.player.getCooledAttackStrength(0.0F)>=.9F){double dx=t.posX-mc.player.posX,dz=t.posZ-mc.player.posZ,dy=t.posY+t.getEyeHeight()-(mc.player.posY+mc.player.getEyeHeight()),h=Math.sqrt(dx*dx+dz*dz);mc.player.rotationYaw=MathHelper.wrapDegrees((float)(Math.toDegrees(Math.atan2(dz,dx))-90));mc.player.rotationPitch=MathHelper.clamp((float)-Math.toDegrees(Math.atan2(dy,h)),-90F,90F);mc.playerController.attackEntity(mc.player,t);mc.player.swingArm(EnumHand.MAIN_HAND);}
 }
}