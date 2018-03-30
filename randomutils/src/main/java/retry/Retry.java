package retry;


import com.google.common.base.Throwables;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@AllArgsConstructor
@Slf4j
@Builder
public class Retry<T> {
    RetryStrategy retryStrategy;
    @Builder.Default SleepHelper sleepHelper = new SleepHelper();

    public T run(Supplier<T> method) {
        try {
            return method.get();
        } catch (Exception exception) {
            return reTry(method, exception);
        }
    }

    public T reTry(Supplier<T> method, Exception exception) {
        int retryCounter = 1;
        Exception exceptionThrown = exception;
        while(true){
            if(retryStrategy.shouldRetry(retryCounter, exceptionThrown)){
                sleepHelper.sleepUninterruptibly(retryStrategy.getWaitTime(retryCounter), TimeUnit.MILLISECONDS);
                try {
                    return method.get();
                } catch (Exception ex){
                    log.warn("Retry attempt {} failed with exception",retryCounter -1, ex);
                    exceptionThrown = exception;
                    retryCounter++;
                }
            } else {
                throw Throwables.propagate(exceptionThrown);
            }
        }
    }
}