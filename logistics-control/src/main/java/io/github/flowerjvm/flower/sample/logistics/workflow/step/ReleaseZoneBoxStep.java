package io.github.flowerjvm.flower.sample.logistics.workflow.step;

import io.github.flowerjvm.flower.core.step.Step;
import io.github.flowerjvm.flower.core.step.StepContext;
import io.github.flowerjvm.flower.core.step.StepResult;
import io.github.flowerjvm.flower.observability.logging.StepLogger;
import io.github.flowerjvm.flower.sample.logistics.domain.WarehouseConveyor;
import io.github.flowerjvm.flower.sample.logistics.domain.WarehouseZone;

public final class ReleaseZoneBoxStep extends Step {

    private final WarehouseConveyor conveyor;
    private final WarehouseZone zone;
    private final ZoneCycleState cycle;
    private final String awaitStepId;

    public ReleaseZoneBoxStep(
            WarehouseConveyor conveyor,
            WarehouseZone zone,
            ZoneCycleState cycle,
            String awaitStepId
    ) {
        this.conveyor = conveyor;
        this.zone = zone;
        this.cycle = cycle;
        this.awaitStepId = awaitStepId;
    }

    @Override
    protected StepResult onTick(StepContext ctx) {
        String workOrderId = cycle.workOrderId();
        if (workOrderId == null) {
            return StepResult.goTo(awaitStepId);
        }

        conveyor.release(zone, workOrderId);
        cycle.clear();
        StepLogger.of(ReleaseZoneBoxStep.class, ctx).info(
                zone.displayName() + " released " + workOrderId);
        return StepResult.goTo(awaitStepId);
    }
}
