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
public class AsyncRetryEventListener implements RetryEventListener<RetryEvent> {
    @NonNull
    String methodName;

    public static AsyncRetryEventListener.AsyncRetryEventListenerBuilder builder(@NonNull final String methodName) {
        Validate.notEmpty(methodName);
        return _builder().methodName(methodName);
    }

    @Override
    public void doOnSuccessfulEvent(RetryEvent eventObject) {
        // An Async task always executes in new thread
        new Thread(new Runnable() {
            public void run() {
                AsyncRetryEventListener.log.info("Call {} is successful in the retryAttempt {}", methodName, eventObject.getRetryAttemptCount());
                //We can even add Metrics over here.
            }
        }).start();

        return;
    }

    @Override
    public void doOnFailedEvent(RetryEvent eventObject) {
        // An Async task always executes in new thread
        new Thread(new Runnable() {
            public void run() {
                AsyncRetryEventListener.log.info("Call {} failed in the retryAttempt {}", methodName, eventObject.getRetryAttemptCount(), eventObject.getLastException());
                //We can even add Metrics over here.
            }
        }).start();
        return;
    }
}