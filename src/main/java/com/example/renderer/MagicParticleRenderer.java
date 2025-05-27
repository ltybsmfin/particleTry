package com.example.renderer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.ShaderStage;
import net.minecraft.client.render.*;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.resource.ResourceManager;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class MagicParticleRenderer {

    final MinecraftClient client;
    private final ResourceManager resourceManager;

    private final BufferBuilderStorage buffers;

    //private final LightmapTextureManager lightmapTextureManager;

//    @Nullable
//    PostEffectProcessor postProcessor;
//    @Nullable
//    private PostEffectProcessor blurPostProcessor;

    @Nullable
    public ShaderProgram blitScreenProgram;
    private final Map<String, ShaderProgram> programs = Maps.<String, ShaderProgram>newHashMap();
    @Nullable
    private static ShaderProgram positionColorProgram;


    public MagicParticleRenderer(MinecraftClient client, ResourceManager resourceManager, BufferBuilderStorage buffers) {
        this.client = client;
        this.resourceManager = resourceManager;
        this.buffers = buffers;
        //this.postProcessor = null;
    }

    public void close()
    {
        //this.lightmapTextureManager.close();

        //this.disablePostProcessor();
        this.clearPrograms();

        if (this.blitScreenProgram != null) {
            this.blitScreenProgram.close();
        }
    }

    public void preloadPrograms(ResourceFactory factory) {
        try {
            this.blitScreenProgram = new ShaderProgram(factory, "blit_screen", VertexFormats.BLIT_SCREEN);
            positionColorProgram = this.preloadProgram(factory, "position_color", VertexFormats.POSITION_COLOR);
        } catch (IOException var3) {
            throw new RuntimeException("Shader加载失败", var3); // 需要更详细的错误日志
        }
    }


    private ShaderProgram preloadProgram(ResourceFactory factory, String name, VertexFormat format) {
        try {
            ShaderProgram shaderProgram = new ShaderProgram(factory, name, format);
            this.programs.put(name, shaderProgram);
            return shaderProgram;
        } catch (Exception var5) {
            throw new IllegalStateException("could not preload shader " + name, var5);
        }
    }

//    public void disablePostProcessor() {
//        if (this.postProcessor != null) {
//            this.postProcessor.close();
//        }
//
//        this.postProcessor = null;
//    }

    void loadPrograms(ResourceFactory factory) {
        RenderSystem.assertOnRenderThread();
        List<ShaderStage> list = Lists.<ShaderStage>newArrayList();
        list.addAll(ShaderStage.Type.FRAGMENT.getLoadedShaders().values());
        list.addAll(ShaderStage.Type.VERTEX.getLoadedShaders().values());
        list.forEach(ShaderStage::release);
        List<Pair<ShaderProgram, Consumer<ShaderProgram>>> list2 = Lists.<Pair<ShaderProgram, Consumer<ShaderProgram>>>newArrayListWithCapacity(this.programs.size());

        try {
            list2.add(Pair.of(new ShaderProgram(factory, "position_color", VertexFormats.POSITION_COLOR), program -> positionColorProgram = program));

            //this.loadBlurPostProcessor(factory);
        } catch (IOException var5) {
            list2.forEach(pair -> ((ShaderProgram)pair.getFirst()).close());
            throw new RuntimeException("could not reload shaders", var5);
        }

        this.clearPrograms();
        list2.forEach(pair -> {
            ShaderProgram shaderProgram = (ShaderProgram)pair.getFirst();
            this.programs.put(shaderProgram.getName(), shaderProgram);
            ((Consumer)pair.getSecond()).accept(shaderProgram);
        });
    }

    private void clearPrograms() {
        RenderSystem.assertOnRenderThread();
        this.programs.values().forEach(ShaderProgram::close);
        this.programs.clear();
    }
    // 修改方法为静态访问
    @Nullable
    public ShaderProgram getPositionColorProgram() {
        return positionColorProgram;
    }
}