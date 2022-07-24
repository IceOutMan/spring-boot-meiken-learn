package com.meiken.springbootmeikenelasticsearch;

import org.assertj.core.util.Maps;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@SpringBootTest
class SpringBootMeikenElasticsearchApplicationTests {

    private String INDEX_NAME = "person";
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    void initIndexTest() {

        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            IndexRequest indexRequest = new IndexRequest();
            indexRequest.index(INDEX_NAME);
            indexRequest.id(String.valueOf(i));
            indexRequest.opType(DocWriteRequest.OpType.INDEX);

            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("id",i);
            jsonMap.put("name", i+" random name");
            jsonMap.put("age", random.nextInt(10));
            jsonMap.put("info", "this is random info " + i);
            indexRequest.source(jsonMap);
            try {
                restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
