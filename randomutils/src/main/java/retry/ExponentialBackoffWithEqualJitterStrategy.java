package retry;

import lombok.Builder;
import java.util.Random;
import java.util.Set;

/**
 * This is an implementation of Equal Jitter strategy mentioned in the AWS blog https://aws.amazon.com/blogs/architecture/exponential-backoff-and-jitter/
 */
@Builder
public class ExponentialBackoffWithEqualJitterStrategy implements RetryStrategy {
    @Builder.Default int totalRetryCount = RetryStrategyDefaults.DEFAULT_RETRY_COUNT;
    @Builder.Default long initialInterval = RetryStrategyDefaults.DEFAULT_SLEEP_INTERVAL;
    @Builder.Default boolean retryOnAllExceptions = RetryStrategyDefaults.DEFAULT_IGNORE_ALL_EXCEPTIONS;
    @Builder.Default
    Set<Class<? extends Throwable>> retryOnExceptions = RetryStrategyDefaults.DEFAULT_RETRYABLE_EXCEPTIONS;
    @Builder.Default long maxInterval = 10000;
    @Builder.Default Random random = new Random();

    @Override
    public long getWaitTime(int retryCounter) {
        long temp = (long) Math.min(maxInterval, initialInterval *  Math.pow(2, (double) (retryCounter -1)));
        long sleepTime = temp / 2 + random.nextInt(Math.max(1, (int) temp/2));
        return sleepTime;
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
