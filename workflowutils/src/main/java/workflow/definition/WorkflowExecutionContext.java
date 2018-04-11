package workflow.definition;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

/**
 * Runtime context of a running workflow.
 */
@Data
@Builder
public class WorkflowExecutionContext {
    private Workflow workflow;
    private String currentStepName;
    private IStepResult stepResult;
    private WorkflowExecutionTaskStatus executionTaskStatus;
    private Instant lastUpdatedTime;

    public enum WorkflowExecutionTaskStatus{
        SUBMITTED,
        RUNNING,
        FAILED,
        COMPLETED
    }
}
