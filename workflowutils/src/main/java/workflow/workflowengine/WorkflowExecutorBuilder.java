package workflow.workflowengine;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import workflow.validation.DefaultWorkflowValidation;
import workflow.validation.WorkflowValidation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

/**
 * Customized builder for WorkflowExecutor
 */
public class WorkflowExecutorBuilder {
    private int numberOfStepsThreads = 100;
    private int numberOfWorkflowThreads = 20;
    //TODO: Need to use it.
    private int maxNumberOfRunningWorkflows = 1000;
    private WorkflowValidation workflowValidation;

    public WorkflowExecutorBuilder(){

    }

    public WorkflowExecutorBuilder withNumberOfStepsThreads(int threadCount){
        this.numberOfStepsThreads = threadCount;
        return this;
    }

    public WorkflowExecutorBuilder withNumberOfWorkflowThreads(int threadCount){
        this.numberOfWorkflowThreads = threadCount;
        return this;
    }

    public WorkflowExecutorBuilder withMaxNumberOfRunningWorkflows(int maxCount){
        this.maxNumberOfRunningWorkflows = maxCount;
        return this;
    }

    public WorkflowExecutorBuilder withWorkflowValidation(WorkflowValidation workflowValidation){
        this.workflowValidation = workflowValidation;
        return this;
    }

    public WorkflowExecutor build() {
        ThreadFactory workerThreadFactory =
                new ThreadFactoryBuilder().setNameFormat("workflow-thread-%d").build();
        ThreadFactory stepThreadFactory =
                new ThreadFactoryBuilder().setNameFormat("workflowStep-thread-%d").build();
        ExecutorService stepExecutorService = Executors.newFixedThreadPool(numberOfStepsThreads, stepThreadFactory);
        ScheduledExecutorService workflowExecutorService = Executors.newScheduledThreadPool(numberOfWorkflowThreads, workerThreadFactory);
        if(workflowValidation == null){
            workflowValidation = DefaultWorkflowValidation.builder().build();
        }
        return new WorkflowExecutor(workflowValidation, stepExecutorService, workflowExecutorService);
    }
}
