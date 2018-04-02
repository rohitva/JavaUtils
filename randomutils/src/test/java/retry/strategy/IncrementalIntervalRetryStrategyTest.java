package retry.strategy;

import com.google.common.collect.ImmutableSet;
import org.junit.Assert;
import org.junit.Test;
public class IncrementalIntervalRetryStrategyTest {

    @Test
    public void testDefaultStrategy(){
       IncrementalIntervalRetryStrategy incrementalIntervalRetryStrategy = IncrementalIntervalRetryStrategy.builder().build();

        //Verify wait time changes with attempt counter. Also it has an upper bound.
        Assert.assertEquals(incrementalIntervalRetryStrategy.getWaitTime(1),
                100);
        Assert.assertEquals(incrementalIntervalRetryStrategy.getWaitTime(2),
                RetryStrategyDefaults.DEFAULT_SLEEP_INTERVAL + incrementalIntervalRetryStrategy.getRetryIntervalIncrement() * 1);
        Assert.assertEquals(incrementalIntervalRetryStrategy.getWaitTime(200),
                incrementalIntervalRetryStrategy.getMaxInterval());

        Assert.assertFalse(incrementalIntervalRetryStrategy.shouldRetry(1, new RuntimeException()));
    }

    @Test
    public void testStrategyIgnoreAllExceptions(){
        IncrementalIntervalRetryStrategy incrementalIntervalRetryStrategy = IncrementalIntervalRetryStrategy.builder().retryOnAllExceptions(true).build();

        Assert.assertTrue(incrementalIntervalRetryStrategy.shouldRetry(1, new RuntimeException()));
        Assert.assertTrue(incrementalIntervalRetryStrategy.shouldRetry(2, new RuntimeException()));
        Assert.assertTrue(incrementalIntervalRetryStrategy.shouldRetry(3, new RuntimeException()));
        //4th attempt should fail.
        Assert.assertFalse(incrementalIntervalRetryStrategy.shouldRetry(4, new RuntimeException()));
    }

    @Test
    public void testStrategyIgnoreAllExceptionsAndOverrideAttemptCount(){
        IncrementalIntervalRetryStrategy incrementalIntervalRetryStrategy = IncrementalIntervalRetryStrategy.builder()
                .retryOnAllExceptions(true)
                .totalRetryCount(2).build();

        Assert.assertTrue(incrementalIntervalRetryStrategy.shouldRetry(1, new RuntimeException()));
        Assert.assertTrue(incrementalIntervalRetryStrategy.shouldRetry(2, new RuntimeException()));
        //3rd attempt should fail.
        Assert.assertFalse(incrementalIntervalRetryStrategy.shouldRetry(3, new RuntimeException()));
    }

    @Test
    public void testStrategyIgnoreSpecificException(){
        IncrementalIntervalRetryStrategy incrementalIntervalRetryStrategy = IncrementalIntervalRetryStrategy.builder()
                .retryOnExceptions(ImmutableSet.of(RuntimeException.class))
                .build();

        Assert.assertTrue(incrementalIntervalRetryStrategy.shouldRetry(1, new RuntimeException()));
        Assert.assertTrue(incrementalIntervalRetryStrategy.shouldRetry(2, new RuntimeException()));
        Assert.assertTrue(incrementalIntervalRetryStrategy.shouldRetry(3, new RuntimeException()));
        //4th attempt should fail.
        Assert.assertFalse(incrementalIntervalRetryStrategy.shouldRetry(4, new RuntimeException()));
        //First attempt with any other Exception should fail.
        Assert.assertFalse(incrementalIntervalRetryStrategy.shouldRetry(1, new Exception()));
    }

    @Test
    public void testStrategyIgnoreExceptionHierarchy(){
        //Ignore same exceptions.
        Assert.assertTrue(IncrementalIntervalRetryStrategy.builder()
                .retryOnExceptions(ImmutableSet.of(RuntimeException.class))
                .build().shouldRetry(1, new RuntimeException()));

        //Retry exception if you have whitelisted base class
        Assert.assertTrue(IncrementalIntervalRetryStrategy.builder()
                .retryOnExceptions(ImmutableSet.of(Exception.class))
                .build().shouldRetry(2, new RuntimeException()));
        Assert.assertTrue( FixedIntervalRetryStrategy.builder()
                .retryOnExceptions(ImmutableSet.of(Throwable.class))
                .build().shouldRetry(2, new Exception()));


        //Doesn't retry if you have whitelisted only extended class
        Assert.assertFalse(IncrementalIntervalRetryStrategy.builder()
                .retryOnExceptions(ImmutableSet.of(RuntimeException.class))
                .build().shouldRetry(3, new Exception()));
        Assert.assertFalse(IncrementalIntervalRetryStrategy.builder()
                .retryOnExceptions(ImmutableSet.of(Exception.class))
                .build().shouldRetry(3, new Throwable()));
    }
}
