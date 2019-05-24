//package com.wangzaiplus.test.controller;
//
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.concurrent.TimeUnit;
//
//@RestController
//@RequestMapping("/redisson")
//public class RedissonController {
//
//    @Autowired
//    private RedissonClient redissonClient;
//
//    @RequestMapping("testRedisson")
//    public String testRedisson() {
//        RLock lock = redissonClient.getLock("testRedissonLock");
//        boolean locked = false;
//        try {
//            locked = lock.tryLock(15, 10, TimeUnit.SECONDS);
//            if (locked) {
//                Thread.sleep(30000);
//                return "ok.......................................";
//            } else {
//                return "获取锁失败.......................................";
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            return "获取锁异常.......................................";
//        } finally {
//            if (!locked) {
//                return "获取锁失败";
//            }
//            lock.unlock();
//        }
//
////        try {
////            lock.lock(30, TimeUnit.SECONDS);
////            lock.lock();
////            Thread.sleep(50000);
////        } catch (Exception e) {
////            e.printStackTrace();
////        } finally {
////            lock.unlock();
////        }
//    }
//
//}
