package com.meiken.operation.create;

import com.meiken.config.ClientFactory;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

public class IndexCreateMain {

    public static void main(String[] args) {
        createIndexByJson();
//        createIndexByFiled();
    }

    /**
     * {
     * "_index": "posts",
     * "_type": "_doc",
     * "_id": "2",
     * "_score": 1,
     * "_source": {
     * "name_filed": "name_value"
     * }
     * }
     */
    public static void createIndexByFiled(){
        RestHighLevelClient client = ClientFactory.getClient();
        IndexRequest request = new IndexRequest("posts");
        request.id("2");
        request.source("name_filed","name_value");

        try {
            IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
            System.out.println(indexResponse);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * {
     * "_index": "posts",
     * "_type": "_doc",
     * "_id": "1",
     * "_score": 1,
     * "_source": {
     * "user": "kimchy",
     * "postDate": "2013-01-30",
     * "message": "trying out Elasticsearch"
     * }
     * }
     */
    // 以json形式为index设置字段
    public static void createIndexByJson(){
        RestHighLevelClient client = ClientFactory.getClient();
        IndexRequest request = new IndexRequest("posts");
        request.id("1");
        String jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        request.source(jsonString, XContentType.JSON);

        try {
            IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
            System.out.println(indexResponse);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}