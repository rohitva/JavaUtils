package retry.strategy;


import java.util.Set;

/**
 * Define the Strategy interface for retry
 */
public interface RetryStrategy {
     //Different retry strategy will mostly use a different wait time.
     long getWaitTime(int retryCounter);
     boolean retryOnAllExceptions();
     int getTotalRetryCount();
     Set<Class<? extends Throwable>> getRetryOnExceptions();

     /**
      * This method return true if exceptionThrown is either exists in set of retryOnExceptions or it's extended from any of the exceptions in the set.
      * We are doing it because if someone want to ignore all RunTimeExceptions they can just ignore RunTimeException and any exception which is an extension of RunTimeException will also be ignored.
      * @param retryOnExceptions
      * @param exceptionThrown
      * @return
      */
     default boolean shouldIgnoreException(Set<Class<? extends Throwable>> retryOnExceptions, Throwable exceptionThrown){
          if(retryOnExceptions == null){
               return false;
          }

          for(Class<? extends Throwable> throwable : retryOnExceptions){
               if(throwable.isAssignableFrom(exceptionThrown.getClass())){
                    return true;
               }
          }
          return false;
     }


     /**
      * This is a default implementation of shouldRetry method.
      * Multiple retry strategy can use the same scheme based on the reTry attempt count and exception thrown.
      * @param retryCounter
      * @param exception
      * @return
      */
     default boolean shouldRetry(int retryCounter, Throwable exception) {
          //If retryCounter is less than 0 then something gone wrong and it's better to stop
          // than retrying forever and burnout the system.
          if(retryCounter < 0 || retryCounter > getTotalRetryCount())
               return false;
          if(retryOnAllExceptions() || shouldIgnoreException(getRetryOnExceptions(), exception)){
               return true;
          }
          return false;
     }
}
