package workflow.workflowengine;

import lombok.Builder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import workflow.definition.StepParameters;
import workflow.definition.StepResult;
import workflow.definition.Workflow;
import workflow.definition.WorkflowExecutionContext;
import workflow.definition.WorkflowParameters;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import static workflow.definition.WorkflowExecutionContext.WorkflowExecutionTaskStatus.COMPLETED;
import static workflow.definition.WorkflowExecutionContext.WorkflowExecutionTaskStatus.FAILED;
import static workflow.definition.WorkflowExecutionContext.WorkflowExecutionTaskStatus.RUNNING;
import static workflow.definition.WorkflowExecutionContext.WorkflowExecutionTaskStatus.SUBMITTED;

//TODO: Workflow and step time outs.
//TODO: Workflow and steps retry and failures.
//TODO: Support for Metrics
@Builder
@Slf4j
public class WorkflowRunner implements Runnable {
    @NonNull
    WorkflowExecutionContext workflowContext;
    @NonNull
    ExecutorService stepExecutorService;
    @NonNull
    ScheduledExecutorService scheduledWorkflowExecutorService;

    @Override
    public void run() {
        if(workflowContext.getExecutionTaskStatus() == SUBMITTED){
            //this is first step of workflow.
            workflowContext.setExecutionTaskStatus(RUNNING);
            log.debug("Moving workflow {} to running state.", workflowContext);
            executeStep(workflowContext.getWorkflow(), getFirstMethod(workflowContext.getWorkflow()));
        }
        if(workflowContext.getExecutionTaskStatus() == FAILED ||
                workflowContext.getExecutionTaskStatus() == COMPLETED){
            //There are no more steps. Just mark the workflow completed.
            log.debug("Workflow {} completed.", workflowContext);
            return;
        } else {
            executeStep(workflowContext);
        }
    }

    private void executeStep(WorkflowExecutionContext workflowExecutionContext){
        Method method = getMethod(workflowContext.getWorkflow(), workflowContext.getStepResult().getNextStepName());
        //TODO: Will be used for delayed Execution
        //Execute the next step.
        log.debug("Moving workflow {} to nextStep ", workflowContext);
        if(workflowExecutionContext.getStepResult().getDelayedDuration() !=null){
            scheduledWorkflowExecutorService.schedule(() -> {
                executeStep(workflowContext.getWorkflow(), getMethod(workflowContext.getWorkflow(), workflowContext.getStepResult().getNextStepName()));
            }, workflowExecutionContext.getStepResult().getDelayedDuration().getDelayTime(),
                    workflowExecutionContext.getStepResult().getDelayedDuration().getTimeUnit());
        } else{
            executeStep(workflowContext.getWorkflow(), method);
        }
    }

    private void executeStep(Workflow workflow, Method method){
        CompletableFuture.supplyAsync(() -> {
            try {
                StepResult stepResult =  (StepResult) method.invoke(workflow);
                log.debug("stepResult after the execution {}", stepResult);
                if(StringUtils.isBlank(stepResult.getNextStepName())){
                        workflowContext.setExecutionTaskStatus(COMPLETED);
                    } else {
                        workflowContext.setStepResult(StepResult.builder().nextStepName(stepResult.getNextStepName())
                        .delayedDuration(stepResult.getDelayedDuration()).build());
                }
                return stepResult.getNextStepName();
            } catch (IllegalAccessException e) {
                log.error(e.getLocalizedMessage());
                    e.printStackTrace();
            } catch (InvocationTargetException e) {
                log.error(e.getLocalizedMessage());
                e.printStackTrace();
             }
             return null;
        }, stepExecutorService).thenRunAsync(this::run, scheduledWorkflowExecutorService);
    }

    private Method getFirstMethod(Workflow workflow){
        for (Annotation annotation : workflow.getClass().getDeclaredAnnotations()) {
            if (annotation.annotationType().equals(WorkflowParameters.class)) {
                WorkflowParameters parameters = (WorkflowParameters) annotation;
                return getMethod(workflow, parameters.firstStep());
            }
        }
        return null;
    }

    private Method getMethod(Workflow workflow, String methodName){
        Method[] methods = workflow.getClass().getMethods();
        for(Method method : methods){
            for(Annotation annotation : method.getDeclaredAnnotations()){
                if(annotation.annotationType().equals(StepParameters.class)){
                    StepParameters stepParameters = (StepParameters ) annotation;
                    if(stepParameters.stepName().equals(methodName)){
                        return method;
                    }
                }
            }
        }
        return null;
    }
}
