package io.github.flowerjvm.flower.sample.durable.workflow.step;

import io.github.flowerjvm.flower.core.step.Step;
import io.github.flowerjvm.flower.core.step.StepContext;
import io.github.flowerjvm.flower.core.step.StepResult;
import io.github.flowerjvm.flower.sample.durable.domain.OrderRepository;

public final class WaitPaymentStep extends Step {

    private final OrderRepository orders;
    private final String orderId;

    public WaitPaymentStep(OrderRepository orders, String orderId) {
        this.orders = orders;
        this.orderId = orderId;
    }

    @Override
    protected void onEnter(StepContext ctx) {
        orders.waitForPayment(orderId);
    }

    @Override
    protected StepResult onTick(StepContext ctx) {
        return orders.isPaid(orderId) ? StepResult.done() : StepResult.stay();
    }
}
