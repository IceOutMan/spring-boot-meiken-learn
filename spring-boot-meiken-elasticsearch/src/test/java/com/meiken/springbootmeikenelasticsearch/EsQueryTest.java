package com.meiken.springbootmeikenelasticsearch;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author glf
 * @Date 2022/7/24
 */
@SpringBootTest
public class EsQueryTest {

    private String INDEX_NAME = "person";
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    

}
