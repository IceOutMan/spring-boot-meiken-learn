package com.meiken.operation.query;

import com.meiken.config.ClientFactory;
import org.elasticsearch.client.RestHighLevelClient;

public class QueryMain {

    public static void main(String[] args) {
        RestHighLevelClient client = ClientFactory.getClient();
    }
}
