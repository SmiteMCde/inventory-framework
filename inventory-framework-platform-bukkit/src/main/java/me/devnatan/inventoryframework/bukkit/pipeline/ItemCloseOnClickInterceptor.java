package me.devnatan.inventoryframework.bukkit.pipeline;

import me.devnatan.inventoryframework.api.VirtualView;
import me.devnatan.inventoryframework.api.component.Component;
import me.devnatan.inventoryframework.api.component.ItemComponent;
import me.devnatan.inventoryframework.api.pipeline.PipelineContext;
import me.devnatan.inventoryframework.api.pipeline.PipelineInterceptor;
import me.devnatan.inventoryframework.bukkit.context.SlotClickContext;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

/**
 * Intercepted when a player clicks on an item the view container. Checks if the container should be
 * closed when the item is clicked.
 */
public final class ItemCloseOnClickInterceptor implements PipelineInterceptor<VirtualView> {

	@Override
	public void intercept(@NotNull PipelineContext<VirtualView> pipeline, @NotNull VirtualView subject) {
		if (!(subject instanceof SlotClickContext)) return;

		final SlotClickContext context = (SlotClickContext) subject;
		final InventoryClickEvent event = context.getClickOrigin();
		if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) return;

		final Component component = context.getComponent();
		if (!(component instanceof ItemComponent) || !component.isVisible()) return;

		final ItemComponent item = (ItemComponent) component;
		if (item.isCloseOnClick()) {
			context.closeForPlayer();
			pipeline.finish();
		}
	}
}
