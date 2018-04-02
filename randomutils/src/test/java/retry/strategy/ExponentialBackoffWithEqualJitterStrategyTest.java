package retry.strategy;

import com.google.common.collect.ImmutableSet;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import retry.Retry;

import java.util.Set;

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

    /**
     * TODO : Add tests to verify how long we sleep between retry attempts.
     */
    public static class RetryTest {
        TestClass testClass = Mockito.mock(TestClass.class);

        public class TestClass{
            public String testMe(){
                return "BalahBalah";
            }
        }

        /**** FixedIntervalRetryStrategy tests *******/

        @Test
        public void testRetryWithFixedIntervalPolicySuccessFirstTime(){
            Retry<String> retryHelper = Retry.<String>builder().retryStrategy(FixedIntervalRetryStrategy.builder().build()).build();
            Mockito.when(testClass.testMe()).thenReturn("testMe");
            Assert.assertEquals("testMe", retryHelper.run(() -> testClass.testMe()));
        }

        @Test
        public void testRetryWithFixedIntervalPolicySuccessKeepFailing() {
            Retry<String> retryHelper = Retry.<String>builder().retryStrategy(FixedIntervalRetryStrategy.builder()
                    .retryOnAllExceptions(true)
                    .retryInterval(1).build()).build();
            Mockito.when(testClass.testMe()).thenThrow(new RuntimeException("boom boom"));
            Assertions.assertThatThrownBy(() -> { retryHelper.run(() -> testClass.testMe()); }).isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("boom");
        }

        @Test
        public void testRetryWithFixedIntervalPolicyFailTwoTimesAndSuccessfull() {
            Retry<String> retryHelper = Retry.<String>builder().retryStrategy(FixedIntervalRetryStrategy.builder()
                    .retryInterval(1).retryOnAllExceptions(true).build()).build();
            Mockito.when(testClass.testMe()).thenThrow(new RuntimeException("boom boom"))
                    .thenThrow(new RuntimeException("Boom again"))
                    .thenReturn("FinallySuccessful");
            Assert.assertEquals("FinallySuccessful", retryHelper.run(() -> testClass.testMe()));
        }

        /**** IncrementalIntervalRetryStrategy tests *******/

        @Test
        public void testRetryWithIncrementalIntervalPolicySuccessFirstTime(){
            Retry<String> retryHelper = Retry.<String>builder().retryStrategy(IncrementalIntervalRetryStrategy.builder().build()).build();
            Mockito.when(testClass.testMe()).thenReturn("testMe");
            Assert.assertEquals("testMe", retryHelper.run(() -> testClass.testMe()));
        }

        @Test
        public void testRetryWithIncrementalIntervalPolicySuccessKeepFailing() {
            Retry<String> retryHelper = Retry.<String>builder().retryStrategy(IncrementalIntervalRetryStrategy.builder()
                    .retryOnAllExceptions(true)
                    .initialInterval(1).retryIntervalIncrement(1).build()).build();
            Mockito.when(testClass.testMe()).thenThrow(new RuntimeException("boom boom"));
            Assertions.assertThatThrownBy(() -> { retryHelper.run(() -> testClass.testMe()); }).isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("boom");
        }

        @Test
        public void testRetryWithIncrementalIntervalPolicyFailTwoTimesAndSuccessfull() {
            Retry<String> retryHelper = Retry.<String>builder().retryStrategy(IncrementalIntervalRetryStrategy.builder()
                    .initialInterval(1).retryIntervalIncrement(1).retryOnAllExceptions(true).build()).build();
            Mockito.when(testClass.testMe()).thenThrow(new RuntimeException("boom boom"))
                    .thenThrow(new RuntimeException("Boom again"))
                    .thenReturn("FinallySuccessful");
            Assert.assertEquals("FinallySuccessful", retryHelper.run(() -> testClass.testMe()));
        }

        /*** ExponentialBackoffWithEqualJitterStrategy tests ***/

        @Test
        public void testRetryWithExponentialPolicySuccessFirstTime(){
            Retry<String> retryHelper = Retry.<String>builder().retryStrategy(ExponentialBackoffWithEqualJitterStrategy.builder().build()).build();
            Mockito.when(testClass.testMe()).thenReturn("testMe");
            Assert.assertEquals("testMe", retryHelper.run(() -> testClass.testMe()));
        }

        @Test
        public void testRetryWithExponentialPolicySuccessKeepFailing() {
            Retry<String> retryHelper = Retry.<String>builder().retryStrategy(ExponentialBackoffWithEqualJitterStrategy.builder()
                    .retryOnAllExceptions(true)
                    .initialInterval(1).build()).build();
            Mockito.when(testClass.testMe()).thenThrow(new RuntimeException("boom boom"));
            Assertions.assertThatThrownBy(() -> { retryHelper.run(() -> testClass.testMe()); }).isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("boom");
        }

        @Test
        public void testRetryWithExponentialPolicyFailTwoTimesAndSuccessfull() {
            Retry<String> retryHelper = Retry.<String>builder().retryStrategy(ExponentialBackoffWithEqualJitterStrategy.builder()
                    .initialInterval(1).retryOnAllExceptions(true).build()).build();
            Mockito.when(testClass.testMe()).thenThrow(new RuntimeException("boom boom"))
                    .thenThrow(new RuntimeException("Boom again"))
                    .thenReturn("FinallySuccessful");
            Assert.assertEquals("FinallySuccessful", retryHelper.run(() -> testClass.testMe()));
        }

        @Test
        public void testDefineNewStrategy(){
            Retry<String> retryHelper = Retry.<String>builder().retryStrategy(new RetryStrategy() {
                @Override
                public long getWaitTime(int retryCounter) {
                    return 0;
                }

                @Override
                public boolean retryOnAllExceptions() {
                    return false;
                }

                @Override
                public int getTotalRetryCount() {
                    return 3;
                }

                @Override
                public Set<Class<? extends Throwable>> getRetryOnExceptions() {
                    return null;
                }
            }).build();

            Mockito.when(testClass.testMe()).thenThrow(new RuntimeException("boom boom"))
                    .thenThrow(new RuntimeException("Boom again"))
                    .thenReturn("FinallySuccessful");
            Assertions.assertThatThrownBy(() -> { retryHelper.run(() -> testClass.testMe()); }).isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("boom boom");
        }
    }
}
