package me.devnatan.inventoryframework.core.pipeline;

import java.util.AbstractMap;
import java.util.Map;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import me.devnatan.inventoryframework.api.exception.InventoryFrameworkException;
import me.devnatan.inventoryframework.api.VirtualView;
import me.devnatan.inventoryframework.api.component.Component;
import me.devnatan.inventoryframework.api.component.ComponentFactory;
import me.devnatan.inventoryframework.api.component.ItemComponentBuilder;
import me.devnatan.inventoryframework.api.component.Pagination;
import me.devnatan.inventoryframework.api.context.IFRenderContext;
import me.devnatan.inventoryframework.api.internal.LayoutSlot;
import me.devnatan.inventoryframework.api.pipeline.PipelineContext;
import me.devnatan.inventoryframework.api.pipeline.PipelineInterceptor;

public final class LayoutRenderInterceptor implements PipelineInterceptor<VirtualView> {

    @Override
    public void intercept(PipelineContext<VirtualView> pipeline, VirtualView subject) {
        if (!(subject instanceof IFRenderContext)) return;

        final IFRenderContext renderContext = (IFRenderContext) subject;
        final Map<Character, Component> paginationComponents = renderContext.getComponents().stream()
                .filter(component -> component instanceof Pagination)
                .map(component -> {
                    final Pagination pagination = (Pagination) component;
                    return new AbstractMap.SimpleImmutableEntry<>(pagination.getLayoutTarget(), pagination);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        for (final LayoutSlot layoutSlot : renderContext.getLayoutSlots()) {
            final IntFunction<ComponentFactory> factory = layoutSlot.getFactory();
            if (factory == null) {
                if (layoutSlot.isDefinedByTheUser())
                    throw new InventoryFrameworkException(
                            "#layoutSlot(...) factory cannot be null when defined by the user");
                continue;
            }

            // Pagination component are rendered AFTER `availableSlot`/`layoutSlot`.
            // Once a layout slot that uses the same layout target as the pagination component one
            // is detected, pagination component takes care of them and uses them as fallback.
            if (paginationComponents.containsKey(layoutSlot.getCharacter())) {
                continue;
            }

            int iterationIndex = 0;
            for (final int slot : layoutSlot.getPositions()) {
                final ComponentFactory componentFactory = factory.apply(iterationIndex++);
                if (componentFactory instanceof ItemComponentBuilder)
                    ((ItemComponentBuilder<?, ?>) componentFactory).withSlot(slot);

                final Component component = componentFactory.create();
                renderContext.addComponent(component);
            }
        }
    }
}
