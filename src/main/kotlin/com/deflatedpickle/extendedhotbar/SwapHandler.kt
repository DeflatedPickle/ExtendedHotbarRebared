/* Copyright (c) 2021 DemonWav, DeflatedPickle under the LGPL3 license */

@file:Suppress("MemberVisibilityCanBePrivate", "SpellCheckingInspection")

package com.deflatedpickle.extendedhotbar

import com.deflatedpickle.extendedhotbar.mixin.MixinCreativeInventoryScreen
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen
import net.minecraft.client.gui.screen.ingame.InventoryScreen
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.client.network.ClientPlayerInteractionManager
import net.minecraft.item.ItemGroup
import net.minecraft.screen.slot.SlotActionType

object SwapHandler {
    const val LEFT_BOTTOM_ROW_SLOT_INDEX = 27

    const val LEFT_HOTBAR_SLOT_INDEX = 36
    const val BOTTOM_RIGHT_CRAFTING_SLOT_INDEX = 4

    val INVENTORY_TAB_INDEX = ItemGroup.INVENTORY.index

    fun performSwap(client: MinecraftClient, fullRow: Boolean) {
        val player: ClientPlayerEntity = client.player ?: return
        val inventory = InventoryScreen(player)
        client.setScreen(inventory)
        val currentScreen = client.currentScreen ?: return

        // For the switcheroo to work, we need to be in the inventory window
        val index: Int
        if (currentScreen is CreativeInventoryScreen) {
            index = currentScreen.selectedTab
            if (index != INVENTORY_TAB_INDEX) {
                (currentScreen as MixinCreativeInventoryScreen).callSetSelectedTab(ItemGroup.INVENTORY)
            }
        } else {
            index = -1
        }
        val syncId = inventory.screenHandler.syncId
        if (fullRow) {
            swapRows(client, syncId)
        } else {
            client.interactionManager?.let { interactionManager ->
                val currentItem = player.inventory.selectedSlot
                swapItem(interactionManager, player, syncId, currentItem)
            }
        }

        // If index == -1 then it's not a creative inventory, if it's INVENTORY_TAB_INDEX then there's no need to change it back to itself
        if (index != -1 && index != INVENTORY_TAB_INDEX) {
            (currentScreen as MixinCreativeInventoryScreen).callSetSelectedTab(ItemGroup.GROUPS[index])
        }
        client.setScreen(null)
    }

    private fun swapRows(client: MinecraftClient, syncId: Int) {
        val interactionManager = client.interactionManager
        val player = client.player
        if (interactionManager == null || player == null) {
            return
        }
        for (i in 0..8) {
            swapItem(interactionManager, player, syncId, i)
        }
    }

    private fun swapItem(
        interactionManager: ClientPlayerInteractionManager,
        player: ClientPlayerEntity,
        syncId: Int,
        slotId: Int
    ) {
        /*
         * Implementation note:
         * There are fancy click mechanisms to swap item stacks without using a temporary slot, but when swapping between two identical item
         * stacks, things can get messed up. Using a temporary slot that we know is guaranteed to be empty is the safest option.
         */

        // Move hotbar item to crafting slot
        interactionManager.clickSlot(syncId, slotId + LEFT_HOTBAR_SLOT_INDEX, 0, SlotActionType.PICKUP, player)
        interactionManager.clickSlot(syncId, BOTTOM_RIGHT_CRAFTING_SLOT_INDEX, 0, SlotActionType.PICKUP, player)
        // Move bottom row item to hotbar
        interactionManager.clickSlot(syncId, slotId + LEFT_BOTTOM_ROW_SLOT_INDEX, 0, SlotActionType.PICKUP, player)
        interactionManager.clickSlot(syncId, slotId + LEFT_HOTBAR_SLOT_INDEX, 0, SlotActionType.PICKUP, player)
        // Move crafting slot item to bottom row
        interactionManager.clickSlot(syncId, BOTTOM_RIGHT_CRAFTING_SLOT_INDEX, 0, SlotActionType.PICKUP, player)
        interactionManager.clickSlot(syncId, slotId + LEFT_BOTTOM_ROW_SLOT_INDEX, 0, SlotActionType.PICKUP, player)
    }
}
