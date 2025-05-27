package com.example;

import com.example.item.ModItemGroups;
import com.example.item.ModItems;
import com.example.particle.ModParticleTypes;
import com.example.particle.ModParticles;
import com.example.particle.ParticleEvents;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.ModInitializer;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public class ParticleTry implements ModInitializer {
	public static final String MOD_ID = "particletry";


	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	@Override
	public void onInitialize() {

		ModItems.registerModItems();
		LOGGER.info("Hello Fabric world!");
		ModItemGroups.registerModItemGroups();
		//ModParticles.registerModParticles();

		ModParticleTypes.register();
		ParticleEvents.register();
	}


}