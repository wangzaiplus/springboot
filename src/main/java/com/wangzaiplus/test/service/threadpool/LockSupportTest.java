package com.wangzaiplus.test.service.threadpool;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {

    private static final char[] a = "abcd".toCharArray();
    private static final char[] A = "ABCD".toCharArray();

    private static Thread aThread;
    private static Thread AThread;

    public static void main(String[] args) {
        aThread = new Thread(() -> {
            for (char c : a) {
                System.out.println(c);
                LockSupport.unpark(AThread);
                LockSupport.park();
            }
        });

        AThread = new Thread(() -> {
            for (char c : A) {
                LockSupport.park();
                System.out.println(c);
                LockSupport.unpark(aThread);
            }
        });

        aThread.start();
        AThread.start();
    }
}
