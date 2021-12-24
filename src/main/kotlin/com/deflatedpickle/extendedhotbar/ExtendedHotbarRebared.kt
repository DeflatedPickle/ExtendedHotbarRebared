/* Copyright (c) 2021 DemonWav, DeflatedPickle under the LGPL3 license */

@file:Suppress("MemberVisibilityCanBePrivate", "SpellCheckingInspection")

package com.deflatedpickle.extendedhotbar

import net.fabricmc.api.ClientModInitializer
import net.minecraft.util.Identifier

@Suppress("UNUSED")
object ExtendedHotbarRebared : ClientModInitializer {
    const val MOD_ID = "$[id]"
    private const val NAME = "$[name]"
    private const val GROUP = "$[group]"
    private const val AUTHOR = "$[author]"
    private const val VERSION = "$[version]"

    val WIDGETS_TEXTURE = Identifier("textures/gui/widgets.png")
    const val DISTANCE = -22

    var enabled = true

    override fun onInitializeClient() {
        println(listOf(MOD_ID, NAME, GROUP, AUTHOR, VERSION))

        KeyboardHandler.initialize()
    }
}
