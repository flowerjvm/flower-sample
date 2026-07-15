package io.github.flowerjvm.flower.sample.logistics.workflow.worker;

import io.github.flowerjvm.flower.core.engine.Engine;
import io.github.flowerjvm.flower.core.worker.DuplicatePolicy;
import io.github.flowerjvm.flower.sample.logistics.domain.WarehouseZone;
import io.github.flowerjvm.flower.sample.logistics.workflow.factory.WarehouseZoneFlowFactory;
import org.springframework.stereotype.Component;

/**
 * Application-owned handle to the Flower workers that run warehouse Zone
 * Flows.
 *
 * <p>Each Zone has a long-running Flow submitted once at startup. New orders
 * do not create Flows; they create boxes that these Zone Flows pick up.
 */
@Component
public final class WarehouseZoneWorker {

    private final Engine engine;
    private final WarehouseZoneFlowFactory flowFactory;

    public WarehouseZoneWorker(Engine engine, WarehouseZoneFlowFactory flowFactory) {
        this.engine = engine;
        this.flowFactory = flowFactory;
    }

    public void submitZoneFlows() {
        for (WarehouseZone zone : WarehouseZone.values()) {
            engine.worker(zone.workerName()).submit(flowFactory.create(zone), DuplicatePolicy.IGNORE);
        }
    }
}
