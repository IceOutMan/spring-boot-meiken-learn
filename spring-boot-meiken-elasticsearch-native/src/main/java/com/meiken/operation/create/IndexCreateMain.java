package com.meiken.operation.create;

import com.alibaba.fastjson.JSON;
import com.meiken.Car;
import com.meiken.EsClient;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * 创建使用
 * 1. ID 指定的话使用指定的ID
 * 2. ID 不指定的话默认生成
 */
public class IndexCreateMain {

    public static void main(String[] args) {

        // 使用指定字段格式
        createDocByField();

        // 使用JSON 格式
//        createDocByJson();

    }

    public static void createDocByField(){
        RestHighLevelClient client = EsClient.getClient();
        IndexRequest request = new IndexRequest(EsClient.INDEX_NAME);

        request.id("create_by_field");
        request.source(
                "color","red",
                "name", "CCC",
                "price", 111,
                "sales", 111,
                "district", new String[]{"中国", "美国"}
        );

        try {
            IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
            System.out.println(indexResponse);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // 以json形式为index设置字段
    public static void createDocByJson(){
        RestHighLevelClient client = EsClient.getClient();
        IndexRequest request = new IndexRequest(EsClient.INDEX_NAME);
        request.id("create_by_json");

        Car car = new Car();
        car.setColor("Blue");
        car.setName("JJJ");
        car.setPrice(222D);
        car.setSales(222L);
        car.setDistrict(new String[]{"中国"});

        request.source(JSON.toJSONString(car), XContentType.JSON);

        try {
            IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
            System.out.println(indexResponse);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}