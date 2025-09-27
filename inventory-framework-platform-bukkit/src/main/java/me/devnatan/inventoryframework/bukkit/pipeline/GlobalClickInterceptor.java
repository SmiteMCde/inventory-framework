package me.devnatan.inventoryframework.bukkit.pipeline;

import me.devnatan.inventoryframework.api.VirtualView;
import me.devnatan.inventoryframework.api.pipeline.PipelineContext;
import me.devnatan.inventoryframework.api.pipeline.PipelineInterceptor;
import me.devnatan.inventoryframework.bukkit.context.SlotClickContext;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

import static me.devnatan.inventoryframework.api.ViewConfig.CANCEL_ON_CLICK;

/**
 * Intercepted when a player clicks on the view container.
 * If the click is canceled, this interceptor ends the pipeline immediately.
 */
public final class GlobalClickInterceptor implements PipelineInterceptor<VirtualView> {

	@Override
	public void intercept(@NotNull PipelineContext<VirtualView> pipeline, @NotNull VirtualView subject) {
		if (!(subject instanceof SlotClickContext)) return;

		final SlotClickContext context = (SlotClickContext) subject;
		final InventoryClickEvent event = context.getClickOrigin();

		// inherit cancellation so we can un-cancel it
		context.setCancelled(event.isCancelled() || context.getConfig().isOptionSet(CANCEL_ON_CLICK, true));
		context.getRoot().onClick(context);
	}
}
