package net.shadowmage.ancientwarfare.automation.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.shadowmage.ancientwarfare.core.model.ModelBaseAW;
import net.shadowmage.ancientwarfare.core.model.ModelLoader;
import net.shadowmage.ancientwarfare.core.util.RenderTools;
import net.shadowmage.ancientwarfare.core.util.Trig;

import org.lwjgl.opengl.GL11;

public class RenderSterlingEngine extends TileEntitySpecialRenderer
{

ModelBaseAW model;

float rotation;
ResourceLocation texture;


public RenderSterlingEngine()
  {
  ModelLoader loader = new ModelLoader();
  model = loader.loadModel(getClass().getResourceAsStream("/assets/ancientwarfare/models/automation/sterling_engine.mf2"));
  texture = new ResourceLocation("ancientwarfare:textures/model/automation/sterling_engine.png");
  }

@Override
public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTick)
  {
  rotation+=0.4f;
  GL11.glPushMatrix();
  
  RenderTools.setFullColorLightmap();
  GL11.glTranslated(x+0.5d, y+1, z+0.5d);
  bindTexture(texture);  
  model.getPiece("flywheel2").setRotation(0, 0, rotation);
  calculateArmAngle1(rotation);
  calculateArmAngle2(rotation-90);
  
  model.getPiece("flywheel_arm").setRotation(0, 0, -rotation);
  
  model.getPiece("piston_crank").setRotation(0, 0, rotation);
  model.getPiece("piston_arm").setRotation(0, 0, -rotation+armAngle);
  
  model.getPiece("piston_arm2").setRotation(0, 0, -rotation+armAngle2);
  
  model.renderModel();

  GL11.glPopMatrix();
  }

float pistonPos, armAngle, pistonPos2, armAngle2;

private void calculateArmAngle1(float crankAngle)
  {
  float ra = crankAngle*Trig.TORADIANS;
  float crankDistance = 1.f;//side a
  float crankLength = 9.f;//side b
  calculatePistonPosition1(ra, crankDistance, crankLength);
  }

private void calculatePistonPosition1(float crankAngleRadians, float radius, float length)
  {
  float cA = MathHelper.cos(crankAngleRadians);
  float sA = MathHelper.sin(crankAngleRadians);
  pistonPos = radius*cA + MathHelper.sqrt_float( length*length-radius*radius*sA*sA );
  
  float bx = sA * radius;
  float by = cA * radius;
  float cx = 0;
  float cy = pistonPos;
  
  float rlrA = (float) Math.atan2(cx-bx, cy-by);
  armAngle = rlrA*Trig.TODEGREES;
  }

private void calculateArmAngle2(float crankAngle)
  {
  float ra = crankAngle*Trig.TORADIANS;
  float crankDistance = 1.f;//side a
  float crankLength = 7.f;//side b
  calculatePistonPosition2(ra, crankDistance, crankLength);
  }

private void calculatePistonPosition2(float crankAngleRadians, float radius, float length)
  {
  float cA = MathHelper.cos(crankAngleRadians);
  float sA = MathHelper.sin(crankAngleRadians);
  pistonPos2 = radius*cA + MathHelper.sqrt_float( length*length-radius*radius*sA*sA );
  
  float bx = sA * radius;
  float by = cA * radius;
  float cx = 0;
  float cy = pistonPos2;

  float rlrA = (float) Math.atan2(cx-bx, cy-by);
  armAngle2 = rlrA*Trig.TODEGREES;
  }

}