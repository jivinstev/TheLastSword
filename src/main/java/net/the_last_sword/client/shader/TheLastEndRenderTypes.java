package net.the_last_sword.client.shader;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.Identifier;
import net.the_last_sword.mv.LegacyRenderType;
import net.the_last_sword.mv.ShaderShard;

import java.util.HashMap;
import java.util.Map;

//The Last End 渲染类型工厂
//为不同的渲染场景（实体、物品、方块、粒子）创建对应的 RenderType
public class TheLastEndRenderTypes {

    private static final Map<String, RenderPipeline> PIPELINES = new HashMap<>();

    //按名称缓存原版着色器的管线，避免每次调用都新建
    private static RenderPipeline pipeline(String name, RenderPipeline.Snippet base, BlendFunction blend,
                                           boolean cull, boolean writeDepth) {
        return PIPELINES.computeIfAbsent(name, n -> RenderPipeline.builder(base)
            .withLocation("pipeline/" + n)
            .withBlend(blend)
            .withCull(cull)
            .withDepthWrite(writeDepth)
            .build());
    }

    //自定义着色器的渲染类型：着色器随 RenderType 状态绑定（1.21.4 的 ShaderStateShard 写法）
    private static RenderType custom(String name, VertexFormat format, Identifier texture,
                                     boolean lightmap, boolean overlay, boolean cull, boolean writeDepth) {
        LegacyRenderType.State.Builder sb = LegacyRenderType.state()
            .setShaderState(new ShaderShard(TheLastEndEffect::getShader))
            .setDepthTestState(LegacyRenderType.LEQUAL_DEPTH_TEST)
            .setTransparencyState(LegacyRenderType.TRANSLUCENT_TRANSPARENCY)
            .setTextureState(new LegacyRenderType.TextureStateShard(texture, false))
            .setCullState(cull ? LegacyRenderType.CULL : LegacyRenderType.NO_CULL);
        if (lightmap) {
            sb.setLightmapState(LegacyRenderType.LIGHTMAP);
        }
        if (overlay) {
            sb.setOverlayState(LegacyRenderType.OVERLAY);
        }
        if (!writeDepth) {
            sb.setWriteMaskState(LegacyRenderType.COLOR_WRITE);
        }
        return LegacyRenderType.create(name, format, VertexFormat.Mode.QUADS, 2097152, true, false,
            sb.createCompositeState(true));
    }

    private static RenderType make(String name, RenderPipeline pipeline, Identifier texture,
                                   boolean lightmap, boolean overlay, boolean outline) {
        RenderSetup.RenderSetupBuilder sb = RenderSetup.builder(pipeline)
            .withTexture("Sampler0", texture)
            .bufferSize(1536);
        if (lightmap) {
            sb.useLightmap();
        }
        if (overlay) {
            sb.useOverlay();
        }
        if (outline) {
            sb.affectsCrumbling();
            sb.setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE);
        }
        return RenderType.create(name, sb.createRenderSetup());
    }

    //创建实体表面的 The Last End 效果渲染类型
    //适用于: 实体外表渲染（如 TestEntity）
    public static RenderType createEntityEffect(Identifier texture) {
        return custom("the_last_end_entity_effect", DefaultVertexFormat.NEW_ENTITY, texture,
            true, true, false, true);
    }

    //创建物品的 The Last End 效果渲染类型（使用纹理图集）
    //适用于: 手持物品、掉落物、GUI 中的物品
    public static RenderType createItemEffectWithAtlas() {
        //只写入颜色，不写入深度（避免透明区域遮挡）
        return custom("the_last_end_item_effect_atlas", DefaultVertexFormat.NEW_ENTITY, TextureAtlas.LOCATION_BLOCKS,
            false, true, false, false);
    }

    //创建物品的 The Last End 效果渲染类型（使用指定纹理）
    //适用于: 特殊物品渲染
    public static RenderType createItemEffect(Identifier texture) {
        return custom("the_last_end_item_effect", DefaultVertexFormat.NEW_ENTITY, texture,
            false, false, false, true);
    }

    //创建方块/天空盒的 The Last End 效果渲染类型
    //适用于: 方块特效、天空盒渲染
    public static RenderType createBlockEffect(Identifier texture) {
        return custom("the_last_end_block_effect", DefaultVertexFormat.BLOCK, TextureAtlas.LOCATION_BLOCKS,
            true, false, true, true);
    }

    //创建粒子的 The Last End 效果渲染类型
    //适用于: 剑刃周围的星点粒子效果
    public static RenderType createParticleEffect() {
        //加法混合 + 禁用深度写入 + 禁用面剔除
        RenderPipeline pipeline = pipeline("the_last_end_particle_effect",
            RenderPipelines.PARTICLE_SNIPPET, BlendFunction.ADDITIVE, false, false);
        return make("the_last_end_particle_effect", pipeline, TextureAtlas.LOCATION_BLOCKS, false, false, false);
    }

    //创建发光层的 The Last End 效果渲染类型（不使用自定义着色器）
    //适用于: 发光边缘效果
    public static RenderType createGlowEffect(Identifier texture) {
        return make("the_last_end_glow_effect", RenderPipelines.ENTITY_TRANSLUCENT, texture, true, true, true);
    }

    private TheLastEndRenderTypes() {}
}
