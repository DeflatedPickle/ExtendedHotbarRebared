/* Copyright (c) 2021 DemonWav, DeflatedPickle under the LGPL3 license */

package com.deflatedpickle.extendedhotbar

import com.deflatedpickle.extendedhotbar.ExtendedHotbarRebared.MOD_ID
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import org.lwjgl.glfw.GLFW.GLFW_KEY_EQUAL
import org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT
import org.lwjgl.glfw.GLFW.GLFW_KEY_R
import org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_SHIFT
import org.lwjgl.glfw.GLFW.GLFW_PRESS
import org.lwjgl.glfw.GLFW.glfwGetKey

object KeyboardHandler {
    private val swapKeyBinding: KeyBinding = KeyBinding(
        "key.$MOD_ID.switch",
        InputUtil.Type.KEYSYM,
        GLFW_KEY_R,
        "key.$MOD_ID"
    )

    private val toggleKeyBinding = KeyBinding(
        "key.$MOD_ID.toggle",
        InputUtil.Type.KEYSYM,
        GLFW_KEY_EQUAL,
        "key.$MOD_ID"
    )

    fun initialize() {
        KeyBindingHelper.registerKeyBinding(swapKeyBinding)
        KeyBindingHelper.registerKeyBinding(toggleKeyBinding)

        ClientTickEvents.END_CLIENT_TICK.register(::onTick)
    }

    private fun onTick(client: MinecraftClient) {
        if (toggleKeyBinding.wasPressed()) {
            ExtendedHotbarRebared.enabled = !ExtendedHotbarRebared.enabled
        }

        if (
            client.world == null || client.currentScreen != null ||
            !MinecraftClient.isHudEnabled() || !ExtendedHotbarRebared.enabled
        ) return

        val window = client.window.handle

        if (swapKeyBinding.wasPressed()) {
            val single = glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS ||
                glfwGetKey(window, GLFW_KEY_RIGHT_SHIFT) == GLFW_PRESS
            SwapHandler.performSwap(client, single)
        }
    }
}
