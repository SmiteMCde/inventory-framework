package me.devnatan.inventoryframework.platform.logging;

import me.devnatan.inventoryframework.api.logging.Logger;
import org.jetbrains.annotations.Nullable;

public final class NoopLogger implements Logger {

	@Override
	public @Nullable String getPrefix() {
		return null;
	}

	@Override
	public void debug(String message) {
	}

	@Override
	public void warn(String message) {
	}

	@Override
	public void error(String message) {
	}
}
