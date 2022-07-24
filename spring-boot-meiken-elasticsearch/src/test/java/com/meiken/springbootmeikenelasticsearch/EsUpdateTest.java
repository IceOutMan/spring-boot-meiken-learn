package com.meiken.springbootmeikenelasticsearch;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @Author glf
 * @Date 2022/7/24
 */
@SpringBootTest
public class EsUpdateTest {

    private String INDEX_NAME = "person";
    @Autowired
    private RestHighLevelClient restHighLevelClient;


    @Test
    void updateIndexTest(){
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(INDEX_NAME); // index
        updateRequest.id("2"); // document id

        updateRequest.doc("name","wang wu two");

        try {
            UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            System.out.println(JSON.toJSONString(updateResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    void updateIndexWithListenerTest() throws InterruptedException {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(INDEX_NAME); // index
        updateRequest.id("2"); // document id

        updateRequest.doc("name","wang wu th");

        ActionListener listener = new ActionListener<UpdateResponse>() {

            @Override
            public void onResponse(UpdateResponse updateResponse) {
                System.out.println(JSON.toJSONString(updateResponse));
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println(JSON.toJSONString(e));
            }
        };

        restHighLevelClient.updateAsync(updateRequest, RequestOptions.DEFAULT, listener);

        Thread.sleep(2000);
    }
}
