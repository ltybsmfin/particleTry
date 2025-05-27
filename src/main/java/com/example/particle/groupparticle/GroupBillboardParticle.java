package com.example.particle.groupparticle;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.BillboardParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.render.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.stream.IntStream;

/**
 * A {@link Particle} which renders a camera-facing sprite with a target texture scale.
 */
@Environment(EnvType.CLIENT)
public abstract class GroupBillboardParticle extends Particle {



    //一定要记得初始化
    protected int ParticleNum;
    protected Vector3f[] ObjectPositions;

    protected float scale = 0.1F * (this.random.nextFloat() * 0.5F + 0.5F) * 2.0F;

    protected GroupBillboardParticle(ClientWorld clientWorld, double d, double e, double f) {
        super(clientWorld, d, e, f);

        BuildObject();
    }

    protected GroupBillboardParticle(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        super(clientWorld, d, e, f, g, h, i);

        BuildObject();
    }

    public BillboardParticle.Rotator getRotator() {
        return BillboardParticle.Rotator.ALL_AXIS;
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        System.out.println(" buildGeometry " + prevPosY);
        Quaternionf quaternionf = new Quaternionf();
        this.getRotator().setRotation(quaternionf, camera, tickDelta);
        if (this.angle != 0.0F) {
            quaternionf.rotateZ(MathHelper.lerp(tickDelta, this.prevAngle, this.angle));
        }

        //BuildObject();

        int GroupNum = (int) Math.ceil(ParticleNum / 10.0);

        IntStream.range(0, 10).forEach(i -> { // 注意移除了 .parallel()
            //System.out.println("构建一组粒子中");
            for (int j = 0; j < GroupNum; j++) {

                int index = i * GroupNum + j;

                if(index >= ObjectPositions.length)
                {
                    return;
                }
                Vector3f ObjectPosition = ObjectPositions[index];
                this.method_60373(vertexConsumer, camera, quaternionf, tickDelta, ObjectPosition);
            }
        });

    }

    protected void method_60373(VertexConsumer vertexConsumer, Camera camera, Quaternionf quaternionf, float f, Vector3f ObjectPosition) {
            Vec3d vec3d = camera.getPos();
//            float g = (float)(MathHelper.lerp((double)f, this.prevPosX, this.x) - vec3d.getX()) + ObjectPosition.x;
//            float h = (float)(MathHelper.lerp((double)f, this.prevPosY, this.y) - vec3d.getY()) + ObjectPosition.y;
//            float i = (float)(MathHelper.lerp((double)f, this.prevPosZ, this.z) - vec3d.getZ()) + ObjectPosition.z;

            float g = (float)(this.x - vec3d.getX()) + ObjectPosition.x;
            float h = (float)(this.y - vec3d.getY()) + ObjectPosition.y;
            float i = (float)(this.z - vec3d.getZ()) + ObjectPosition.z;

            this.method_60374(vertexConsumer, quaternionf, g, h, i, f);
        }

        protected void method_60374(VertexConsumer vertexConsumer, Quaternionf quaternionf, float f, float g, float h, float i) {
            float j = this.getSize(i);
            float k = this.getMinU();
            float l = this.getMaxU();
            float m = this.getMinV();
            float n = this.getMaxV();
            int o = this.getBrightness(i);
            this.method_60375(vertexConsumer, quaternionf, f, g, h, 1.0F, -1.0F, j, l, n, o);
            this.method_60375(vertexConsumer, quaternionf, f, g, h, 1.0F, 1.0F, j, l, m, o);
            this.method_60375(vertexConsumer, quaternionf, f, g, h, -1.0F, 1.0F, j, k, m, o);
            this.method_60375(vertexConsumer, quaternionf, f, g, h, -1.0F, -1.0F, j, k, n, o);
        }

        private void method_60375(
                VertexConsumer vertexConsumer,
                Quaternionf quaternionf,
        float f, float g, float h,
        float i, float j, float k,
        float l, float m, int n
    ) {
            Vector3f vector3f =
                    new Vector3f(i, j, 0.0F)
                            .rotate(quaternionf)
                        .mul(k).add(f, g, h);

        vertexConsumer.
                vertex(vector3f.x(), vector3f.y(), vector3f.z())
                .texture(l, m)
                .color(this.red, this.green, this.blue, this.alpha)
                .light(n);
    }

    /**
     * {@return the draw scale of this particle, which is used while rendering in {@link #buildGeometry}}
     */
    public float getSize(float tickDelta) {
        return this.scale;
    }

    @Override
    public Particle scale(float scale) {
        this.scale *= scale;
        return super.scale(scale);
    }

    /**
     * {@return the lower U coordinate of the UV coordinates used to draw this particle}
     */
    protected abstract float getMinU();

    /**
     * {@return the upper U coordinate of the UV coordinates used to draw this particle}
     */
    protected abstract float getMaxU();

    /**
     * {@return the lower V coordinate of the UV coordinates used to draw this particle}
     */
    protected abstract float getMinV();

    /**
     * {@return the upper V coordinate of the UV coordinates used to draw this particle}
     */
    protected abstract float getMaxV();

    protected abstract void BuildObject();


//    public void BuildObject()
//    {
//        int resolution = 100;
//        this.ObjectPositions = new Vector3f[100];
//        this.ParticleNum = 100;
//
//        for(int i = 0; i < resolution; i++)
//        {
//            this.ObjectPositions[i] = GetPoint(i, resolution, 10f);
//        }
////        for(int i = 0, x = 0; x < 10; x++)
////        {
////            for(int y = 0; y < 10; y++)
////            {
////                for(int z = 0; z < 10; z++, i++)
////                {
////                    this.ObjectPositions[i] = new Vector3f(x, y, z);
////                }
////            }
////        }
//    }
//
//    public Vector3f GetPoint(int index, int resolution, float radius)
//    {
//        System.out.println("成功构建一个粒子中");
//        float angle =  (float)index / resolution * (float) org.joml.Math.PI * 2;
//        float x = radius * org.joml.Math.sin(angle);
//        float y = 0f;
//        float z = radius * org.joml.Math.cos(angle);
//
//        return new Vector3f(x, y, z);
//    }



    @Environment(EnvType.CLIENT)
    public interface Rotator {
        BillboardParticle.Rotator ALL_AXIS = (quaternion, camera, tickDelta) -> quaternion.set(camera.getRotation());
        BillboardParticle.Rotator Y_AND_W_ONLY = (quaternion, camera, tickDelta) -> quaternion.set(0.0F, camera.getRotation().y, 0.0F, camera.getRotation().w);

        void setRotation(Quaternionf quaternion, Camera camera, float tickDelta);
    }
}
