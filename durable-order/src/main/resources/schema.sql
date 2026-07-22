CREATE TABLE IF NOT EXISTS flower_flow_checkpoint (
    flow_type TEXT NOT NULL,
    flow_key TEXT NOT NULL,
    state TEXT NOT NULL,
    current_step_id TEXT,
    current_step_no INTEGER NOT NULL,
    current_step_entered INTEGER NOT NULL CHECK (current_step_entered IN (0, 1)),
    persistence TEXT NOT NULL,
    worker_name TEXT,
    updated_at_millis INTEGER NOT NULL,
    definition_version TEXT,
    tenant_id TEXT,
    user_id TEXT,
    session_id TEXT,
    run_id TEXT,
    trace_id TEXT,
    correlation_id TEXT,
    PRIMARY KEY (flow_type, flow_key)
);

CREATE INDEX IF NOT EXISTS idx_flower_checkpoint_active
    ON flower_flow_checkpoint (state, updated_at_millis);

CREATE INDEX IF NOT EXISTS idx_flower_checkpoint_worker_active
    ON flower_flow_checkpoint (worker_name, state, updated_at_millis);

CREATE INDEX IF NOT EXISTS idx_flower_checkpoint_tenant_active
    ON flower_flow_checkpoint (tenant_id, state, updated_at_millis);

CREATE INDEX IF NOT EXISTS idx_flower_checkpoint_run
    ON flower_flow_checkpoint (run_id);

CREATE TABLE IF NOT EXISTS sample_order (
    order_id TEXT PRIMARY KEY,
    status TEXT NOT NULL,
    payment_received INTEGER NOT NULL CHECK (payment_received IN (0, 1)),
    inventory_reserved INTEGER NOT NULL CHECK (inventory_reserved IN (0, 1)),
    shipped INTEGER NOT NULL CHECK (shipped IN (0, 1)),
    completed INTEGER NOT NULL CHECK (completed IN (0, 1)),
    created_at TEXT NOT NULL,
    updated_at TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS sample_order_step_timer (
    order_id TEXT PRIMARY KEY,
    step_id TEXT NOT NULL,
    started_at_millis INTEGER NOT NULL,
    FOREIGN KEY (order_id) REFERENCES sample_order (order_id)
);

CREATE TABLE IF NOT EXISTS sample_audit_event (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    audit_type TEXT NOT NULL,
    message TEXT NOT NULL,
    created_at TEXT NOT NULL
);
