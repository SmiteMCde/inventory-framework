package me.devnatan.inventoryframework;

import me.devnatan.inventoryframework.api.RootView;
import me.devnatan.inventoryframework.api.context.IFContext;
import me.devnatan.inventoryframework.internal.MockElementFactory;

import java.util.HashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestUtils {

	public static RootView createRootMock() {
		RootView root = mock(RootView.class);
		when(root.getElementFactory()).thenReturn(new MockElementFactory());
		return root;
	}

	public static <T extends IFContext> T createContextMock(RootView root, Class<T> type) {
		T context = mock(type);
		when(context.getIndexedViewers()).thenReturn(new HashMap<>());
		when(context.getRoot()).thenReturn(root);
		return context;
	}
}
