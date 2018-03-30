package retry;


import com.google.common.util.concurrent.Uninterruptibles;

import java.util.concurrent.TimeUnit;

public class SleepHelper {
    public void sleepUninterruptibly(long sleepTime, TimeUnit unit){
        Uninterruptibles.sleepUninterruptibly(sleepTime, unit);
    }
}
