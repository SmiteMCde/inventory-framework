package me.devnatan.inventoryframework.api.component;

@FunctionalInterface
public interface PaginationElementFactory<V> {

    ComponentFactory create(Pagination root, int index, int slot, V value);
}
