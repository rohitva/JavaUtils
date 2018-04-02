package retry.listener;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import retry.Retry;
import retry.example.SynRetryEventListener;
import retry.strategy.FixedIntervalRetryStrategy;

public class RetryTestWithSyncListener {
    TestClass testClass = Mockito.mock(TestClass.class);

    public class TestClass{
        public String testMe(){
            return "BalahBalah";
        }
    }

    @Test
    public void testRetryTestWithSyncListener(){
        SynRetryEventListener synRetryEventListener = SynRetryEventListener.builder("test").build();
        Retry<String> retryHelper = Retry.<String>builder(FixedIntervalRetryStrategy.builder().build())
                .retryEventListener(synRetryEventListener)
                .build();
        Mockito.when(testClass.testMe()).thenReturn("testMe");
        Assert.assertEquals("testMe", retryHelper.run(() -> testClass.testMe()));
    }
}
