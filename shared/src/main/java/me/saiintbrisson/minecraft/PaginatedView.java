package me.saiintbrisson.minecraft;

import org.jetbrains.annotations.NotNull;

/** Bukkit platform PaginatedView backward compatible implementation. */
public abstract class PaginatedView<T> extends AbstractPaginatedView<T> {

    /**
     * @deprecated Use {@link #onInit()} instead.
     */
    @Deprecated
    public PaginatedView() {
        this(0);
    }

    /**
     * @deprecated Use {@link #onInit()} instead.
     */
    @Deprecated
    public PaginatedView(int size) {
        this(size, null);
    }

    /**
     * @deprecated Use {@link #onInit()} instead.
     */
    @Deprecated
    public PaginatedView(String title) {
        this(0, title);
    }

    /**
     * @deprecated Use {@link #onInit()} instead.
     */
    @Deprecated
    public PaginatedView(String title, @NotNull ViewType type) {
        this(0, title, type);
    }

    /**
     * @deprecated Use {@link #onInit()} instead.
     */
    @Deprecated
    public PaginatedView(@NotNull ViewType type) {
        this(0, null, type);
    }

    /**
     * @deprecated Use {@link #onInit()} instead.
     */
    @Deprecated
    public PaginatedView(int size, String title) {
        this(size, title, ViewType.CHEST);
    }

    /**
     * @deprecated Use {@link #onInit()} instead.
     */
    @Deprecated
    public PaginatedView(int size, String title, @NotNull ViewType type) {
        super(size, title, type);
    }

    @Override
    public final String toString() {
        return super.toString();
    }
}
