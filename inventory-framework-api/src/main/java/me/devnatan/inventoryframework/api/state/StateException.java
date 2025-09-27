package me.devnatan.inventoryframework.api.state;

import me.devnatan.inventoryframework.api.exception.InventoryFrameworkException;

public class StateException extends InventoryFrameworkException {
	public StateException() {
		super();
	}

	public StateException(String message) {
		super(message);
	}

	public StateException(String message, Throwable cause) {
		super(message, cause);
	}
}
