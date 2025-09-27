package me.devnatan.inventoryframework.runtime.view

import me.devnatan.inventoryframework.api.ViewConfigBuilder
import me.devnatan.inventoryframework.api.component.Pagination
import me.devnatan.inventoryframework.api.state.State
import me.devnatan.inventoryframework.minestom.View
import me.devnatan.inventoryframework.minestom.component.MinestomItemComponentBuilder
import me.devnatan.inventoryframework.minestom.context.Context
import me.devnatan.inventoryframework.minestom.context.RenderContext
import me.devnatan.inventoryframework.minestom.context.SlotClickContext
import me.devnatan.inventoryframework.runtime.ExampleUtil.displayItem
import me.devnatan.inventoryframework.runtime.ExampleUtil.getRandomItems
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material

class SimplePagination : View() {
    private val state: State<Pagination> =
        lazyPaginationState(
            { _ -> getRandomItems(123).toMutableList() },
            { _: Context, builder: MinestomItemComponentBuilder, index: Int, value: ItemStack ->
                builder.withItem(value)
                builder.onClick { ctx: SlotClickContext ->
                    ctx.player.sendMessage("You clicked on item $index")
                }
            },
        )

    override fun onInit(config: ViewConfigBuilder): Unit =
        with(config) {
            cancelOnClick()
            size(3)
            title("Simple Pagination")
            layout("OOOOOOOOO", "OOOOOOOOO", "  P   N  ")
        }

    override fun onFirstRender(render: RenderContext) {
        val previousItem = displayItem(Material.ARROW, "Previous")
        val nextItem = displayItem(Material.ARROW, "Next")
        render
            .layoutSlot('P', previousItem)
            .displayIf({ ctx -> state[ctx].canBack() })
            .updateOnStateChange(state)
            .onClick { ctx: SlotClickContext -> state[ctx].back() }
        render
            .layoutSlot('N', nextItem)
            .displayIf({ ctx -> state[ctx].canAdvance() })
            .updateOnStateChange(state)
            .onClick { ctx: SlotClickContext -> state[ctx].advance() }
    }
}
