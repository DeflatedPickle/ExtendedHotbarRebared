/* Copyright (c) 2021-2022 DemonWav,DeflatedPickle under the MIT license */

@file:Suppress("MemberVisibilityCanBePrivate", "SpellCheckingInspection")

package com.deflatedpickle.extendedhotbar

import net.minecraft.util.Identifier
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer

@Suppress("UNUSED")
object ExtendedHotbarRebared : ModInitializer {
    const val MOD_ID = "$[id]"
    private const val NAME = "$[name]"
    private const val GROUP = "$[group]"
    private const val AUTHOR = "$[author]"
    private const val VERSION = "$[version]"

    val WIDGETS_TEXTURE = Identifier("textures/gui/widgets.png")
    const val DISTANCE = -22

    var enabled = true

    override fun onInitialize(mod: ModContainer) {
        println(listOf(MOD_ID, NAME, GROUP, AUTHOR, VERSION))

        KeyboardHandler.initialize()
    }
}
