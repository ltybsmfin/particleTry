package com.example.particle;

import com.example.particle.singleparticle.MagicAnimatedParticle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;

@Environment(EnvType.CLIENT)
public class TestParticle extends MagicAnimatedParticle {
    TestParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z, spriteProvider, 0.0125F);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
        this.scale *= 3F;
        this.maxAge = 60 + this.random.nextInt(12);
        //this.setTargetColor(15916745);
        this.setSpriteForAge(spriteProvider);
    }

    @Override
    public void move(double dx, double dy, double dz) {
        this.setBoundingBox(this.getBoundingBox().offset(dx, dy, dz));
        this.repositionFromBoundingBox();
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle
                (
                        SimpleParticleType parameters,
                        ClientWorld clientWorld,
                        double d, double e, double f,
                        double g, double h, double i
                )
        {
            // 修改为创建MagicParticle实例，并传递完整的8个位置参数
            return new TestParticle
                    (
                    clientWorld,
                    d, e, f,  // 位置坐标
                    g, h, i,                // 速度参数
                    this.spriteProvider      // 纹理提供器
            );
        }
    }
}