package workflow.definition;

public interface IStepResult {
    //Get and set the next step. If this is last step of workflow then don't set it.
    String getNextStepName();
    void setNextStepName(String nextStepName);
    //Get and set the delayed duration for next step. If you want to immediately execute the next step then don't set it.
    DelayedDuration getDelayedDuration();
    void setDelayedDuration(DelayedDuration delayedDuration);
}
