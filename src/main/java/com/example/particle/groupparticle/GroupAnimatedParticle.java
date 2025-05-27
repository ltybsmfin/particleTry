package com.example.particle.groupparticle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;

@Environment(EnvType.CLIENT)
public abstract class GroupAnimatedParticle extends GroupSpriteBillboardParticle {
    protected final SpriteProvider spriteProvider;
    private float targetRed;
    private float targetGreen;
    private float targetBlue;
    private boolean changesColor;



    protected GroupAnimatedParticle(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider, float upwardsAcceleration) {
        super(world, x, y, z);
        this.velocityMultiplier = 0.91F;
        this.gravityStrength = upwardsAcceleration;
        this.spriteProvider = spriteProvider;
        //this.maxAge = 320;

    }



    public void setColor(int rgbHex) {
        float f = ((rgbHex & 0xFF0000) >> 16) / 255.0F;
        float g = ((rgbHex & 0xFF00) >> 8) / 255.0F;
        float h = ((rgbHex & 0xFF) >> 0) / 255.0F;
        float i = 1.0F;
        this.setColor(f * 1.0F, g * 1.0F, h * 1.0F);
    }

    public void setTargetColor(int rgbHex) {
        this.targetRed = ((rgbHex & 0xFF0000) >> 16) / 255.0F;
        this.targetGreen = ((rgbHex & 0xFF00) >> 8) / 255.0F;
        this.targetBlue = ((rgbHex & 0xFF) >> 0) / 255.0F;
        this.changesColor = true;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    //act in WorldSpace
//    @Override
//    public void tick() {
//        super.tick();
//        this.setSpriteForAge(this.spriteProvider);
//        if (this.age > this.maxAge / 2) {
//            //this.setAlpha(1.0F - ((float)this.age - this.maxAge / 2) / this.maxAge);
//            this.setAlpha(1f);
//            if (this.changesColor) {
//                this.red = this.red + (this.targetRed - this.red) * 0.2F;
//                this.green = this.green + (this.targetGreen - this.green) * 0.2F;
//                this.blue = this.blue + (this.targetBlue - this.blue) * 0.2F;
//            }
//        }
//        //this.prevPosY += 1;
//    }

    @Override
    public int getBrightness(float tint) {
        return 15728880;
    }
}