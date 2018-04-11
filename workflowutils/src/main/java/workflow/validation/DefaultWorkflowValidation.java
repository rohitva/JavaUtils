package workflow.validation;

import lombok.Builder;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import workflow.definition.StepParameters;
import workflow.definition.Workflow;
import workflow.definition.WorkflowParameters;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Builder
public class DefaultWorkflowValidation implements WorkflowValidation {
    public void validateWorkflow(Workflow workflow){
        String firstStep = StringUtils.EMPTY;

        for (Annotation annotation : workflow.getClass().getDeclaredAnnotations()) {
            if (annotation.annotationType().equals(WorkflowParameters.class)) {
                WorkflowParameters parameters = (WorkflowParameters) annotation;
                Validate.notBlank(parameters.firstStep(), "WorkflowParameters : firstStep is blank.");
                Validate.isTrue(parameters.endtoEndTimeOutMilliSeconds() > 0 , "WorkflowParameters : EndToEndTimeOutMilliSeconds should be greater than 0.");
                Validate.isTrue(parameters.submitToStartTimeOutMilliSeconds() >0, "WorkflowParameters : SubmitToStartTimeOutMilliSeconds should be greater than 0.");
                Validate.isTrue(parameters.submitToStartTimeOutMilliSeconds() <= parameters.endtoEndTimeOutMilliSeconds(),
                        "WorkflowParameters : SubmitToStartTimeOutMilliSeconds should be <= EndToEndTimeOutMilliSeconds.");
                firstStep = parameters.firstStep();
            }
        }

        Validate.notBlank(firstStep, "WorkflowParameters : Couldn't find a valid firstStep.");

        Method[] methods = workflow.getClass().getMethods();
        for(Method method : methods){
            for(Annotation annotation : method.getDeclaredAnnotations()){
                if(annotation.annotationType().equals(StepParameters.class)){
                    StepParameters stepParameters = (StepParameters ) annotation;
                    validateStep(stepParameters, firstStep);
                }
            }
        }
    }

    private static void validateStep(final StepParameters stepParameters, final String firstStep){
        //Exactly one first step.
        //At most one catch step.
        //Step time can't be more than workflow time.
        //Step should return specific Class
    }
}
