package me.devnatan.inventoryframework.core;

import me.devnatan.inventoryframework.api.Ref;
import me.devnatan.inventoryframework.api.context.IFContext;
import me.devnatan.inventoryframework.api.exception.UnassignedReferenceException;
import org.jetbrains.annotations.NotNull;

public final class RefImpl<E> implements Ref<E> {

	private static final Object UNASSIGNED_VALUE = new Object();

	private Object assignment = UNASSIGNED_VALUE;

	@SuppressWarnings("unchecked")
	@Override
	public @NotNull E value(@NotNull IFContext context) {
		if (assignment == UNASSIGNED_VALUE) throw new UnassignedReferenceException();

		return (E) assignment;
	}

	@Override
	public void assign(Object value) {
		if (assignment != UNASSIGNED_VALUE)
			throw new IllegalStateException("Reference cannot be reassigned");

		this.assignment = value;
	}
}
