package io.github.flowerjvm.flower.sample.logistics.workflow.step;

import io.github.flowerjvm.flower.sample.logistics.domain.WarehouseZone;

public final class SortBoxStep extends TimedZoneWorkStep {

    public SortBoxStep(ZoneCycleState cycle, String awaitStepId) {
        super(WarehouseZone.SORTATION, cycle, awaitStepId, "sets the outbound route gate");
    }
}
