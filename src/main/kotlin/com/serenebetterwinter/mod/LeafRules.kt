package com.serenebetterwinter.mod

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.BlockTags
import net.minecraft.tags.TagKey
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.LeavesBlock
import net.minecraft.world.level.block.state.BlockState

object LeafRules {
    private val deciduous = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(WinterMod.MOD_ID, "deciduous_leaves"))
    private val conifer = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(WinterMod.MOD_ID, "conifer_leaves"))
    private val coniferHints = setOf("spruce", "pine", "fir", "cypress", "juniper", "cedar", "sequoia", "redwood", "hemlock", "conifer")
    private val hotHints = setOf("jungle")
    private val cache = HashMap<Block, Boolean>()

    fun shouldHide(state: BlockState): Boolean {
        val block = state.block
        val key = BuiltInRegistries.BLOCK.getKey(block)
        val leaves = state.`is`(BlockTags.LEAVES) || block is LeavesBlock || key.path.contains("leaves")
        if (!leaves) return false
        if (hotHints.any { key.path.contains(it) }) return false
        if (state.`is`(conifer)) return false
        if (state.`is`(deciduous)) return true
        cache[block]?.let { return it }
        val hide = when {
            coniferHints.any { key.path.contains(it) } -> false
            key.namespace == "dynamic_trees" || key.namespace.startsWith("dynamictrees") || key.namespace.startsWith("dt") -> true
            else -> true
        }
        cache[block] = hide
        return hide
    }
}
