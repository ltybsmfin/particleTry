package com.example.particle;

import com.example.particle.geometryhelp.Circle;
import com.example.particle.groupparticle.GroupAnimatedParticle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import org.joml.Math;
import org.joml.Vector3f;

public class testGroupParticle extends GroupAnimatedParticle {
    private final int animationType;


    testGroupParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z, spriteProvider, 0.0125F);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
        this.scale *= 3F;
        this.maxAge = 120 + this.random.nextInt(12);

        this.animationType = ParticleEvents.getTriggerCount() % 3;
        //this.setTargetColor(15916745);
        this.setSpriteForAge(spriteProvider);
        //super.ObjectPositions =

        //System.out.println("你使用了木棍”, animationType ,“次");
        System.out.println(animationType);

    }

    @Override
    public void move(double dx, double dy, double dz) {
        this.setBoundingBox(this.getBoundingBox().offset(dx, dy, dz));
        //this.repositionFromBoundingBox();
    }

    public void BuildObject()
    {
        int resolution = 100;
        this.ObjectPositions = new Vector3f[resolution];
        this.ParticleNum = resolution;
        Circle circle = new Circle(resolution, 10f);

        for(int i = 0; i < resolution; i++)
        {
            //Vector3f position = getRotator()
            this.ObjectPositions[i] = circle.GetObjectPoint(i);
        }

    }

    @Override
    public void tick() {
        super.tick();
        System.out.println("调用了tick运动方式为" + animationType);

        this.setSpriteForAge(this.spriteProvider);

        switch (animationType) {
            case 0:
                applyAnimationType0();
                break;
            case 1:
                applyAnimationType1();
                break;
            case 2:
                applyAnimationType2();
                break;
        }
        //act();

    }

    Vector3f velocity = new Vector3f(5, 5, 5);
    public void act()
    {
        this.x += velocity.x * 0.04;
        this.y += velocity.y * 0.04;
        this.z += velocity.z * 0.04;

    }

    private void applyAnimationType0() {
        this.x += velocity.x * 0.04;

    }

    private void applyAnimationType1() {
        this.y += velocity.y * 0.04;
    }

    private void applyAnimationType2() {
        this.z += velocity.z * 0.04;
    }

    public Vector3f GetPoint(int index, int resolution, float radius)
    {
        System.out.println("成功构建一个粒子中");
        float angle =  (float)index / resolution * (float) org.joml.Math.PI * 2;
        float x = radius * org.joml.Math.sin(angle);
        float y = 0f;
        float z = radius * org.joml.Math.cos(angle);

        return new Vector3f(x, y, z);
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
            return new testGroupParticle
                    (
                            clientWorld,
                            d, e, f,  // 位置坐标
                            g, h, i,                // 速度参数
                            this.spriteProvider      // 纹理提供器
                    );
        }
    }
}
