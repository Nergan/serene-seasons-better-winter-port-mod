package com.serenebetterwinter.mod

import net.fabricmc.loader.api.FabricLoader
import net.minecraft.world.level.Level
import java.util.Locale

/** Reads Serene Seasons through its public helper, so this mod does not compile against that jar. */
object SeasonWatch {
    private var ready = false
    private var seasonState: java.lang.reflect.Method? = null
    private var subSeason: java.lang.reflect.Method? = null
    private var logged = false
    private var cachedGameTime = Long.MIN_VALUE
    private var cachedId: String? = null
    private var cachedLevel: Level? = null

    fun isLeafless(level: Level?): Boolean = WinterConfig.dropsLeaves(subSeasonId(level))

    fun subSeasonId(level: Level?): String? {
        if (level == null || !FabricLoader.getInstance().isModLoaded("sereneseasons")) return null
        if (cachedLevel === level && cachedGameTime == level.gameTime) return cachedId
        val id = read(level)?.toString()?.trim()?.uppercase(Locale.ROOT)
        cachedLevel = level
        cachedGameTime = level.gameTime
        cachedId = id
        return id
    }

    private fun read(level: Level): Any? {
        try {
            if (!ready) {
                val helper = Class.forName("sereneseasons.api.season.SeasonHelper")
                seasonState = helper.getMethod("getSeasonState", Level::class.java)
                ready = true
            }
            val state = seasonState?.invoke(null, level) ?: return null
            if (subSeason == null) {
                subSeason = state.javaClass.getMethod("getSubSeason")
            }
            return subSeason?.invoke(state)
        } catch (error: Throwable) {
            if (!logged) {
                logged = true
                WinterMod.LOGGER.warn("Could not read the Serene Seasons sub-season.", error)
            }
            return null
        }
    }
}
