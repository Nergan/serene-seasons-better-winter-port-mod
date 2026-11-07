package com.serenebetterwinter.mod

import net.minecraft.core.BlockPos
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.BlockTags
import net.minecraft.tags.TagKey
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState

object VisualRules {
    private val always = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(WinterMod.MOD_ID, "always_hide_when_attached_to_hidden_leaves"))
    private val never = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(WinterMod.MOD_ID, "never_hide_when_attached_to_hidden_leaves"))
    private val attachedHints = setOf("apple", "cocoon", "cotton", "silk", "fruit", "pod")
    private val woodyHints = setOf("branch", "trunk", "log", "stem", "wood", "bark", "root", "stump", "twig")

    fun shouldHide(level: BlockGetter, pos: BlockPos, state: BlockState): Boolean {
        if (!leafless(level)) return false
        if (LeafRules.shouldHide(state)) return true
        if (snowAboveLeaves(level, pos, state)) return true
        return attached(level, pos, state)
    }

    fun snowAboveLeaves(level: BlockGetter, pos: BlockPos, state: BlockState): Boolean {
        if (!WinterConfig.hideSnow || !state.`is`(Blocks.SNOW)) return false
        return LeafRules.shouldHide(level.getBlockState(pos.below()))
    }

    fun passThrough(level: Level, pos: BlockPos, state: BlockState): Boolean {
        if (!WinterConfig.passThrough || !SeasonWatch.isLeafless(level)) return false
        if (LeafRules.shouldHide(state)) return true
        return snowAboveLeaves(level, pos, state)
    }

    private fun leafless(level: BlockGetter): Boolean = when (level) {
        is Level -> SeasonWatch.isLeafless(level)
        else -> WinterClient.leafless
    }

    private fun attached(level: BlockGetter, pos: BlockPos, state: BlockState): Boolean {
        if (!WinterConfig.hideAttached || state.isAir || state.`is`(never)) return false
        if (LeafRules.shouldHide(state) || snowAboveLeaves(level, pos, state)) return false
        if (!LeafRules.shouldHide(level.getBlockState(pos.above()))) return false
        if (state.`is`(always)) return true
        if (state.`is`(BlockTags.LOGS) || state.`is`(BlockTags.LOGS_THAT_BURN)) return false
        val path = BuiltInRegistries.BLOCK.getKey(state.block).path
        if (woodyHints.any { path.contains(it) }) return false
        return attachedHints.any { path.contains(it) }
    }
}
