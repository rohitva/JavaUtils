package retry.listener;

/**
 * In case of any call/retry is successful or fails the caller might be interested in reacting to this event.
 * The caller might add more logs, metrics, notifications specific to their use case. RetryEventListener interface supports the same.
 */
public interface RetryEventListener<T> {
    // this can be any type of method
    void doOnSuccessfulEvent(T eventObject);

    void doOnFailedEvent(T eventObject);
}
