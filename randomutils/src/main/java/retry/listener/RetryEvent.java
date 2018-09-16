package retry.listener;

import java.util.Set;

public interface RetryEvent {
    int getRetryAttemptCount();

    Throwable getLastException();

    Set<Throwable> getAllExceptions();
}
