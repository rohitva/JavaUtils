package retry;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;
import org.junit.Assert;
import retry.strategy.ExponentialBackoffWithEqualJitterStrategy;
import retry.strategy.FixedIntervalRetryStrategy;
import retry.strategy.IncrementalIntervalRetryStrategy;

/**
 * TODO : Add tests to verify how long we sleep between retry attempts.
 */
public class RetryTest {
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
}
