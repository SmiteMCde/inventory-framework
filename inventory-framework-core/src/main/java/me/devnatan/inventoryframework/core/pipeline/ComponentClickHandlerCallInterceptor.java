package me.devnatan.inventoryframework.core.pipeline;

import java.util.logging.Level;
import java.util.logging.Logger;
import me.devnatan.inventoryframework.api.IFDebug;
import me.devnatan.inventoryframework.api.VirtualView;
import me.devnatan.inventoryframework.api.component.Component;
import me.devnatan.inventoryframework.api.context.IFSlotClickContext;
import me.devnatan.inventoryframework.api.pipeline.PipelineContext;
import me.devnatan.inventoryframework.api.pipeline.PipelineInterceptor;
import org.jetbrains.annotations.NotNull;

public final class ComponentClickHandlerCallInterceptor implements PipelineInterceptor<VirtualView> {

    Logger logger = Logger.getLogger("IF");

    @Override
    public void intercept(@NotNull PipelineContext<VirtualView> pipeline, @NotNull VirtualView subject) {
        if (!(subject instanceof IFSlotClickContext)) return;

        final IFSlotClickContext click = (IFSlotClickContext) subject;
        final Component component = click.getComponent();

        if (component == null) {
            IFDebug.debug("ComponentClickHandlerCallInterceptor: null component");
            return;
        }
        if (component.getInteractionHandler() == null) {
            IFDebug.debug("ComponentClickHandlerCallInterceptor: null component.getInteractionHandler()");
            return;
        }

        IFDebug.debug("ComponentClickHandlerCallInterceptor: %s", component);
        try {
            component.getInteractionHandler().clicked(component, click);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred while handling a click event", e);
        }
    }
}
