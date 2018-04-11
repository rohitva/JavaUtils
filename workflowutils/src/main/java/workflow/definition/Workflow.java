package workflow.definition;

/**
 * Interface to define a workflow. All workflow should extend it.
 */
public interface Workflow<T extends WorkflowContext> {
  T getWorkflowContext();
}
