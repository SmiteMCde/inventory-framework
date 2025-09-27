package me.devnatan.inventoryframework.api.state;

public class IllegalStateModificationException extends StateException {
    public IllegalStateModificationException(String message) {
        super(message);
    }
}
