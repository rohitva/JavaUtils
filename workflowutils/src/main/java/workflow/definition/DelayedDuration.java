package workflow.definition;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.concurrent.TimeUnit;

@Builder
@Data
public class DelayedDuration {
    @NonNull long delayTime;
    @NonNull
    @Builder.Default TimeUnit timeUnit = TimeUnit.MILLISECONDS;
}
