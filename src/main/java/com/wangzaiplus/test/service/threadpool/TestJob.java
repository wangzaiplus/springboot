package com.wangzaiplus.test.service.threadpool;

import java.util.concurrent.CountDownLatch;

public class TestJob implements Runnable{

    private CountDownLatch countDownLatch;

    public TestJob(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            System.out.println("开始干活...");
            Thread.sleep(3000);
            Test.a = Test.a + 1;
            System.out.println("干完了.a: " + Test.a + ", " + countDownLatch.getCount());
            countDownLatch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
