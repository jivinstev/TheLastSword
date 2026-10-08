package net.the_last_sword.init;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.the_last_sword.TheLastSwordMod;
import net.the_last_sword.client.shader.TheLastEndEffect;
import net.the_last_sword.client.shader.TheLastEndShaderInstance;

import java.io.IOException;

//着色器注册
@EventBusSubscriber(modid = TheLastSwordMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModShaders {

    //注册着色器
    @SubscribeEvent
    public static void onRegisterShaders(RegisterShadersEvent event) throws IOException {
        //注册 The Last End 着色器
        ShaderInstance shader = TheLastEndShaderInstance.create(
            event.getResourceProvider(),
            ResourceLocation.fromNamespaceAndPath(TheLastSwordMod.MOD_ID, "the_last_end"),
            DefaultVertexFormat.BLOCK
        );
        event.registerShader(shader, TheLastEndEffect::setShader);
    }
}
