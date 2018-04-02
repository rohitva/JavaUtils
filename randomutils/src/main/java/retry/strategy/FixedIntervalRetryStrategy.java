package retry.strategy;

import lombok.Builder;
import java.util.Set;

@Builder
public class FixedIntervalRetryStrategy implements RetryStrategy {
    @Builder.Default int totalRetryCount = RetryStrategyDefaults.DEFAULT_RETRY_COUNT;
    @Builder.Default long retryInterval = RetryStrategyDefaults.DEFAULT_SLEEP_INTERVAL;
    @Builder.Default boolean retryOnAllExceptions = RetryStrategyDefaults.DEFAULT_IGNORE_ALL_EXCEPTIONS;
    @Builder.Default Set<Class<? extends Throwable>> retryOnExceptions = RetryStrategyDefaults.DEFAULT_RETRYABLE_EXCEPTIONS;

    @Override
    public long getWaitTime(int retryCounter) {
        return retryInterval;
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
