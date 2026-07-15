package io.github.flowerjvm.flower.sample.basic.finishfail;

import io.github.flowerjvm.flower.sample.basic.support.RecordingFlowerListener;
import io.github.flowerjvm.flower.sample.basic.support.RuntimeTestSupport;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FinishFailFlowSampleTest {

    @Test
    void finishStopsFlowWithoutRunningLaterSteps() throws Exception {
        RecordingFlowerListener listener = RuntimeTestSupport.runFlow(
                FinishFlowSample.createFlow("test"));

        assertThat(listener.events()).containsExactly(
                "submitted basic-finish/test",
                "entered finish-now",
                "exited finish-now",
                "finished basic-finish/test");
    }

    @Test
    void failStopsFlowAsFailedWithoutRunningLaterSteps() throws Exception {
        RecordingFlowerListener listener = RuntimeTestSupport.runFlow(
                FailFlowSample.createFlow("test"));

        assertThat(listener.events()).containsExactly(
                "submitted basic-fail/test",
                "entered fail-now",
                "exited fail-now",
                "failed basic-fail/test");
    }
}
