package workflow.example;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import workflow.definition.WorkflowContext;

/**
 * Created by rovashis on 4/5/18.
 */
@Getter
@Setter
@Builder
public class MakingDinnerContext implements WorkflowContext {
    String firstName;
    String lastName;
    Boolean workflowCompleted = Boolean.FALSE;

    @Override
    public String toString() {
        return "{" + firstName + " " + lastName + "}";
    }

    @Override
    public void markTheWorkFlowCompleted() {
        this.workflowCompleted = true;
    }
}
