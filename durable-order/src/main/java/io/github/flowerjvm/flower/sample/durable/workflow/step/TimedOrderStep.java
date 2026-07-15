package io.github.flowerjvm.flower.sample.durable.workflow.step;

import io.github.flowerjvm.flower.core.step.Step;
import io.github.flowerjvm.flower.core.step.StepContext;
import io.github.flowerjvm.flower.core.step.StepResult;
import io.github.flowerjvm.flower.sample.durable.domain.OrderRepository;

abstract class TimedOrderStep extends Step {

    protected final OrderRepository orders;
    protected final String orderId;

    TimedOrderStep(OrderRepository orders, String orderId) {
        this.orders = orders;
        this.orderId = orderId;
    }

    @Override
    protected StepResult onTick(StepContext ctx) {
        apply();
        return StepResult.done();
    }

    protected abstract void apply();
}
