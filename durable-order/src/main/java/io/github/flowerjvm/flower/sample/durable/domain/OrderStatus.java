package io.github.flowerjvm.flower.sample.durable.domain;

public enum OrderStatus {
    SUBMITTED,
    VALIDATED,
    WAITING_PAYMENT,
    PAID,
    INVENTORY_RESERVED,
    SHIPPED,
    COMPLETED
}
