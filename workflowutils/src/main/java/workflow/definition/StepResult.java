package workflow.definition;

import lombok.Builder;

import java.time.Duration;


@Builder
public class StepResult implements IStepResult {
    String nextStepName;
    DelayedDuration delayedDuration;

    @Override
    public String getNextStepName() {
        return nextStepName;
    }

    @Override
    public void setNextStepName(String nextStepName) {
        this.setNextStepName(nextStepName);
    }

    @Override
    public DelayedDuration getDelayedDuration() {
        return delayedDuration;
    }

    @Override
    public void setDelayedDuration(DelayedDuration delayedDuration) {
        this.delayedDuration = delayedDuration;
    }
}
