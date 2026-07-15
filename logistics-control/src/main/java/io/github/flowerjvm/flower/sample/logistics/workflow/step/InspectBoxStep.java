package io.github.flowerjvm.flower.sample.logistics.workflow.step;

import io.github.flowerjvm.flower.sample.logistics.domain.WarehouseZone;

public final class InspectBoxStep extends TimedZoneWorkStep {

    public InspectBoxStep(ZoneCycleState cycle, String awaitStepId) {
        super(WarehouseZone.INSPECTION, cycle, awaitStepId, "scans the box code and runs weight/vision check");
    }
}
