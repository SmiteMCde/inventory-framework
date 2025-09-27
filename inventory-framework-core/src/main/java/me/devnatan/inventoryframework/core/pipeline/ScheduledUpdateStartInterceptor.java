package me.devnatan.inventoryframework.core.pipeline;

import me.devnatan.inventoryframework.api.RootView;
import me.devnatan.inventoryframework.api.VirtualView;
import me.devnatan.inventoryframework.api.context.IFContext;
import me.devnatan.inventoryframework.api.internal.Job;
import me.devnatan.inventoryframework.api.pipeline.PipelineContext;
import me.devnatan.inventoryframework.api.pipeline.PipelineInterceptor;
import me.devnatan.inventoryframework.api.pipeline.StandardPipelinePhases;

import java.util.ArrayList;
import java.util.List;

public final class ScheduledUpdateStartInterceptor implements PipelineInterceptor<VirtualView> {

    @Override
    public void intercept(PipelineContext<VirtualView> pipeline, VirtualView subject) {
        if (pipeline.getPhase() != StandardPipelinePhases.VIEWER_ADDED) return;

        final IFContext context = (IFContext) subject;
        final RootView root = context.getRoot();
        final long updateIntervalInTicks = context.getConfig().getUpdateIntervalInTicks();

        if (updateIntervalInTicks == 0) {
            return;
        }

        if (root.getScheduledUpdateJob() != null && root.getScheduledUpdateJob().isStarted()) {
            return;
        }

        final Job updateJob = root.getElementFactory().scheduleJobInterval(root, updateIntervalInTicks, () -> {
            final List<IFContext> contextList = new ArrayList<>(root.getInternalContexts());
            contextList.stream().filter(IFContext::isActive).forEach(IFContext::update);
        });
        root.setScheduledUpdateJob(updateJob);
        updateJob.start();
    }
}
