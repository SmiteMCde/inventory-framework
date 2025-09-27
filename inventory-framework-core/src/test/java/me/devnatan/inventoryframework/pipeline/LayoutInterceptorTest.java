package me.devnatan.inventoryframework.pipeline;

import me.devnatan.inventoryframework.api.ViewConfig;
import me.devnatan.inventoryframework.api.ViewContainer;
import me.devnatan.inventoryframework.api.VirtualView;
import me.devnatan.inventoryframework.api.context.IFRenderContext;
import me.devnatan.inventoryframework.api.exception.InvalidLayoutException;
import me.devnatan.inventoryframework.api.pipeline.Pipeline;
import me.devnatan.inventoryframework.api.pipeline.StandardPipelinePhases;
import me.devnatan.inventoryframework.core.pipeline.LayoutResolutionInterceptor;
import org.junit.jupiter.api.Test;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LayoutInterceptorTest {

	@Test
	void invalidLayoutLengthForContainer() {
		Pipeline<VirtualView> pipeline = new Pipeline<>(StandardPipelinePhases.LAYOUT_RESOLUTION);
		pipeline.intercept(StandardPipelinePhases.LAYOUT_RESOLUTION, new LayoutResolutionInterceptor());

		ViewConfig config = mock(ViewConfig.class);
		String[] layout = new String[]{"XXXXXXX" /* rows count = 1 */};
		when(config.getLayout()).thenReturn(layout);

		IFRenderContext context = mock(IFRenderContext.class);
		when(context.getConfig()).thenReturn(config);

		ViewContainer container = mock(ViewContainer.class);
		when(container.getRowsCount()).thenReturn(layout.length + 1 /* rows count = 2 */);
		when(context.getContainer()).thenReturn(container);

		Throwable throwable = assertThrows(
			InvalidLayoutException.class,
			() -> pipeline.execute(StandardPipelinePhases.LAYOUT_RESOLUTION, context));

		assertEquals(
			format(
				"Layout length (%d) must respect the rows count of the container (%d).",
				layout.length, container.getRowsCount()),
			throwable.getMessage());
	}

	@Test
	void invalidLayoutLengthForLayer() {
		Pipeline<VirtualView> pipeline = new Pipeline<>(StandardPipelinePhases.LAYOUT_RESOLUTION);
		pipeline.intercept(StandardPipelinePhases.LAYOUT_RESOLUTION, new LayoutResolutionInterceptor());

		ViewConfig config = mock(ViewConfig.class);

		String[] layout = new String[]{"XXX" /* columns count = 3 */};
		when(config.getLayout()).thenReturn(layout);

		IFRenderContext context = mock(IFRenderContext.class);
		when(context.getConfig()).thenReturn(config);

		ViewContainer container = mock(ViewContainer.class);
		when(container.getRowsCount()).thenReturn(layout.length);
		when(container.getColumnsCount()).thenReturn(9 /* columns count = 9 */);
		when(context.getContainer()).thenReturn(container);

		Throwable throwable = assertThrows(
			InvalidLayoutException.class,
			() -> pipeline.execute(StandardPipelinePhases.LAYOUT_RESOLUTION, context));

		assertEquals(
			format(
				"Layout layer length located at %d must respect the columns count of the container (given: %d, expect: %d).",
				0, layout[0].length(), container.getColumnsCount()),
			throwable.getMessage());
	}
}
