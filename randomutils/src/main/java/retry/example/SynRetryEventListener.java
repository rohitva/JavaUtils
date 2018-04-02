package retry.example;

import lombok.Builder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import retry.listener.RetryEvent;
import retry.listener.RetryEventListener;

/**
 * An example of asynchronous event listener.
 */
@Slf4j
@Builder(builderMethodName = "_builder") //
public class SynRetryEventListener implements RetryEventListener<RetryEvent> {
    @NonNull String methodName;

    public static SynRetryEventListenerBuilder builder(@NonNull final String methodName) {
        Validate.notEmpty(methodName);
        return _builder().methodName("my default value"); // Replace Builder constructor with _builder()
    }

    @Override
    public void doOnSuccessfulEvent(RetryEvent eventObject) {
        log.info("Call {} is successful in the retryAttempt {}", methodName, eventObject.getRetryAttemptCount());
        //We can even add Metrics over here.
        return;
    }

    @Override
    public void doOnFailedEvent(RetryEvent eventObject) {
        log.info("Call {} failed in the retryAttempt {}", methodName, eventObject.getRetryAttemptCount(), eventObject.getLastException());
        //We can even add Metrics over here.
        return;
    }
}
