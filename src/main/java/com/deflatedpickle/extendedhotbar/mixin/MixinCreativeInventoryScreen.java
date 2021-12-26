package com.deflatedpickle.extendedhotbar.mixin;

import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.item.ItemGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@SuppressWarnings("UnusedMixin")
@Mixin(CreativeInventoryScreen.class)
public interface MixinCreativeInventoryScreen {
    @Invoker
    void callSetSelectedTab(ItemGroup group);
}
