package com.meiken;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class EsClient {

    public final static String INDEX_NAME = "meiken_car";
    public final static RestHighLevelClient client;

    static {
        // 集群的话配置多个
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                )
        );
    }

    public static RestHighLevelClient getClient() {
        return client;
    }
}
