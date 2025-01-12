/* Copyright (c) 2021-2022 DemonWav,DeflatedPickle under the MIT license */

package com.deflatedpickle.extendedhotbar

import net.minecraft.client.gui.hud.InGameHud
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerEntity
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

object Util {
    fun drawTopHotbarBackground(inGameHud: InGameHud, matrixStack: MatrixStack) {
        if (ExtendedHotbarRebared.enabled) {
            inGameHud.drawTexture(
                matrixStack,
                inGameHud.scaledWidth / 2 - 91,
                inGameHud.scaledHeight - 22 + ExtendedHotbarRebared.DISTANCE,
                0, 0,
                182, 22,
            )
        }
    }

    fun drawTopHotbarItems(inGameHud: InGameHud, x: Int, y: Int, partialTicks: Float, playerEntity: PlayerEntity, index: Int, seed: Int) {
        if (ExtendedHotbarRebared.enabled) {
            inGameHud.renderHotbarItem(
                x, y + ExtendedHotbarRebared.DISTANCE,
                partialTicks,
                playerEntity,
                playerEntity.inventory.main[index + 27],
                seed,
            )
        }
    }

    fun moveHud(matrixStack: MatrixStack, info: CallbackInfo) {
        if (ExtendedHotbarRebared.enabled) {
            if (info.id == "move:head") {
                matrixStack.push()
                matrixStack.translate(0.0, ExtendedHotbarRebared.DISTANCE.toDouble(), 0.0)
            } else matrixStack.pop()
        }
    }

    fun moveActionBarText(y: Float): Float = if (ExtendedHotbarRebared.enabled) y + ExtendedHotbarRebared.DISTANCE else y
}
