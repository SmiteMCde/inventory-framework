package me.devnatan.inventoryframework.bukkit.internal;

import com.tcoded.folialib.impl.PlatformScheduler;
import com.tcoded.folialib.wrapper.task.WrappedTask;
import me.devnatan.inventoryframework.api.internal.Job;

class BukkitTaskJobImpl implements Job {

	private final PlatformScheduler scheduler;
	private final long intervalInTicks;
	private final Runnable execution;
	private WrappedTask task;

	public BukkitTaskJobImpl(PlatformScheduler scheduler, long intervalInTicks, Runnable execution) {
		this.scheduler = scheduler;
		this.intervalInTicks = intervalInTicks;
		this.execution = execution;
	}

	@Override
	public boolean isStarted() {
		return task != null && !task.isCancelled();
	}

	@Override
	public void start() {
		if (isStarted()) return;

		// TODO Attach context to this Job and use entity scheduler instead of global one
		task = scheduler.runTimer(this::loop, intervalInTicks, intervalInTicks);
	}

	@Override
	public void cancel() {
		if (!isStarted()) return;
		task.cancel();
		task = null;
	}

	@Override
	public void loop() {
		execution.run();
	}
}
