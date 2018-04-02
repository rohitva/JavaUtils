package retry.strategy;

import java.util.HashSet;
import java.util.Set;

public class RetryStrategyDefaults {
    public static final Set<Class<? extends Throwable>> DEFAULT_RETRYABLE_EXCEPTIONS = new HashSet<>();
    public static final boolean DEFAULT_IGNORE_ALL_EXCEPTIONS = false;
    public static final int DEFAULT_RETRY_COUNT = 3;
    public static final long DEFAULT_SLEEP_INTERVAL = 100;
}
