package retry.listener;

import lombok.Builder;
import java.util.Set;

@Builder
public class RetryEventContext implements RetryEvent{
    int retryAttemptCount;
    Throwable lastException;
    Set<Throwable> allExceptions;

    @Override
    public int getRetryAttemptCount() {
        return retryAttemptCount;
    }

    @Override
    public Throwable getLastException() {
        return lastException;
    }

    @Override
    public Set<Throwable> getAllExceptions() {
        return allExceptions;
    }
}
