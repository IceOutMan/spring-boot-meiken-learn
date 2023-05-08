package com.meiken.operation.get;

import com.alibaba.fastjson.JSON;
import com.meiken.EsClient;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class GetMain {

    public static void main(String[] args) {
        RestHighLevelClient client = EsClient.getClient();

        GetRequest getRequest = new GetRequest(EsClient.INDEX_NAME);
        getRequest.id("create_by_field");

        try {
            GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
            String sourceString = getResponse.getSourceAsString();
            System.out.println(sourceString);

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
