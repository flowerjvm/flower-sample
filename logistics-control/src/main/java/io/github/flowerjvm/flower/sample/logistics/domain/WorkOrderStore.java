package io.github.flowerjvm.flower.sample.logistics.domain;

import java.util.Collection;

public interface WorkOrderStore {

    WorkOrder accept(WorkOrder workOrder);

    WorkOrder transition(String workOrderId, WorkOrderStatus next);

    WorkOrder find(String workOrderId);

    Collection<WorkOrder> findAll();
}
