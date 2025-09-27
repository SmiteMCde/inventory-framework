package me.devnatan.inventoryframework;

import me.devnatan.inventoryframework.api.state.State;
import me.devnatan.inventoryframework.core.state.MutableValue;

class AnvilInputStateValue extends MutableValue {

	private final AnvilInputConfig config;

	public AnvilInputStateValue(State<?> state, AnvilInputConfig config) {
		super(state, config.initialInput);
		this.config = config;
	}

	@Override
	public void set(Object value) {
		final Object newValue;
		if (config.inputChangeHandler == null) newValue = value;
		else newValue = config.inputChangeHandler.apply((String) value);

		super.set(newValue);
	}
}
