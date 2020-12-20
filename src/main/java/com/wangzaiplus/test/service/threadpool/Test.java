package com.wangzaiplus.test.service.threadpool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

public class Test {

    public static volatile int a = 0;

    public static void main(String[] args) {
        doJob();
    }

    private static void doJob() {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        ThreadPoolExecutor pool = ThreadPoolFactory.testThreadPool();
        for (int i = 0; i < 5; i++) {
            System.out.println(a);
            if (a >= 3) {
                System.out.println("over");
                break;
            }

            pool.execute(new TestJob(countDownLatch));
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pool.shutdown();

        System.out.println("doJob end");
    }

}
