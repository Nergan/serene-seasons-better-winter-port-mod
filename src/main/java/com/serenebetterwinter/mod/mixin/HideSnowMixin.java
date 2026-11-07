package com.serenebetterwinter.mod.mixin;

import com.serenebetterwinter.mod.VisualRules;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelBlockRenderer.class)
public abstract class HideSnowMixin {
    @Inject(method = {"tesselateBlock", "tesselateWithAO", "tesselateWithoutAO"}, at = @At("HEAD"), cancellable = true)
    private void serenebetterwinter$hide(
        BlockAndTintGetter level,
        BakedModel model,
        BlockState state,
        BlockPos pos,
        PoseStack poseStack,
        VertexConsumer consumer,
        boolean checkSides,
        RandomSource random,
        long seed,
        int packedOverlay,
        CallbackInfo callback
    ) {
        if (VisualRules.INSTANCE.shouldHide(level, pos, state)) {
            callback.cancel();
        }
    }
}
