package me.devnatan.inventoryframework.core.pipeline;

import me.devnatan.inventoryframework.api.IFDebug;
import me.devnatan.inventoryframework.api.Viewer;
import me.devnatan.inventoryframework.api.VirtualView;
import me.devnatan.inventoryframework.api.context.IFSlotClickContext;
import me.devnatan.inventoryframework.api.pipeline.PipelineContext;
import me.devnatan.inventoryframework.api.pipeline.PipelineInterceptor;
import org.jetbrains.annotations.NotNull;

public final class ViewerLastInteractionTrackerInterceptor implements PipelineInterceptor<VirtualView> {

    @Override
    public void intercept(@NotNull PipelineContext<VirtualView> pipeline, @NotNull VirtualView subject) {
        if (!(subject instanceof IFSlotClickContext)) return;

        final IFSlotClickContext click = (IFSlotClickContext) subject;

        // fast path -- skip checks and currentTimeMillis() calls if interaction delay is not enabled
        if (click.getConfig().getInteractionDelayInMillis() <= 0) return;

        final Viewer viewer = click.getViewer();
        if (!click.isCombined() && viewer.isBlockedByInteractionDelay()) {
            IFDebug.debug("ViewerLastInteractionTrackerInterceptor: pipeline finished due to interaction delay");
            click.setCancelled(true);
            pipeline.finish();
            return;
        }

        viewer.setLastInteractionInMillis(System.currentTimeMillis());
    }
}
