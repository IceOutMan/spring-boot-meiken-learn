package com.meiken.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.sql.Time;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class ReadWriteLockMain {
    private static final String CONNECT_STR = "127.0.0.1:2181";
    private static final String MEIKEN_READ_WRITE_LOCK = "/meiken_read_write_lock";

    public static void main(String[] args) throws InterruptedException {

        RetryPolicy tempRetryPolicy = new ExponentialBackoffRetry(5 * 1000, 10);

        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().connectString(CONNECT_STR).sessionTimeoutMs(5000) // 会话超时时间
                .connectionTimeoutMs(5000) // 连接超时时间
                .retryPolicy(tempRetryPolicy).namespace("base") // 包含隔离名称
                .build();

        CountDownLatch countDownLatch = new CountDownLatch(1);

        // 监听连接成功事件
        curatorFramework.getConnectionStateListenable().addListener((client, newState) -> {
            if (newState == ConnectionState.CONNECTED) {
                System.out.println("连接成功...");
                countDownLatch.countDown();
            }
        });
        curatorFramework.start();

        countDownLatch.await();

        // 读写锁
        InterProcessReadWriteLock interProcessReadWriteLock = new InterProcessReadWriteLock(curatorFramework, MEIKEN_READ_WRITE_LOCK);

        // 读 # 线程加锁
        new Thread(() -> {
            read(curatorFramework, interProcessReadWriteLock);
        }).start();

        // 写 # 线程加锁
        new Thread(() -> {
            write(curatorFramework, interProcessReadWriteLock);
        }).start();
    }

    private static void read(CuratorFramework curatorFramework, InterProcessReadWriteLock interProcessReadWriteLock) {
        // 锁等待时间，如果没有设置，就会一直等待
        InterProcessMutex readLock = null;
        Boolean readLockFlag = false;
        Long waitLockTime = 2000L;
        try {
            readLock = interProcessReadWriteLock.readLock();
            readLockFlag = readLock.acquire(waitLockTime, TimeUnit.MILLISECONDS);
            if (readLockFlag) {
                // 读
                Thread.sleep(50000);
            }

            // 释放锁
            readLock.release();
            readLockFlag = false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // 其他异常导致的锁没有被正常释放
            if (readLockFlag) {
                try {
                    readLock.release();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    private static void write(CuratorFramework curatorFramework, InterProcessReadWriteLock interProcessReadWriteLock) {
        // 锁等待时间，如果没有设置，就会一直等待
        InterProcessMutex writeLock = null;
        Boolean writeLockFlag = false;
        Long waitLockTime = 2000L;
        try {
            writeLock = interProcessReadWriteLock.writeLock();
            writeLockFlag = writeLock.acquire(waitLockTime, TimeUnit.MILLISECONDS);
            if (writeLockFlag) {
                // 写
                Thread.sleep(50000);
            }

            // 释放锁
            writeLock.release();
            writeLockFlag = false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // 其他异常导致的锁没有被正常释放
            if (writeLockFlag) {
                try {
                    writeLock.release();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

