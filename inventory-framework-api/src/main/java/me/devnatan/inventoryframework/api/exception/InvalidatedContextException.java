package me.devnatan.inventoryframework.api.exception;

public class InvalidatedContextException extends InventoryFrameworkException {
    InvalidatedContextException(String message, Throwable cause) {
        super(message, cause);
    }
}
