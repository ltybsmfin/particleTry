package com.example.particle;


import com.example.ParticleTry;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticleTypes {
    public static final SimpleParticleType TEST_PARTICLE = FabricParticleTypes.simple();
    public static final SimpleParticleType TEST_GROUP_PARTICLE = FabricParticleTypes.simple();

    public static void register() {
        // 服务端注册到粒子类型注册表
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(ParticleTry.MOD_ID, "test_particle"), TEST_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(ParticleTry.MOD_ID, "testgroup_particle"), TEST_GROUP_PARTICLE);
    }

    public static void registerClient()
    {
        // 在初始化代码中注册
        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.TEST_PARTICLE, TestParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.TEST_GROUP_PARTICLE, testGroupParticle.Factory::new);

        // 注册工厂实例
        ParticleFactoryRegistry.getInstance().register(
                ModParticleTypes.TEST_PARTICLE,
                TestParticle.Factory::new
        );
        ParticleFactoryRegistry.getInstance().register(
                ModParticleTypes.TEST_GROUP_PARTICLE,
                testGroupParticle.Factory::new
        );
    }
}