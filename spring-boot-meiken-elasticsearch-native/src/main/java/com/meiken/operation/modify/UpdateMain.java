package com.meiken.operation.modify;

import com.alibaba.fastjson.JSON;
import com.meiken.Car;
import com.meiken.EsClient;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * 更新文档
 * 1. 需要ID
 * 2. 指定更新
 */
public class UpdateMain {

    public static void main(String[] args) {
//        updateByField();

        updateByJson();
    }

    public static void updateByField() {
        RestHighLevelClient client = EsClient.getClient();
        UpdateRequest updateRequest = new UpdateRequest(EsClient.INDEX_NAME, "create_by_field");
        updateRequest.doc("name", "upsert name by field", "color", "Black");

        try {
            client.update(updateRequest, RequestOptions.DEFAULT);

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void updateByJson(){
        RestHighLevelClient client = EsClient.getClient();
        UpdateRequest updateRequest = new UpdateRequest(EsClient.INDEX_NAME, "create_by_json");

        Car car = new Car();
        car.setName("json udpate car");
        car.setColor("gray");
        updateRequest.doc( JSON.toJSONString(car), XContentType.JSON);

        try {
            client.update(updateRequest, RequestOptions.DEFAULT);

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
