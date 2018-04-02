package retry.strategy;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.junit.Assert;
import retry.strategy.ExponentialBackoffWithEqualJitterStrategy;
import retry.strategy.FixedIntervalRetryStrategy;

public class FixedIntervalRetryStrategyTest {

    @Test
    public void testDefaultStrategy(){
        FixedIntervalRetryStrategy fixedIntervalRetryStrategy = FixedIntervalRetryStrategy.builder().build();

        //Verify wait time is constant irrespective of attempt counter.
        Assert.assertEquals(fixedIntervalRetryStrategy.getWaitTime(1), RetryStrategyDefaults.DEFAULT_SLEEP_INTERVAL);
        Assert.assertEquals(fixedIntervalRetryStrategy.getWaitTime(2), RetryStrategyDefaults.DEFAULT_SLEEP_INTERVAL);
        Assert.assertEquals(fixedIntervalRetryStrategy.getWaitTime(20), RetryStrategyDefaults.DEFAULT_SLEEP_INTERVAL);

        Assert.assertFalse(fixedIntervalRetryStrategy.shouldRetry(1, new RuntimeException()));
    }

    @Test
    public void testStrategyWaitTime(){
        FixedIntervalRetryStrategy fixedIntervalRetryStrategy = FixedIntervalRetryStrategy.builder().retryInterval(200).build();

        //Verify wait time is constant irrespective of attempt counter.
        Assert.assertEquals(fixedIntervalRetryStrategy.getWaitTime(1), 200);
        Assert.assertEquals(fixedIntervalRetryStrategy.getWaitTime(2), 200);
        Assert.assertEquals(fixedIntervalRetryStrategy.getWaitTime(20), 200);
    }

    @Test
    public void testStrategyIgnoreAllExceptions(){
        FixedIntervalRetryStrategy fixedIntervalRetryStrategy = FixedIntervalRetryStrategy.builder().retryOnAllExceptions(true).build();

        Assert.assertTrue(fixedIntervalRetryStrategy.shouldRetry(1, new RuntimeException()));
        Assert.assertTrue(fixedIntervalRetryStrategy.shouldRetry(2, new RuntimeException()));
        Assert.assertTrue(fixedIntervalRetryStrategy.shouldRetry(3, new RuntimeException()));
        //4th attempt should fail.
        Assert.assertFalse(fixedIntervalRetryStrategy.shouldRetry(4, new RuntimeException()));
    }

    @Test
    public void testStrategyIgnoreSpecificException(){
        FixedIntervalRetryStrategy fixedIntervalRetryStrategy = FixedIntervalRetryStrategy.builder()
                .retryOnExceptions(ImmutableSet.of(RuntimeException.class))
                .build();

        Assert.assertTrue(fixedIntervalRetryStrategy.shouldRetry(1, new RuntimeException()));
        Assert.assertTrue(fixedIntervalRetryStrategy.shouldRetry(2, new RuntimeException()));
        Assert.assertTrue(fixedIntervalRetryStrategy.shouldRetry(3, new RuntimeException()));
        //4th attempt should fail.
        Assert.assertFalse(fixedIntervalRetryStrategy.shouldRetry(4, new RuntimeException()));
        //First attempt with any other Exception should fail.
        Assert.assertFalse(fixedIntervalRetryStrategy.shouldRetry(1, new Exception()));
    }

    @Test
    public void testStrategyIgnoreExceptionHierarchy(){
        //Ignore same exceptions.
        Assert.assertTrue( FixedIntervalRetryStrategy.builder()
                .retryOnExceptions(ImmutableSet.of(RuntimeException.class))
                .build().shouldRetry(1, new RuntimeException()));

        //Retry exception if you have whitelisted base class
        Assert.assertTrue( FixedIntervalRetryStrategy.builder()
                .retryOnExceptions(ImmutableSet.of(Exception.class))
                .build().shouldRetry(2, new RuntimeException()));
        Assert.assertTrue( FixedIntervalRetryStrategy.builder()
                .retryOnExceptions(ImmutableSet.of(Throwable.class))
                .build().shouldRetry(2, new Exception()));


        //Doesn't retry if you have whitelisted only extended class
        Assert.assertFalse( FixedIntervalRetryStrategy.builder()
                .retryOnExceptions(ImmutableSet.of(RuntimeException.class))
                .build().shouldRetry(3, new Exception()));
        Assert.assertFalse( FixedIntervalRetryStrategy.builder()
                .retryOnExceptions(ImmutableSet.of(Exception.class))
                .build().shouldRetry(3, new Throwable()));
    }
}
