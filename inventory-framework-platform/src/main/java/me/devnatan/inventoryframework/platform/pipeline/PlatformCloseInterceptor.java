package me.devnatan.inventoryframework.platform.pipeline;

import me.devnatan.inventoryframework.api.VirtualView;
import me.devnatan.inventoryframework.api.context.IFCloseContext;
import me.devnatan.inventoryframework.api.pipeline.PipelineContext;
import me.devnatan.inventoryframework.api.pipeline.PipelineInterceptor;
import me.devnatan.inventoryframework.platform.PlatformView;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unchecked")
public final class PlatformCloseInterceptor implements PipelineInterceptor<VirtualView> {

	@SuppressWarnings("rawtypes")
	@Override
	public void intercept(@NotNull PipelineContext<VirtualView> pipeline, VirtualView subject) {
		if (!(subject instanceof IFCloseContext)) return;

		final IFCloseContext context = (IFCloseContext) subject;
		final PlatformView root = (PlatformView) context.getRoot();
		root.onClose(context);
	}
}
