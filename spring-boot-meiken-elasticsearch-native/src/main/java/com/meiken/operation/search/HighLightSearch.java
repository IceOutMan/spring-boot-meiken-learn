package com.meiken.operation.search;

import com.alibaba.fastjson.JSON;
import com.meiken.EsClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;

import java.io.IOException;

public class HighLightSearch {
    public static void main(String[] args) {

        RestHighLevelClient client = EsClient.getClient();
        SearchRequest searchRequest = new SearchRequest(EsClient.INDEX_NAME);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();


        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("will match content", "title", "name");
        searchSourceBuilder.query(multiMatchQueryBuilder);

        HighlightBuilder highlightBuilder = new HighlightBuilder() ;
        highlightBuilder.field("title");
        highlightBuilder.field("name");
        highlightBuilder.preTags("<font color='red'>");
        highlightBuilder.postTags("</font>");
        searchSourceBuilder.highlighter(highlightBuilder);

        searchRequest.source(searchSourceBuilder);

        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println(JSON.toJSONString(searchResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
