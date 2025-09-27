package me.devnatan.inventoryframework.platform.pipeline;

import me.devnatan.inventoryframework.api.RootView;
import me.devnatan.inventoryframework.api.ViewConfigBuilder;
import me.devnatan.inventoryframework.api.VirtualView;
import me.devnatan.inventoryframework.api.pipeline.PipelineContext;
import me.devnatan.inventoryframework.api.pipeline.PipelineInterceptor;
import me.devnatan.inventoryframework.platform.PlatformView;
import org.jetbrains.annotations.NotNull;

public final class PlatformInitInterceptor implements PipelineInterceptor<VirtualView> {

	public void intercept(@NotNull PipelineContext<VirtualView> pipeline, VirtualView view) {
		if (!(view instanceof RootView)) return;

		@SuppressWarnings("rawtypes") final PlatformView root = (PlatformView) view;
		ViewConfigBuilder configBuilder = root.createConfig();
		root.onInit(configBuilder);
		root.setConfig(configBuilder.build());
	}
}
