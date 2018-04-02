package retry;

import java.util.function.Supplier;

public interface RetryInterface<T> {
    T run(Supplier<T> method);
}
