package com.serenebetterwinter.mod

import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.writeText

object WinterConfig {
    var passThrough: Boolean = true
        private set
    var hideSnow: Boolean = true
        private set
    var hideAttached: Boolean = true
        private set
    var subSeasons: Set<String> = setOf("LATE_AUTUMN", "EARLY_WINTER", "MID_WINTER", "LATE_WINTER")
        private set

    fun load(configDir: Path) {
        val file = configDir.resolve("serenebetterwinter-common.toml")
        if (!file.exists()) {
            file.parent?.createDirectories()
            file.writeText(
                """
                pass_through_hidden_blocks = true
                hide_snow_above_hidden_leaves = true
                hide_blocks_attached_to_hidden_leaves = true
                leaf_drop_subseasons = LATE_AUTUMN, EARLY_WINTER, MID_WINTER, LATE_WINTER
                """.trimIndent() + "\n",
            )
        }
        val text = file.readText()
        passThrough = flag(text, "pass_through_hidden_blocks", passThrough)
        hideSnow = flag(text, "hide_snow_above_hidden_leaves", hideSnow)
        hideAttached = flag(text, "hide_blocks_attached_to_hidden_leaves", hideAttached)
        val seasons = Regex("""(?m)^\s*leaf_drop_subseasons\s*=\s*(.+)$""").find(text)?.groupValues?.get(1)
        if (seasons != null) {
            subSeasons = seasons.split(',').map { it.trim().uppercase() }.filter { it.contains('_') }.toSet()
        }
    }

    fun dropsLeaves(subSeasonId: String?): Boolean = subSeasonId != null && subSeasonId.uppercase() in subSeasons

    private fun flag(text: String, key: String, fallback: Boolean): Boolean {
        val match = Regex("""(?m)^\s*$key\s*=\s*(true|false)\s*$""").find(text) ?: return fallback
        return match.groupValues[1] == "true"
    }
}
