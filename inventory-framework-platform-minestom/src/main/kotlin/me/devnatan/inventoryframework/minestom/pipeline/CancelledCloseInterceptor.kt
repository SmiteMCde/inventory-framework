package me.devnatan.inventoryframework.minestom.pipeline

import me.devnatan.inventoryframework.api.VirtualView
import me.devnatan.inventoryframework.api.pipeline.PipelineContext
import me.devnatan.inventoryframework.api.pipeline.PipelineInterceptor
import me.devnatan.inventoryframework.minestom.context.CloseContext

class CancelledCloseInterceptor : PipelineInterceptor<VirtualView> {
    override fun intercept(
        pipeline: PipelineContext<VirtualView>,
        subject: VirtualView,
    ) {
        if (subject !is CloseContext) return

        if (!subject.isCancelled) return

        subject.root.nextTick { subject.viewer.open(subject.container) }
    }
}
