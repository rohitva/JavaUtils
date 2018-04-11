package workflow.definition;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Parameters to define a workflow step.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface StepParameters {
    long endtoEndTimeOutMilliSeconds() default 500;
    @NonNull String stepName();
    //TODO: Schedule executions
    //TODO: Delay between execution
    //TODO: Retry policy of a step or number of retries
}
