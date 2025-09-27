package me.devnatan.inventoryframework.bukkit.internal;

import me.devnatan.inventoryframework.api.ViewType;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import static java.util.Objects.requireNonNull;
import static me.devnatan.inventoryframework.bukkit.runtime.util.InventoryUtils.toInventoryType;

public class BukkitInventoryFactory extends InventoryFactory {

	public BukkitInventoryFactory() {
	}

	@Override
	public Inventory createInventory(InventoryHolder holder, ViewType type, int size, Object title) {
		final Inventory inventory;
		final String titleAsText = title == null || ((String) title).isEmpty() ? null : (String) title;

		if (titleAsText == null) {
			inventory = !type.isExtendable() || size == 0
				? Bukkit.createInventory(holder, requireNonNull(toInventoryType(type)))
				: Bukkit.createInventory(holder, size);
		} else if (!type.isExtendable()) {
			inventory = Bukkit.createInventory(holder, requireNonNull(toInventoryType(type)), titleAsText);
		} else {
			inventory = size == 0
				? Bukkit.createInventory(holder, requireNonNull(toInventoryType(type)), titleAsText)
				: Bukkit.createInventory(holder, size, titleAsText);
		}
		return inventory;
	}
}
