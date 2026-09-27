package com.zero7wrath.clientbase.modules.features.combat;
import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
public class Killaura extends Module {
 private final Minecraft mc=Minecraft.getMinecraft();
 public Killaura(){super("Killaura","Attacks the nearest living target in range.",Category.Combat);}
 public void onUpdate(){
  if(mc.player==null||mc.world==null||mc.playerController==null)return;
  EntityLivingBase target=null; double best=4.5D;
  for(Entity e:mc.world.loadedEntityList){
   if(!(e instanceof EntityLivingBase)||e==mc.player||e.isDead)continue;
   EntityLivingBase living=(EntityLivingBase)e;
   if(living.getHealth()<=0.0F)continue;
   double d=mc.player.getDistance(e.posX,e.posY,e.posZ);
   if(d<best){best=d;target=living;}
  }
  if(target!=null&&mc.player.getCooledAttackStrength(0.0F)>=0.85F){
   double dx=target.posX-mc.player.posX,dz=target.posZ-mc.player.posZ;
   double dy=target.posY+target.getEyeHeight()-(mc.player.posY+mc.player.getEyeHeight());
   double h=Math.sqrt(dx*dx+dz*dz);
   mc.player.rotationYaw=MathHelper.wrapDegrees((float)(Math.toDegrees(Math.atan2(dz,dx))-90.0D));
   mc.player.rotationPitch=MathHelper.clamp((float)-Math.toDegrees(Math.atan2(dy,h)),-90.0F,90.0F);
   mc.playerController.attackEntity(mc.player,target);
   mc.player.swingArm(EnumHand.MAIN_HAND);
  }
 }
}