package workflow.example;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import workflow.definition.DelayedDuration;
import workflow.definition.StepParameters;
import workflow.definition.StepResult;
import workflow.definition.Workflow;
import workflow.definition.WorkflowParameters;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Builder
@WorkflowParameters(endtoEndTimeOutMilliSeconds = 1000L, submitToStartTimeOutMilliSeconds = 200L, firstStep = "FIRST_STEP", catchStep = "CATCH_STEP")
@Data
@Slf4j
public class MakingDinner implements Workflow<MakingDinnerContext> {
    private static final String FIRST_STEP = "FIRST_STEP";
    private static final String SECOND_STEP = "SECOND_STEP";
    private static final String THIRD_STEP = "THIRD_STEP";
    private static final String FOURTH_STEP = "FOURTH_STEP";
    private static final String LAST_STEP = "LAST_STEP";
    private static final String CATCH_STEP = "CATCH_STEP";
    private static final int MAX_SLEEP_IN_BETWEEN_MS = 200;
    private static final int TIME_TO_RECHECK_STATUS_MS = 2;
    private static final Random random = new Random();

    private MakingDinnerContext context;

    public MakingDinnerContext getWorkflowContext() {
        return context;
    }

    @StepParameters(endtoEndTimeOutMilliSeconds = 200, stepName = FIRST_STEP)
    public StepResult firstStep(){
        context.setFirstName("Ankit");
        context.setLastName("Sharma");
        log.info("Hii Welcome to workflow service. Cooking Dinner for {} ", context);
        sleepForSomeTime();
        return StepResult.builder().nextStepName(SECOND_STEP).build();
    }

    @StepParameters(endtoEndTimeOutMilliSeconds = 200, stepName = SECOND_STEP)
    public StepResult secondStep(){
        context.setFirstName("Peter");
        context.setLastName("Ken");
        log.info("Hii Welcome to workflow service. Cooking Dinner for {} ", context);
        sleepForSomeTime();
        return StepResult.builder().nextStepName(THIRD_STEP).build();
    }

    @StepParameters(endtoEndTimeOutMilliSeconds = 200, stepName = THIRD_STEP)
    public StepResult thirdStep(){
        context.setFirstName("Mark");
        context.setLastName("Hanary");
        log.info("Hii Welcome to workflow service. Cooking Dinner for {} ", context);
        sleepForSomeTime();
        return StepResult.builder().nextStepName(FOURTH_STEP).delayedDuration(getDelayedTime()).build();
    }

    @StepParameters(endtoEndTimeOutMilliSeconds = 200, stepName = FOURTH_STEP)
    public StepResult fourthStep(){
        context.setFirstName("Tony");
        context.setLastName("Stark");
        log.info("Hii Welcome to workflow service. Cooking Dinner for {} ", context);
        return StepResult.builder().nextStepName(LAST_STEP).delayedDuration(getDelayedTime()).build();
    }

    @StepParameters(endtoEndTimeOutMilliSeconds = 200, stepName = LAST_STEP)
    public StepResult lastStep(){
        log.info("Hurray your workflow completed successfully.");
        sleepForSomeTime();
        context.markTheWorkFlowCompleted();
        return StepResult.builder().build();
    }

    @SneakyThrows
    private void sleepForSomeTime(){
        //We sleep for a random amount of time as we don't want all of the sleeping executors to wake up at the same time.
        Thread.sleep(MAX_SLEEP_IN_BETWEEN_MS / 2 + random.nextInt(MAX_SLEEP_IN_BETWEEN_MS / 2));
    }

    private DelayedDuration getDelayedTime(){
        return DelayedDuration.builder().delayTime(2000).timeUnit(TimeUnit.MILLISECONDS).build();
    }

}
