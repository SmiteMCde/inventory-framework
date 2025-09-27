package me.devnatan.inventoryframework.api.pipeline;

@FunctionalInterface
public interface PipelineInterceptor<S> {

	void intercept(PipelineContext<S> pipeline, S subject);
}
