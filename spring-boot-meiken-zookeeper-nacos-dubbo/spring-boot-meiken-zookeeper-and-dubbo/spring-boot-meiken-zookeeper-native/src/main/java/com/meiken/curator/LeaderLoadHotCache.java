package com.meiken.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class LeaderLoadHotCache {
    private static final String CONNECT_STR = "127.0.0.1:2181";
    private static RetryPolicy retryPolicy = new ExponentialBackoffRetry(5 * 1000, 10);

    public static void main(String[] args) {

        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(CONNECT_STR, retryPolicy);
        curatorFramework.start();

        LeaderSelectorListener listener = new LeaderSelectorListenerAdapter() {
            @Override
            public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
                // leader 选举成功后会执行该逻辑
                System.out.println("load hot data to cache");
            }
        };

        LeaderSelector selector = new LeaderSelector(curatorFramework, "/cachePreHot_leader", listener);
        selector.autoRequeue();
        selector.start();
    }

    // 责任链模式
    public static void fluentStyle() {
        RetryPolicy tempRetryPolicy = new ExponentialBackoffRetry(5 * 1000, 10);

        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(CONNECT_STR)
                .sessionTimeoutMs(5000) // 会话超时时间
                .connectionTimeoutMs(5000) // 连接超时时间
                .retryPolicy(tempRetryPolicy)
                .namespace("base") // 包含隔离名称
                .build();

        // 监听连接成功事件
        curatorFramework.getConnectionStateListenable().addListener( (client, newState) -> {
            if(newState == ConnectionState.CONNECTED){
                System.out.printf("连接成功...");
            }
        });

        curatorFramework.start();
    }
}
