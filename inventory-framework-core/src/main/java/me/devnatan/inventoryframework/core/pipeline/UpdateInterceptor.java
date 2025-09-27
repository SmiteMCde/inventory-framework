package me.devnatan.inventoryframework.core.pipeline;

import me.devnatan.inventoryframework.api.VirtualView;
import me.devnatan.inventoryframework.api.component.Component;
import me.devnatan.inventoryframework.api.context.IFRenderContext;
import me.devnatan.inventoryframework.api.pipeline.PipelineContext;
import me.devnatan.inventoryframework.api.pipeline.PipelineInterceptor;

import java.util.List;

/**
 * Intercepts the update phase of a context.
 */
public final class UpdateInterceptor implements PipelineInterceptor<VirtualView> {

	@Override
	public void intercept(PipelineContext<VirtualView> pipeline, VirtualView subject) {
		if (!(subject instanceof IFRenderContext)) return;

		final IFRenderContext context = (IFRenderContext) subject;
		if (!context.isRendered()) return;

		final List<Component> componentList = context.getComponents();
		for (final Component component : componentList) context.updateComponent(component, false);
	}
}
