package workflow.example;

import lombok.extern.slf4j.Slf4j;
import workflow.workflowengine.WorkflowExecutor;
import workflow.workflowengine.WorkflowExecutorBuilder;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class WorkflowExecutorSample {
    public static void main(String[] args) throws InterruptedException {
        WorkflowExecutorBuilder workflowExecutorBuilder = new WorkflowExecutorBuilder();
        WorkflowExecutor workflowExecutor = workflowExecutorBuilder.build();
        List<MakingDinner> listOfWorkflow = new ArrayList<>();
        long time_1 = System.currentTimeMillis();
        for(int i=0; i< 100; i++){
            MakingDinner makingDinner = MakingDinner.builder().context(MakingDinnerContext.builder().build()).build();
            listOfWorkflow.add(makingDinner);
            workflowExecutor.submitWorkflow(makingDinner);
        }
        while(true) {
            Boolean pendingWork = Boolean.FALSE;
            for (MakingDinner makingDinner : listOfWorkflow) {
                if (!Boolean.TRUE.equals(makingDinner.getWorkflowContext().getWorkflowCompleted())) {
                    pendingWork = Boolean.TRUE;
                    break;
                }
            }
            if(!pendingWork)
            break;
        }
        long time_2 = System.currentTimeMillis();
        log.info("Number of listOfWorkflow {}", listOfWorkflow.size());
        System.out.println("We took " + (time_2 - time_1) + " to complete the execution");
        workflowExecutor.shutDown();
    }
}
