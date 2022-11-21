/* Copyright (c) 2022 DemonWav,DeflatedPickle under the MIT license */

package com.deflatedpickle.extendedhotbar.mixin;

import com.deflatedpickle.extendedhotbar.Util;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@SuppressWarnings("UnusedMixin")
@Mixin(InGameHud.class)
abstract class MixinInGameHud extends DrawableHelper {
  @Inject(
      method = "renderHotbar",
      at =
          @At(
              value = "INVOKE",
              shift = At.Shift.AFTER,
              ordinal = 0,
              target =
                  "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V"))
  public void drawTopHotbarBackground(float delta, MatrixStack matrix, CallbackInfo info) {
    Util.INSTANCE.drawTopHotbarBackground((InGameHud) (Object) this, matrix);
  }

  @Inject(
      method = "renderHotbar",
      at =
          @At(
              value = "INVOKE",
              shift = At.Shift.AFTER,
              ordinal = 0,
              target =
                  "Lnet/minecraft/client/gui/hud/InGameHud;renderHotbarItem(IIFLnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;I)V"),
      locals = LocalCapture.CAPTURE_FAILEXCEPTION)
  public void drawTopHotbarItems(
      float partialTicks,
      MatrixStack matrixStack,
      CallbackInfo info,
      PlayerEntity playerEntity,
      ItemStack itemStack,
      Arm arm,
      int i,
      int j,
      int k,
      int l,
      int seed,
      int index,
      int x,
      int y) {
    Util.INSTANCE.drawTopHotbarItems(
        (InGameHud) (Object) this, x, y, partialTicks, playerEntity, index, seed);
  }

  @Inject(
      id = "move",
      method = {"renderStatusBars", "renderHeldItemTooltip", "renderMountHealth"},
      at = {@At(value = "HEAD", id = "head"), @At(value = "RETURN", id = "return")})
  public void moveHud(MatrixStack matrixStack, CallbackInfo info) {
    Util.INSTANCE.moveHud(matrixStack, info);
  }

  @Inject(
      id = "move",
      method = {"renderExperienceBar", "renderMountJumpBar"},
      at = {@At(value = "HEAD", id = "head"), @At(value = "RETURN", id = "return")})
  public void moveExpBar(MatrixStack matrixStack, int x, CallbackInfo info) {
    Util.INSTANCE.moveHud(matrixStack, info);
  }

  @ModifyArg(
      method = "render",
      index = 3,
      at =
          @At(
              value = "INVOKE",
              target =
                  "Lnet/minecraft/client/font/TextRenderer;drawWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/Text;FFI)I"))
  public float moveActionBarText(float y) {
    return Util.INSTANCE.moveActionBarText(y);
  }
}
