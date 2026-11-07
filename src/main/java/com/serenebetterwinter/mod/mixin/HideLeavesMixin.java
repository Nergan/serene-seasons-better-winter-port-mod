package com.serenebetterwinter.mod.mixin;

import com.serenebetterwinter.mod.LeafRules;
import com.serenebetterwinter.mod.VisualRules;
import com.serenebetterwinter.mod.WinterClient;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class HideLeavesMixin {
    @Inject(method = "getRenderShape", at = @At("HEAD"), cancellable = true)
    private void serenebetterwinter$hideLeaves(CallbackInfoReturnable<RenderShape> callback) {
        if (!WinterClient.leafless) {
            return;
        }
        BlockState state = (BlockState) (Object) this;
        if (LeafRules.INSTANCE.shouldHide(state)) {
            callback.setReturnValue(RenderShape.INVISIBLE);
        }
    }

    @Inject(
        method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;",
        at = @At("HEAD"),
        cancellable = true
    )
    private void serenebetterwinter$passThrough(
        BlockGetter level,
        BlockPos pos,
        CollisionContext context,
        CallbackInfoReturnable<VoxelShape> callback
    ) {
        if (!(level instanceof Level world)) {
            return;
        }
        BlockState state = (BlockState) (Object) this;
        if (VisualRules.INSTANCE.passThrough(world, pos, state)) {
            callback.setReturnValue(Shapes.empty());
        }
    }

    @Inject(
        method = "getLightBlock(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)I",
        at = @At("HEAD"),
        cancellable = true
    )
    private void serenebetterwinter$light(BlockGetter level, BlockPos pos, CallbackInfoReturnable<Integer> callback) {
        if (open(level, pos)) {
            callback.setReturnValue(0);
        }
    }

    @Inject(
        method = "propagatesSkylightDown(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z",
        at = @At("HEAD"),
        cancellable = true
    )
    private void serenebetterwinter$sky(BlockGetter level, BlockPos pos, CallbackInfoReturnable<Boolean> callback) {
        if (open(level, pos)) {
            callback.setReturnValue(true);
        }
    }

    @Inject(
        method = "getShadeBrightness(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)F",
        at = @At("HEAD"),
        cancellable = true
    )
    private void serenebetterwinter$shade(BlockGetter level, BlockPos pos, CallbackInfoReturnable<Float> callback) {
        if (open(level, pos)) {
            callback.setReturnValue(1.0F);
        }
    }

    @Inject(
        method = "getOcclusionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/phys/shapes/VoxelShape;",
        at = @At("HEAD"),
        cancellable = true
    )
    private void serenebetterwinter$occlusion(BlockGetter level, BlockPos pos, CallbackInfoReturnable<VoxelShape> callback) {
        if (open(level, pos)) {
            callback.setReturnValue(Shapes.empty());
        }
    }

    @Inject(
        method = "isSolidRender(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z",
        at = @At("HEAD"),
        cancellable = true
    )
    private void serenebetterwinter$solid(BlockGetter level, BlockPos pos, CallbackInfoReturnable<Boolean> callback) {
        if (open(level, pos)) {
            callback.setReturnValue(false);
        }
    }

    private boolean open(BlockGetter level, BlockPos pos) {
        if (!(level instanceof Level world)) {
            return false;
        }
        BlockState state = (BlockState) (Object) this;
        return VisualRules.INSTANCE.passThrough(world, pos, state);
    }
}
