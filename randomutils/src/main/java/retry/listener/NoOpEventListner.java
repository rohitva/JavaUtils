package retry.listener;

/**
 * Created by rovashis on 4/2/18.
 */
public class NoOpEventListner implements RetryEventListener {
    @Override
    public void doOnSuccessfulEvent(Object eventObject) {
        //Do nothing
    }

    @Override
    public void doOnFailedEvent(Object eventObject) {
        //Do nothing
    }
}
