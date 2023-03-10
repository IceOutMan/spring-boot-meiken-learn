package com.meiken.springbootmeikenzookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class LeaderLoadHotCache {
    private final static String CONNECT_STR = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    // 支持不同的重试策略
    private static RetryPolicy retryPolicy = new ExponentialBackoffRetry(5 * 1000, 10);

    public static void main(String[] args) {

        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(CONNECT_STR, retryPolicy);
        curatorFramework.start();

        // leader 选举成功后会执行的逻辑
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


    public static void fluentStyle() {
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(CONNECT_STR)
                .sessionTimeoutMs(5000) // 会话超时时间
                .connectionTimeoutMs(5000) // 连接超时时间
                .retryPolicy(retryPolicy)
                .namespace("base") // 包含隔离名称
                .build();

        client.start();

    }
}
