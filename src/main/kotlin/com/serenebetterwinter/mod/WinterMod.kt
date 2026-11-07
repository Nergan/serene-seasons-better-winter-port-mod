package com.serenebetterwinter.mod

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.Minecraft
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class WinterMod : ModInitializer {
    override fun onInitialize() {
        WinterConfig.load(FabricLoader.getInstance().configDir)
    }

    companion object {
        const val MOD_ID: String = "serenebetterwinter"
        val LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)
    }
}

class WinterClient : ClientModInitializer {
    override fun onInitializeClient() {
        WinterConfig.load(FabricLoader.getInstance().configDir)
        ClientTickEvents.END_CLIENT_TICK.register {
            val level = Minecraft.getInstance().level
            leafless = level != null && SeasonWatch.isLeafless(level)
        }
    }

    companion object {
        @JvmField
        var leafless: Boolean = false
    }
}
