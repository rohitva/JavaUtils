package workflow.workflowengine;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import workflow.definition.Workflow;
import workflow.definition.WorkflowExecutionContext;
import workflow.validation.WorkflowValidation;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//TODO : logging also how to do that in test
//TODO : Metrics
//TODO : Add unit tests
//TODO : Error handling - steps failures and retry
//TODO : Schedule a workflow (with time limitations).
@AllArgsConstructor
public class WorkflowExecutor {
    @NonNull private WorkflowValidation workflowValidation;
    @NonNull private ExecutorService stepExecutorService;
    @NonNull private ScheduledExecutorService workflowExecutorService;

    //TODO: Block more then specified number of workflows.
    //AtomicInteger currentNumberOfWorkflows;

    public void submitWorkflow(Workflow workflow){
        //First check if this is a valid workflow
        workflowValidation.validateWorkflow(workflow);
        executeWorkflow(workflow);
    }

    @SneakyThrows
    private void executeWorkflow(Workflow workflow){
        WorkflowExecutionContext workflowExecutionContext = WorkflowExecutionContext.builder()
                .executionTaskStatus(WorkflowExecutionContext.WorkflowExecutionTaskStatus.SUBMITTED)
                .workflow(workflow).build();
        WorkflowRunner workflowRunner = WorkflowRunner.builder().stepExecutorService(stepExecutorService)
                .workflowContext(workflowExecutionContext)
                .scheduledWorkflowExecutorService(workflowExecutorService).build();
        workflowExecutorService.submit(workflowRunner);
    }

    @SneakyThrows
    public void shutDown(){
        stepExecutorService.shutdown();
        workflowExecutorService.shutdown();
        if(!workflowExecutorService.awaitTermination(10, TimeUnit.SECONDS)){
            workflowExecutorService.shutdownNow();
        }
        if(!stepExecutorService.awaitTermination(1, TimeUnit.SECONDS)){
            stepExecutorService.shutdown();
        }
        System.out.println("Shutdown completed for the executor services.");
    }
}
