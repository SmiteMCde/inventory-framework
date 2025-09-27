package me.devnatan.inventoryframework.platform.pipeline;

import me.devnatan.inventoryframework.api.VirtualView;
import me.devnatan.inventoryframework.api.context.IFContext;
import me.devnatan.inventoryframework.api.pipeline.PipelineContext;
import me.devnatan.inventoryframework.api.pipeline.PipelineInterceptor;
import me.devnatan.inventoryframework.api.pipeline.StandardPipelinePhases;
import me.devnatan.inventoryframework.platform.PlatformView;

public final class PlatformUpdateHandlerInterceptor implements PipelineInterceptor<VirtualView> {

    @SuppressWarnings("unchecked")
    @Override
    public void intercept(PipelineContext<VirtualView> pipeline, VirtualView subject) {
        if (!(subject instanceof IFContext) || pipeline.getPhase() != StandardPipelinePhases.UPDATE) return;

        @SuppressWarnings("rawtypes")
        final PlatformView root = (PlatformView) ((IFContext) subject).getRoot();
        root.onUpdate((IFContext) subject);
    }
}
