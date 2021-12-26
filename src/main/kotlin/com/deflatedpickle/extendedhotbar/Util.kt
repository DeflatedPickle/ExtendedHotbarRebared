/* Copyright (c) 2021 DemonWav, DeflatedPickle under the LGPL3 license */

package com.deflatedpickle.extendedhotbar

import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.gui.hud.InGameHud
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerEntity
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

object Util {
    fun drawTopHotbarBackground(drawableHelper: DrawableHelper, matrixStack: MatrixStack, scaledWidth: Int, scaledHeight: Int) {
        if (ExtendedHotbarRebared.enabled) {
            drawableHelper.drawTexture(
                matrixStack,
                scaledWidth / 2 - 91,
                scaledHeight - 22 + ExtendedHotbarRebared.DISTANCE,
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
