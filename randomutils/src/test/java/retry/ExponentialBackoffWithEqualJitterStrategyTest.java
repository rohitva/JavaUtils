package retry;

import com.google.common.collect.ImmutableSet;
import org.junit.Assert;
import org.junit.Test;

public class ExponentialBackoffWithEqualJitterStrategyTest {

    @Test
    public void testDefaultStrategy(){
        ExponentialBackoffWithEqualJitterStrategy exponentialBackoffWithEqualJitterStrategy = ExponentialBackoffWithEqualJitterStrategy.builder().build();

        int i =0;
        //As these tests using some random jitter it's better to run multiple times.
        while(i<100){
            //Verify wait time changes with attempt counter. Also it has an upper bound.
            Assert.assertTrue(exponentialBackoffWithEqualJitterStrategy.getWaitTime(1) <= 100 &&
                    exponentialBackoffWithEqualJitterStrategy.getWaitTime(1) >= 50);

            Assert.assertTrue(exponentialBackoffWithEqualJitterStrategy.getWaitTime(2) <= 200 &&
                    exponentialBackoffWithEqualJitterStrategy.getWaitTime(2) >= 100);

            Assert.assertTrue(exponentialBackoffWithEqualJitterStrategy.getWaitTime(3) <= 400 &&
                    exponentialBackoffWithEqualJitterStrategy.getWaitTime(3) >= 200);

            Assert.assertTrue(exponentialBackoffWithEqualJitterStrategy.getWaitTime(4) <= 800 &&
                    exponentialBackoffWithEqualJitterStrategy.getWaitTime(4) >= 400);


            Assert.assertTrue(exponentialBackoffWithEqualJitterStrategy.getWaitTime(30) <= 10000 &&
                    exponentialBackoffWithEqualJitterStrategy.getWaitTime(30) >= 5000);

            Assert.assertFalse(exponentialBackoffWithEqualJitterStrategy.shouldRetry(1, new RuntimeException()));
            i++;
        }
    }

    @Test
    public void testStrategyIgnoreAllExceptions(){
        ExponentialBackoffWithEqualJitterStrategy backoffWithEqualJitterStrategy = ExponentialBackoffWithEqualJitterStrategy.builder().retryOnAllExceptions(true).build();

        Assert.assertTrue(backoffWithEqualJitterStrategy.shouldRetry(1, new RuntimeException()));
        Assert.assertTrue(backoffWithEqualJitterStrategy.shouldRetry(2, new RuntimeException()));
        Assert.assertTrue(backoffWithEqualJitterStrategy.shouldRetry(3, new RuntimeException()));
        //4th attempt should fail.
        Assert.assertFalse(backoffWithEqualJitterStrategy.shouldRetry(4, new RuntimeException()));
    }

    @Test
    public void testStrategyIgnoreAllExceptionsAndOverrideAttemptCount(){
        ExponentialBackoffWithEqualJitterStrategy backoffWithEqualJitterStrategy = ExponentialBackoffWithEqualJitterStrategy.builder()
                .retryOnAllExceptions(true)
                .totalRetryCount(2).build();

        Assert.assertTrue(backoffWithEqualJitterStrategy.shouldRetry(1, new RuntimeException()));
        Assert.assertTrue(backoffWithEqualJitterStrategy.shouldRetry(2, new RuntimeException()));
        //3rd attempt should fail.
        Assert.assertFalse(backoffWithEqualJitterStrategy.shouldRetry(3, new RuntimeException()));
    }

    @Test
    public void testStrategyIgnoreSpecificException(){
        ExponentialBackoffWithEqualJitterStrategy backoffWithEqualJitterStrategy = ExponentialBackoffWithEqualJitterStrategy.builder()
                .retryOnExceptions(ImmutableSet.of(RuntimeException.class))
                .build();

        Assert.assertTrue(backoffWithEqualJitterStrategy.shouldRetry(1, new RuntimeException()));
        Assert.assertTrue(backoffWithEqualJitterStrategy.shouldRetry(2, new RuntimeException()));
        Assert.assertTrue(backoffWithEqualJitterStrategy.shouldRetry(3, new RuntimeException()));
        //4th attempt should fail.
        Assert.assertFalse(backoffWithEqualJitterStrategy.shouldRetry(4, new RuntimeException()));
        //First attempt with any other Exception should fail.
        Assert.assertFalse(backoffWithEqualJitterStrategy.shouldRetry(1, new Exception()));
    }

    @Test
    public void testStrategyIgnoreExceptionHierarchy(){
        //Ignore same exceptions.
        Assert.assertTrue( ExponentialBackoffWithEqualJitterStrategy.builder()
                .retryOnExceptions(ImmutableSet.of(RuntimeException.class))
                .build().shouldRetry(1, new RuntimeException()));

        //Retry exception if you have whitelisted base class
        Assert.assertTrue( ExponentialBackoffWithEqualJitterStrategy.builder()
                .retryOnExceptions(ImmutableSet.of(Exception.class))
                .build().shouldRetry(2, new RuntimeException()));
        Assert.assertTrue( ExponentialBackoffWithEqualJitterStrategy.builder()
                .retryOnExceptions(ImmutableSet.of(Throwable.class))
                .build().shouldRetry(2, new Exception()));


        //Doesn't retry if you have whitelisted only extended class
        Assert.assertFalse( ExponentialBackoffWithEqualJitterStrategy.builder()
                .retryOnExceptions(ImmutableSet.of(RuntimeException.class))
                .build().shouldRetry(3, new Exception()));
        Assert.assertFalse( ExponentialBackoffWithEqualJitterStrategy.builder()
                .retryOnExceptions(ImmutableSet.of(Exception.class))
                .build().shouldRetry(3, new Throwable()));
    }
}
