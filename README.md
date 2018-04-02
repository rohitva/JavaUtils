# JavaUtils
This package has multiple different java utilities. 

## RandomUtils 
This package has support to retry any method/API call. We have added support of three widely used retry strategies :  
**FixedIntervalRetryStrategy** , **IncrementalIntervalRetryStrategy** and **ExponentialBackoffWithEqualJitterStrategy**.
Application developer can choose which strategy they want to use and what are right parameters for their application (WaitTime, TotalRetryCount etc).
It also supports call-back for success and failure. There are examples of how the application developer can write synchronous or asynchronous call-back. 