package retry;

import com.google.common.base.Throwables;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import retry.helper.SleepHelper;
import retry.listener.NoOpEventListner;
import retry.listener.RetryEventContext;
import retry.listener.RetryEventListener;
import retry.strategy.RetryStrategy;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@AllArgsConstructor
@Slf4j
@Builder(builderMethodName = "_builder")
public class Retry<T> implements RetryInterface<T> {
    @NonNull
    RetryStrategy retryStrategy;
    @Builder.Default
    SleepHelper sleepHelper = new SleepHelper();
    @Builder.Default
    RetryEventListener retryEventListener = new NoOpEventListner();

    public static RetryBuilder builder(@NonNull final RetryStrategy retryStrategy) {
        Validate.notNull(retryStrategy);
        return _builder().retryStrategy(retryStrategy);
    }


    @Override
    public T run(Supplier<T> method) {
        try {
            T output = method.get();
            retryEventListener.doOnSuccessfulEvent(RetryEventContext.builder().retryAttemptCount(0).build());
            return output;
        } catch (Throwable exception) {
            retryEventListener.doOnFailedEvent(RetryEventContext.builder().retryAttemptCount(0).lastException(exception).build());
            return reTry(method, exception);
        }
    }

    private T reTry(Supplier<T> method, Throwable exception) {
        int retryCounter = 1;
        Throwable exceptionThrown = exception;
        Set<Throwable> allException = new HashSet<>();
        allException.add(exception);

        while (true) {
            if (retryStrategy.shouldRetry(retryCounter, exceptionThrown)) {
                sleepHelper.sleepUninterruptibly(retryStrategy.getWaitTime(retryCounter), TimeUnit.MILLISECONDS);
                try {
                    T output = method.get();
                    retryEventListener.doOnSuccessfulEvent(RetryEventContext.builder().retryAttemptCount(retryCounter).allExceptions(allException).build());
                    return output;
                } catch (Throwable newException) {
                    allException.add(newException);
                    retryEventListener.doOnFailedEvent(RetryEventContext.builder().retryAttemptCount(0).lastException(newException).allExceptions(allException).build());
                    exceptionThrown = newException;
                    retryCounter++;
                }
            } else {
                throw Throwables.propagate(exceptionThrown);
            }
        }
    }
}