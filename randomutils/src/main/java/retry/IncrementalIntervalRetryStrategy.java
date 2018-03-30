package retry;

import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
public class IncrementalIntervalRetryStrategy implements RetryStrategy {
    @Builder.Default int totalRetryCount = RetryStrategyDefaults.DEFAULT_RETRY_COUNT;
    @Builder.Default long initialInterval = RetryStrategyDefaults.DEFAULT_SLEEP_INTERVAL;
    @Builder.Default boolean retryOnAllExceptions = RetryStrategyDefaults.DEFAULT_IGNORE_ALL_EXCEPTIONS;
    @Builder.Default
    Set<Class<? extends Throwable>> retryOnExceptions = new HashSet<>();
    @Builder.Default long retryIntervalIncrement = 100;
    @Builder.Default long maxInterval = 10000;


    @Override
    public long getWaitTime(int retryCounter) {
        return Math.min(maxInterval, initialInterval + initialInterval * (retryCounter - 1));
    }

    @Override
    public boolean retryOnAllExceptions() {
        return retryOnAllExceptions;
    }

    @Override
    public int getTotalRetryCount() {
        return totalRetryCount;
    }

    @Override
    public Set<Class<? extends Throwable>> getRetryOnExceptions() {
        return retryOnExceptions;
    }
}
