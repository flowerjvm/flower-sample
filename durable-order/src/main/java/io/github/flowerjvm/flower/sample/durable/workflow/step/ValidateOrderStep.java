package io.github.flowerjvm.flower.sample.durable.workflow.step;

import io.github.flowerjvm.flower.sample.durable.domain.OrderRepository;

public final class ValidateOrderStep extends TimedOrderStep {

    public ValidateOrderStep(OrderRepository orders, String orderId) {
        super(orders, orderId);
    }

    @Override
    protected void apply() {
        orders.validate(orderId);
    }
}
