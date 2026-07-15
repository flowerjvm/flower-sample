package io.github.flowerjvm.flower.sample.basic.stay;

import io.github.flowerjvm.flower.sample.basic.support.RecordingFlowerListener;
import io.github.flowerjvm.flower.sample.basic.support.RuntimeTestSupport;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StayFlowSampleTest {

    @Test
    void stayStepWaitsThenMovesToFinalStep() throws Exception {
        RecordingFlowerListener listener = RuntimeTestSupport.runFlow(
                StayFlowSample.createFlow("test", 50L));

        assertThat(listener.events()).containsExactly(
                "submitted basic-stay/test",
                "entered stay-then-done",
                "exited stay-then-done",
                "entered final-step",
                "exited final-step",
                "finished basic-stay/test");
    }
}
