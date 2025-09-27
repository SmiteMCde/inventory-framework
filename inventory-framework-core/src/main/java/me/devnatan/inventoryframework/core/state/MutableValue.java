package me.devnatan.inventoryframework.core.state;

import java.util.Objects;

import me.devnatan.inventoryframework.api.state.AbstractStateValue;
import me.devnatan.inventoryframework.api.state.State;
import org.jetbrains.annotations.ApiStatus;

/**
 * <b><i> This is an internal inventory-framework API that should not be used from outside of
 * this library. No compatibility guarantees are provided. </i></b>
 */
@ApiStatus.Internal
public class MutableValue extends AbstractStateValue {

    private Object currValue;

    public MutableValue(State<?> state, Object currValue) {
        super(state);
        this.currValue = currValue;
    }

    @Override
    public Object get() {
        return currValue;
    }

    @Override
    public void set(Object value) {
        this.currValue = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MutableValue that = (MutableValue) o;
        return Objects.equals(currValue, that.currValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), currValue);
    }

    @Override
    public String toString() {
        return "MutableValue{" + "currValue=" + currValue + "} " + super.toString();
    }
}
