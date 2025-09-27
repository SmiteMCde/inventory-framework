package me.devnatan.inventoryframework.paper.internal;

import me.devnatan.inventoryframework.api.ViewType;
import me.devnatan.inventoryframework.bukkit.internal.BukkitInventoryFactory;
import me.devnatan.inventoryframework.bukkit.runtime.util.InventoryUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import static java.util.Objects.requireNonNull;

/**
 * InventoryFactory implementation for PaperSpigot software.
 * Supports {@link Component} as inventory title.
 */
@SuppressWarnings("unused")
final class PaperInventoryFactory extends BukkitInventoryFactory {

	PaperInventoryFactory() {
	}

	@Override
	public Inventory createInventory(InventoryHolder holder, ViewType type, int size, Object title) {
		if (!(title instanceof Component titleAsComponent))
			return super.createInventory(holder, type, size, title);
		final Inventory inventory;

		if (!type.isExtendable()) {
			inventory = Bukkit.createInventory(
				holder, requireNonNull(InventoryUtils.toInventoryType(type)), titleAsComponent);
		} else {
			inventory = size == 0
				? Bukkit.createInventory(
				holder, requireNonNull(InventoryUtils.toInventoryType(type)), titleAsComponent)
				: Bukkit.createInventory(holder, size, titleAsComponent);
		}
		return inventory;
	}
}
