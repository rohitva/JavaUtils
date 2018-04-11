package workflow.definition;

import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Parameters needed to define a workflow.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface WorkflowParameters {
    long endtoEndTimeOutMilliSeconds() default 1000;
    long submitToStartTimeOutMilliSeconds() default 1000;
    String firstStep() default StringUtils.EMPTY;
    String catchStep() default StringUtils.EMPTY;
}
