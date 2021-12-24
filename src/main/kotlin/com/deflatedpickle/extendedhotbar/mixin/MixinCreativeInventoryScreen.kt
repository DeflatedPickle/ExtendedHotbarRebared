/* Copyright (c) 2021 DemonWav, DeflatedPickle under the LGPL3 license */

package com.deflatedpickle.extendedhotbar.mixin

import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen
import net.minecraft.item.ItemGroup
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.gen.Invoker

@Mixin(CreativeInventoryScreen::class)
interface MixinCreativeInventoryScreen {
    @Invoker
    fun callSetSelectedTab(group: ItemGroup)
}
