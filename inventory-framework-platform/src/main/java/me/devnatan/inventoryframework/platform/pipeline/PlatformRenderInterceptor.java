package me.devnatan.inventoryframework.platform.pipeline;

import me.devnatan.inventoryframework.api.VirtualView;
import me.devnatan.inventoryframework.api.context.IFRenderContext;
import me.devnatan.inventoryframework.api.pipeline.PipelineContext;
import me.devnatan.inventoryframework.api.pipeline.PipelineInterceptor;
import me.devnatan.inventoryframework.platform.PlatformView;
import org.jetbrains.annotations.NotNull;

public final class PlatformRenderInterceptor implements PipelineInterceptor<VirtualView> {

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void intercept(@NotNull PipelineContext<VirtualView> pipeline, VirtualView view) {
        if (!(view instanceof IFRenderContext)) return;

        final PlatformView root = (PlatformView) ((IFRenderContext) view).getRoot();
        final IFRenderContext context = (IFRenderContext) view;
        root.onFirstRender(context);
    }
}
