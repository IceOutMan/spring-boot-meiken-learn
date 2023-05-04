package com.meiken.operation.search;

import com.alibaba.fastjson.JSON;
import com.meiken.EsClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.PipelineAggregatorBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Cardinality;
import org.elasticsearch.search.aggregations.metrics.CardinalityAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.MaxAggregationBuilder;
import org.elasticsearch.search.aggregations.pipeline.BucketSortPipelineAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.util.*;

/**
 *  聚合 -> 子聚合
 */
public class AggSortPageSearch {

    public static void main(String[] args) {
        Integer pageNum = 2;
        Integer pageSize =10 ;

        try {
            RestHighLevelClient client = EsClient.getClient();
            SearchRequest searchRequest = new SearchRequest(EsClient.INDEX_NAME);

            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

            // 设置 size 为 0，只返回聚合结果
            searchSourceBuilder.size(0);

            // 构建 query
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

            // 有效的数据
            boolQueryBuilder.filter(QueryBuilders.termQuery("data_valid_flag", true));
            // 满足阈值范围的数据
            boolQueryBuilder.filter(QueryBuilders.termQuery("over_threshold_range_flag", true));

            // 或查询
            BoolQueryBuilder firstOrSecondBoolQueryBuilder = QueryBuilders.boolQuery();
            firstOrSecondBoolQueryBuilder.should(QueryBuilders.termsQuery("first_in_charge_id", Arrays.asList(new int[]{1})));
            firstOrSecondBoolQueryBuilder.should(QueryBuilders.termsQuery("second_in_charge_id",Arrays.asList(new int[]{1})));
            boolQueryBuilder.filter(firstOrSecondBoolQueryBuilder);


            // 正序asc/倒序desc

            // 构建 cardinality 聚合 - 用来获取总数
            CardinalityAggregationBuilder albumsDistinctAgg = AggregationBuilders.cardinality("albums_distinct").field("album_id");

            //  构建 外层 album_id 聚合
            TermsAggregationBuilder albumsAgg = AggregationBuilders.terms("albums").field("album_id").size(Integer.MAX_VALUE);
            // 构建内部 max 子的聚合
            MaxAggregationBuilder maxPlayDeviceCntNumAgg = AggregationBuilders.max("max_num").field("");

            // 构建 bucket_sort 子聚合
            List<FieldSortBuilder> fieldSorts = new ArrayList<>();
            fieldSorts.add(new FieldSortBuilder("max_num").order(SortOrder.DESC));

            BucketSortPipelineAggregationBuilder sortPageAgg = PipelineAggregatorBuilders.bucketSort("sort_pipeline_agg", fieldSorts).from((pageNum - 1) * pageSize).size(pageSize);

            // 查询条件
            searchSourceBuilder.query(boolQueryBuilder);

            // 将子聚合添加到顶层聚合中
            albumsAgg.subAggregation(maxPlayDeviceCntNumAgg);
            albumsAgg.subAggregation(sortPageAgg);

            // searchSource 构建外层聚合
            searchSourceBuilder.aggregation(albumsDistinctAgg);
            searchSourceBuilder.aggregation(albumsAgg);

            searchRequest.source(searchSourceBuilder);

            // 发起搜索请求
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            // 处理聚合结果
            Cardinality albumsDistinct = searchResponse.getAggregations().get("albums_distinct");
            Long total = albumsDistinct.getValue();

            Terms albums = searchResponse.getAggregations().get("albums");
            for (Terms.Bucket albumBucket : albums.getBuckets()) {
                System.out.println(JSON.toJSONString(albumBucket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
