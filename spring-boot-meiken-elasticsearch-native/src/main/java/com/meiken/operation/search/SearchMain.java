package com.meiken.operation.search;

import com.meiken.EsClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * Search 可以进行 复杂查询
 */
public class SearchMain {


    public static void main(String[] args) {
        RestHighLevelClient client = EsClient.getClient();

        SearchRequest searchRequest = new SearchRequest();


    }

}
