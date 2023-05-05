package com.meiken.curator;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.tomcat.jni.Time;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class DistributeLockMain {
    private static final String CONNECT_STR = "127.0.0.1:2181";
    private static final String MEIKEN_DISTRIBUTE_LOCK = "/meiken_lock";
    public static void main(String[] args) throws InterruptedException {

        RetryPolicy tempRetryPolicy = new ExponentialBackoffRetry(5 * 1000, 10);

        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(CONNECT_STR)
                .sessionTimeoutMs(5000) // 会话超时时间
                .connectionTimeoutMs(5000) // 连接超时时间
                .retryPolicy(tempRetryPolicy)
                .namespace("base") // 包含隔离名称
                .build();

        CountDownLatch countDownLatch = new CountDownLatch(1);

        // 监听连接成功事件
        curatorFramework.getConnectionStateListenable().addListener( (client, newState) -> {
            if(newState == ConnectionState.CONNECTED){
                System.out.println("连接成功...");
                countDownLatch.countDown();
            }
        });
        curatorFramework.start();

        countDownLatch.await();

        // 获取锁
        InterProcessMutex interProcessMutex = new InterProcessMutex(curatorFramework, MEIKEN_DISTRIBUTE_LOCK);

        // 锁等待时间，如果没有设置，就会一直等待
        Boolean lockFlag = false;
        Long waitLockTime = 2000L;
        try {
            lockFlag = interProcessMutex.acquire(waitLockTime, TimeUnit.MILLISECONDS);
            if(lockFlag){
                // do something need lock
                Thread.sleep(50000);
            }

            // 释放锁
            interProcessMutex.release();
            lockFlag=false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            // 其他异常导致的锁没有被正常释放
            if(lockFlag){
                try {
                    interProcessMutex.release();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
