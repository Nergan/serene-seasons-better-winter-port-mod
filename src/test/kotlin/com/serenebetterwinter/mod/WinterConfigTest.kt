package com.serenebetterwinter.mod

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class WinterConfigTest {
    @Test
    fun defaultSeasonsDropLeaves() {
        assertTrue(WinterConfig.dropsLeaves("LATE_AUTUMN"))
        assertTrue(WinterConfig.dropsLeaves("MID_WINTER"))
        assertFalse(WinterConfig.dropsLeaves("EARLY_SPRING"))
        assertFalse(WinterConfig.dropsLeaves(null))
    }
}
