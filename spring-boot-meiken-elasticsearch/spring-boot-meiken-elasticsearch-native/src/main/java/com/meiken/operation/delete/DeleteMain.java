package com.meiken.operation.delete;


import com.alibaba.fastjson.JSON;
import com.meiken.EsClient;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class DeleteMain {

    public static void main(String[] args) {
        RestHighLevelClient client = EsClient.getClient();

        DeleteRequest deleteRequest = new DeleteRequest(EsClient.INDEX_NAME);
        deleteRequest.id("create_by_field");


        try {
            DeleteResponse delResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
            System.out.println(JSON.toJSONString(delResponse));

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
