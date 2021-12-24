/* Copyright (c) 2021 DemonWav, DeflatedPickle under the LGPL3 license */

@file:Suppress("unused", "SpellCheckingInspection")

package com.deflatedpickle.extendedhotbar.mixin

import com.deflatedpickle.extendedhotbar.ExtendedHotbarRebared
import com.deflatedpickle.extendedhotbar.ExtendedHotbarRebared.DISTANCE
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.gui.hud.InGameHud
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.Arm
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.ModifyArg
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import org.spongepowered.asm.mixin.injection.callback.LocalCapture

@Mixin(InGameHud::class)
abstract class MixinInGameHud : DrawableHelper() {
    @Shadow val scaledWidth = 0
    @Shadow val scaledHeight = 0

    @Shadow
    abstract fun renderHotbarItem(
        x: Int,
        y: Int,
        tickDelta: Float,
        playerEntity: PlayerEntity,
        itemStack: ItemStack,
        seed: Int,
    )

    @Inject(
        method = ["renderHotbar"],
        at = [
            At(
                value = "INVOKE",
                shift = At.Shift.AFTER,
                ordinal = 0,
                target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V",
            )
        ],
    )
    fun drawTopHotbarBackground(tickDelta: Float, matrixStack: MatrixStack, info: CallbackInfo) {
        if (ExtendedHotbarRebared.enabled) {
            this.drawTexture(
                matrixStack,
                scaledWidth / 2 - 91,
                scaledHeight - 22 + DISTANCE,
                0, 0,
                182, 22,
            )
        }
    }

    @Inject(
        method = ["renderHotbar"],
        at = [
            At(
                value = "INVOKE",
                shift = At.Shift.BEFORE,
                ordinal = 0,
                target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHotbarItem(IIFLnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;I)V",
            )
        ],
        locals = LocalCapture.CAPTURE_FAILEXCEPTION,
    )
    fun drawTopHotbarItems(
        partialTicks: Float,
        matrixStack: MatrixStack,
        info: CallbackInfo,
        playerEntity: PlayerEntity,
        itemStack: ItemStack,
        arm: Arm,
        i: Int,
        j: Int,
        k: Int,
        l: Int,
        seed: Int,
        index: Int,
        x: Int,
        y: Int
    ) {
        if (ExtendedHotbarRebared.enabled) {
            this.renderHotbarItem(
                x, y + DISTANCE,
                partialTicks,
                playerEntity,
                playerEntity.inventory.main[index + 27],
                seed,
            )
        }
    }

    @Inject(
        id = "move",
        method = [
            "renderStatusBars",
            "renderHeldItemTooltip",
            "renderMountHealth",
        ],
        at = [
            At(value = "HEAD", id = "head"),
            At(value = "RETURN", id = "return"),
        ]
    )
    fun moveHud(matrixStack: MatrixStack, info: CallbackInfo) {
        if (ExtendedHotbarRebared.enabled) {
            if (info.id == "move:head") {
                matrixStack.push()
                matrixStack.translate(0.0, DISTANCE.toDouble(), 0.0)
            } else matrixStack.pop()
        }
    }

    @Inject(
        id = "move",
        method = [
            "renderExperienceBar",
            "renderMountJumpBar",
        ],
        at = [
            At(value = "HEAD", id = "head"),
            At(value = "RETURN", id = "return"),
        ]
    )
    fun moveExperienceBar(matrixStack: MatrixStack, x: Int, info: CallbackInfo) {
        if (ExtendedHotbarRebared.enabled) {
            if (info.id == "move:head") {
                matrixStack.push()
                matrixStack.translate(0.0, DISTANCE.toDouble(), 0.0)
            } else matrixStack.pop()
        }
    }

    @ModifyArg(
        method = ["render"],
        index = 3,
        at = At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/Text;FFI)I"
        )
    )
    fun moveActionBarText(y: Float): Float = if (ExtendedHotbarRebared.enabled) y + DISTANCE else y
}
