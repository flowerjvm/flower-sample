package io.github.flowerjvm.flower.sample.basic.support;

import io.github.flowerjvm.flower.core.engine.Engine;
import io.github.flowerjvm.flower.core.event.EventBus;
import io.github.flowerjvm.flower.core.event.InMemoryEventBus;
import io.github.flowerjvm.flower.core.flow.Flow;
import io.github.flowerjvm.flower.core.worker.Worker;

public final class RuntimeTestSupport {

    private RuntimeTestSupport() {
    }

    public static RecordingFlowerListener runFlow(Flow flow) throws Exception {
        return runFlow(flow, InMemoryEventBus.create(), null);
    }

    public static RecordingFlowerListener runFlow(
            Flow flow,
            EventBus eventBus,
            AfterSubmit afterSubmit) throws Exception {
        RecordingFlowerListener listener = new RecordingFlowerListener();
        Worker worker = Worker.builder("basic-worker")
                .intervalMillis(25L)
                .build();
        Engine engine = Engine.builder()
                .eventBus(eventBus)
                .listener(listener)
                .worker(worker)
                .build();

        engine.start();
        try {
            worker.submit(flow);
            if (afterSubmit != null) {
                afterSubmit.run();
            }
            listener.awaitTerminal();
            return listener;
        } finally {
            engine.stop();
        }
    }

    public static RecordingFlowerListener runFlowAndPublish(
            Flow flow,
            InMemoryEventBus eventBus,
            Object event,
            long delayMillis) throws Exception {
        return runFlow(flow, eventBus, new DelayedPublish(eventBus, event, delayMillis));
    }

    public interface AfterSubmit {
        void run() throws Exception;
    }

    private static final class DelayedPublish implements AfterSubmit {

        private final InMemoryEventBus eventBus;
        private final Object event;
        private final long delayMillis;

        private DelayedPublish(InMemoryEventBus eventBus, Object event, long delayMillis) {
            this.eventBus = eventBus;
            this.event = event;
            this.delayMillis = delayMillis;
        }

        @Override
        public void run() throws Exception {
            Thread.sleep(delayMillis);
            eventBus.publish(event);
        }
    }
}
