package io.github.flowerjvm.flower.sample.cafe.workflow.step;

import io.github.flowerjvm.flower.core.step.Step;
import io.github.flowerjvm.flower.core.step.StepContext;
import io.github.flowerjvm.flower.core.step.StepResult;
import io.github.flowerjvm.flower.observability.logging.StepLogger;
import io.github.flowerjvm.flower.sample.cafe.domain.CafeOrderStore;

public final class CompleteOrderStep extends Step {

    private final CafeOrderStore store;

    public CompleteOrderStep(CafeOrderStore store) {
        this.store = store;
    }

    @Override
    protected StepResult onTick(StepContext ctx) {
        String orderId = ctx.flowId().flowKey();
        store.complete(orderId);
        StepLogger.of(CompleteOrderStep.class, ctx).info("order completed");
        return StepResult.done();
    }
}
