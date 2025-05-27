package com.example;

import com.example.particle.*;
import com.example.renderer.MagicParticleRenderer;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.render.*;
import org.joml.Matrix4f;

public class particletryModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        //ParticleFactoryRegistry.getInstance().register(ModParticles.GREEN_FLAME, FlameParticle.Factory::new);
        ModParticleTypes.registerClient();


        System.out.println("客户端初始化");

        //magicRender = new MagicParticleRenderer(Mi)

        HudRenderCallback.EVENT.register((drawContext, tickDeltaManager) -> {
            // Get the transformation matrix from the matrix stack, alongside the tessellator instance and a new buffer builder.
            Matrix4f transformationMatrix = drawContext.getMatrices().peek().getPositionMatrix();
            Tessellator tessellator = Tessellator.getInstance();

            // Begin a triangle strip buffer using the POSITION_COLOR vertex format.
            BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);

            // Write our vertices, Z doesn't really matter since it's on the HUD.
            buffer.vertex(transformationMatrix, 20, 20, 5).color(0xFF414141);
            buffer.vertex(transformationMatrix, 5, 40, 5).color(0xFF000000);
            buffer.vertex(transformationMatrix, 35, 40, 5).color(0xFF000000);
            buffer.vertex(transformationMatrix, 20, 60, 5).color(0xFF414141);

            // We'll get to this bit in the next section.
            RenderSystem.setShader(GameRenderer::getParticleProgram);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

            // Draw the buffer onto the screen.
            BufferRenderer.drawWithGlobalProgram(buffer.end());
        });

//        HudRenderCallback.EVENT.register((drawContext, tickDeltaManager) -> {
//            // 获取变换矩阵和Tessellator实例
//            Matrix4f transformationMatrix = drawContext.getMatrices().peek().getPositionMatrix();
//            Tessellator tessellator = Tessellator.getInstance();
//
//            // 改为POINTS绘制模式
//            BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
//
//            // 定义四个点的位置和颜色
//            buffer.vertex(transformationMatrix, 20, 20, 5).color(0xFF414141);
//            buffer.vertex(transformationMatrix, 5, 40, 5).color(0xFF000000);
//            buffer.vertex(transformationMatrix, 35, 40, 5).color(0xFF000000);
//            buffer.vertex(transformationMatrix, 20, 60, 5).color(0xFF414141);
//
//            // 设置粒子着色器
//            RenderSystem.setShader(GameRenderer::getParticleProgram);
//            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//
//            // 绘制点
//            BufferRenderer.drawWithGlobalProgram(buffer.end());
//        });
    }




}
