package com.wangzaiplus.test.service.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolFactory {

    private static final int testCorePoolSize = 2;
    private static final int testMaxPoolSize = 4;
    private static final long testKeepAliveTime = 2;
    private static final int testBlockingQueueSize = 20;

    public static ThreadPoolExecutor testThreadPool() {
        return new ThreadPoolExecutor(testCorePoolSize,
                testMaxPoolSize,
                testKeepAliveTime,
                TimeUnit.SECONDS, new ArrayBlockingQueue(testBlockingQueueSize));
    }

}
