package me.devnatan.inventoryframework.platform.pipeline;

import me.devnatan.inventoryframework.api.IFDebug;
import me.devnatan.inventoryframework.api.Viewer;
import me.devnatan.inventoryframework.api.VirtualView;
import me.devnatan.inventoryframework.api.context.IFCloseContext;
import me.devnatan.inventoryframework.api.pipeline.PipelineContext;
import me.devnatan.inventoryframework.api.pipeline.PipelineInterceptor;
import me.devnatan.inventoryframework.platform.PlatformView;

public final class ContextInvalidationOnCloseInterceptor implements PipelineInterceptor<VirtualView> {

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void intercept(PipelineContext<VirtualView> pipeline, VirtualView subject) {
        if (!(subject instanceof IFCloseContext)) return;

        final IFCloseContext context = (IFCloseContext) subject;
        if (!context.isActive() || context.isCancelled()) {
            IFDebug.debug(
                    "Invalidation skipped (active = %b, cancelled = %b)", context.isActive(), context.isCancelled());
            return;
        }

        final PlatformView root = (PlatformView) context.getRoot();
        final Viewer viewer = context.getViewer();
        root.removeAndTryInvalidateContext(viewer, context);
    }
}
