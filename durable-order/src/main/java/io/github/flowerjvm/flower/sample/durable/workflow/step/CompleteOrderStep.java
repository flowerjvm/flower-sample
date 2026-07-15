package io.github.flowerjvm.flower.sample.durable.workflow.step;

import io.github.flowerjvm.flower.sample.durable.domain.OrderRepository;

public final class CompleteOrderStep extends TimedOrderStep {

    public CompleteOrderStep(OrderRepository orders, String orderId) {
        super(orders, orderId);
    }

    @Override
    protected void apply() {
        orders.complete(orderId);
    }
}
