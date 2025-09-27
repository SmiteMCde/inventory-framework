package me.devnatan.inventoryframework.core.pipeline;

import me.devnatan.inventoryframework.api.RootView;
import me.devnatan.inventoryframework.api.VirtualView;
import me.devnatan.inventoryframework.api.context.IFCloseContext;
import me.devnatan.inventoryframework.api.context.IFContext;
import me.devnatan.inventoryframework.api.pipeline.PipelineContext;
import me.devnatan.inventoryframework.api.pipeline.PipelineInterceptor;
import me.devnatan.inventoryframework.api.pipeline.StandardPipelinePhases;

public final class ScheduledUpdateStopInterceptor implements PipelineInterceptor<VirtualView> {

	@Override
	public void intercept(PipelineContext<VirtualView> pipeline, VirtualView subject) {
		if (pipeline.getPhase() != StandardPipelinePhases.VIEWER_REMOVED) return;

		final IFCloseContext context = (IFCloseContext) subject;
		final long updateIntervalInTicks = context.getConfig().getUpdateIntervalInTicks();
		if (updateIntervalInTicks == 0) {
			return;
		}

		final RootView root = context.getRoot();
		if (root.getScheduledUpdateJob() == null
			|| !root.getScheduledUpdateJob().isStarted()) {
			return;
		}

		if (context.isCancelled() || !context.isActive()) {
			return;
		}

		if (root.getInternalContexts().stream().filter(IFContext::isActive).count() > 1) {
			return;
		}

		root.getScheduledUpdateJob().cancel();
	}
}
