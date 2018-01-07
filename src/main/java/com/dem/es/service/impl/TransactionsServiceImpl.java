package com.dem.es.service.impl;

import com.dem.es.service.TransactionsService;
import com.dem.es.util.Constant;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filter.InternalFilter;
import org.elasticsearch.search.aggregations.bucket.global.Global;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.histogram.InternalDateHistogram;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.aggregations.metrics.min.Min;
import org.elasticsearch.search.aggregations.metrics.stats.Stats;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionsServiceImpl implements TransactionsService {
    @Autowired
    private TransportClient client;

    /**
     * {
     * "aggs": {
     * "agg_color": {
     * "terms": {
     * "field": "color",
     * "size": 2
     * }
     * }
     * },
     * "size": 0
     * }
     *
     * @param size
     * @return
     */
    @Override
    public List<Map<String, Object>> getCountsByColor(int size) {
        List<Map<String, Object>> resList = new ArrayList<>();
        SearchRequestBuilder searchRequestBuilder = getTransactionSearchReq(0)
                .addAggregation(AggregationBuilders.terms("agg_color")
                        .size(size).field("color"));
        SearchResponse response = searchRequestBuilder.get();
        Terms agg = response.getAggregations().get("agg_color");
        Map<String, Object> curMap = null;
        for (Terms.Bucket bucket : agg.getBuckets()) {
            curMap = new HashMap<>();
            curMap.put("color", bucket.getKey());
            curMap.put("count", bucket.getDocCount());
            resList.add(curMap);
        }
        return resList;
    }

    /**
     * {
     * "size": 0,
     * "aggs": {
     * "colors": {
     * "terms": {
     * "field": "color"
     * },
     * "aggs": {
     * "avgPrice": {
     * "avg": {
     * "field": "price"
     * }
     * },
     * "amount": {
     * "sum": {
     * "field": "price"
     * }
     * },
     * "minPrice": {
     * "min": {
     * "field": "price"
     * }
     * },
     * "maxPrice": {
     * "max": {
     * "field": "price"
     * }
     * }
     * }
     * }
     * }
     * }
     *
     * @param size
     * @return
     */
    @Override
    public List<Map<String, Object>> getMinMaxPriceByColor(int size) {
        List<Map<String, Object>> resList = new ArrayList<>();
        SearchRequestBuilder searchRequestBuilder = getTransactionSearchReq(0);
        searchRequestBuilder.addAggregation(AggregationBuilders.terms("colors").field("color").size(size)
                .subAggregation(AggregationBuilders.avg("avgPrice").field("price"))
                .subAggregation(AggregationBuilders.min("minPrice").field("price"))
                .subAggregation(AggregationBuilders.max("maxPrice").field("price")));
        SearchResponse response = searchRequestBuilder.get();
        Terms terms = response.getAggregations().get("colors");
        for (Terms.Bucket bucket : terms.getBuckets()) {
            Map<String, Object> cur = new HashMap<>();
            cur.put("color", bucket.getKeyAsString());
            cur.put("count", bucket.getDocCount());
            Avg avg = bucket.getAggregations().get("avgPrice");
            cur.put("avgPrice", avg.getValue());
            Min min = bucket.getAggregations().get("minPrice");
            cur.put("minPrice", min.getValue());
            Max max = bucket.getAggregations().get("maxPrice");
            cur.put("maxPrice", max.getValue());
            resList.add(cur);
        }
        return resList;
    }


    @Override
    public List<Map<String, Object>> getStatasByColors(int size) {
        List<Map<String, Object>> resList = new ArrayList<>();
        SearchRequestBuilder searchRequestBuilder = getTransactionSearchReq(0);
        searchRequestBuilder.addAggregation(AggregationBuilders.terms("colors")
                .field("color").size(size)
                .subAggregation(AggregationBuilders.stats("statsVal").field("price")));
        SearchResponse response = searchRequestBuilder.get();
        Terms terms = response.getAggregations().get("colors");
        for (Terms.Bucket bucket : terms.getBuckets()) {
            Map<String, Object> cur = new HashMap<>();
            cur.put("color", bucket.getKeyAsString());
            cur.put("count", bucket.getDocCount());
            Stats stats = bucket.getAggregations().get("statsVal");
            cur.put("avgPrice", stats.getAvg());
            cur.put("minPrice", stats.getMin());
            cur.put("maxPrice", stats.getMax());
            cur.put("sum", stats.getSum());
            resList.add(cur);
        }
        return resList;
    }

    /**
     * {
     * "size":0,
     * "aggs":{
     * "make":{
     * "terms":{
     * "field":"make",
     * "size":10
     * },"aggs":{
     * "avg_price":{
     * "avg":{
     * "field":"price"
     * }
     * }
     * }
     * }
     * }
     * }
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> getAvgPriceByMake(int size) {
        List<Map<String, Object>> resList = new ArrayList<>();
        SearchRequestBuilder searchRequestBuilder = getTransactionSearchReq(0);
        searchRequestBuilder.addAggregation(AggregationBuilders.terms("makes").field("make").size(size)
                .subAggregation(AggregationBuilders.avg("avg_price").field("price")));
        SearchResponse response = searchRequestBuilder.get();
        Terms agg = response.getAggregations().get("makes");
        Map<String, Object> curMap = null;
        for (Terms.Bucket bucket : agg.getBuckets()) {
            curMap = new HashMap<>();
            curMap.put("make", bucket.getKey());
            curMap.put("count", bucket.getDocCount());
            Avg avgPrice = bucket.getAggregations().get("avg_price");
            curMap.put("avg", avgPrice.getValue());
            resList.add(curMap);
        }
        return resList;
    }

    /**
     * {
     * "aggs": {
     * "color": {
     * "terms": {
     * "field": "color"
     * },
     * "aggs": {
     * "agg_price": {
     * "avg": {
     * "field": "price"
     * }
     * },
     * "make": {
     * "terms": {
     * "field": "make"
     * },
     * "aggs": {
     * "agg_minprice": {
     * "min": {
     * "field": "price"
     * }
     * },
     * "agg_maxPrice": {
     * "max": {
     * "field": "price"
     * }
     * }
     * }
     * }
     * }
     * }
     * },
     * "size": 0
     * }
     *
     * @param size
     * @return
     */
    @Override
    public List<Map<String, Object>> getAvgAndMinPriceByColorAndMake(int size) {
        List<Map<String, Object>> resList = new ArrayList<>();
        SearchRequestBuilder searchRequestBuilder = getTransactionSearchReq(0);
        searchRequestBuilder.addAggregation(AggregationBuilders.terms("color")
                .size(size)
                .field("color")
                .subAggregation(AggregationBuilders.avg("avg_price").field("price"))
                .subAggregation(AggregationBuilders.terms("maker").field("make")
                        .subAggregation(AggregationBuilders.min("min_price").field("price"))
                        .subAggregation(AggregationBuilders.max("max_price").field("price"))));

        SearchResponse response = searchRequestBuilder.get();
        Terms agg = response.getAggregations().get("color");
        Map<String, Object> curMap = null;
        Map<String, Object> cur = null;
        for (Terms.Bucket bucket : agg.getBuckets()) {
            curMap = new HashMap<>();
            curMap.put("color", bucket.getKey());
            curMap.put("count", bucket.getDocCount());
            Avg avg = bucket.getAggregations().get("avg_price");
            curMap.put("avgPrice", avg.getValue());
            Terms makerAggs = bucket.getAggregations().get("maker");
            List<Map<String, Object>> makeList = new ArrayList<>();
            for (Terms.Bucket makerAgg : makerAggs.getBuckets()) {
                cur = new HashMap<>();
                cur.put("make", makerAgg.getKey());
                cur.put("count", makerAgg.getDocCount());
                Min min = makerAgg.getAggregations().get("min_price");
                cur.put("minPrice", min.getValue());
                Max max = makerAgg.getAggregations().get("max_price");
                cur.put("maxPrice", max.getValue());
                makeList.add(cur);
            }
            curMap.put("makers", makeList);
            resList.add(curMap);
        }
        return resList;
    }

    /**
     * {
     * "size": 0,
     * "aggs": {
     * "months": {
     * "date_histogram": {
     * "field": "sold",
     * "interval": "month",
     * "format": "yyyy-MM-dd",
     * "min_doc_count": 0,
     * "extended_bounds": {
     * "min": "2014-01-01",
     * "max": "2014-12-31"
     * }
     * },"aggs":{
     * "sumPrice":{
     * "sum":{
     * "field":"price"
     * }
     * }
     * }
     * }
     * }
     * }
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> getAmountByMonth() {
        List<Map<String, Object>> resList = new ArrayList<>();
        SearchRequestBuilder searchRequestBuilder = getTransactionSearchReq(0);
        searchRequestBuilder.addAggregation(AggregationBuilders
                .dateHistogram("months")
                .dateHistogramInterval(DateHistogramInterval.MONTH)
                .field("sold")
                .format("yyyy-MM-dd")
                .subAggregation(AggregationBuilders.sum("amount")
                        .field("price")));
        SearchResponse response = searchRequestBuilder.get();
        InternalDateHistogram terms = response.getAggregations().get("months");
        Map<String, Object> curMap = null;
        for (InternalDateHistogram.Bucket bucket : terms.getBuckets()) {
            curMap = new HashMap<>();
            curMap.put("month", bucket.getKeyAsString());
            curMap.put("count", bucket.getDocCount());
            Sum sum = bucket.getAggregations().get("amount");
            curMap.put("amount", sum.getValue());
            resList.add(curMap);
        }
        return resList;
    }

    /**
     * {"size":0,"aggs":{"prices":{"histogram":{"field":"price","interval":20000,"min_doc_count":0},"aggs":{"amount":{"sum":{"field":"price"}}}}}}
     *
     * @param interval
     * @return
     */
    @Override
    public List<Map<String, Object>> getPriceHistogram(double interval) {
        List<Map<String, Object>> resList = new ArrayList<>();
        SearchRequestBuilder searchRequestBuilder = getTransactionSearchReq(0);
        searchRequestBuilder.addAggregation(AggregationBuilders
                .histogram("prices")
                .field("price")
                .interval(interval)
                .minDocCount(0)
                .subAggregation(AggregationBuilders
                        .sum("amount")
                        .field("price")));
        SearchResponse response = searchRequestBuilder.get();
        Histogram prices = response.getAggregations().get("prices");
        double min = 0D;
        Map<String, Object> curMap = null;
        for (Histogram.Bucket bucket : prices.getBuckets()) {
            curMap = new HashMap<>();
            min = Double.valueOf(bucket.getKey().toString());
            curMap.put("name", "[" + min + "," + (min + interval) + ")");
            curMap.put("count", bucket.getDocCount());
            Sum sum = bucket.getAggregations().get("amount");
            curMap.put("amount", sum.getValue());
            resList.add(curMap);
        }
        return resList;
    }

    /**
     * {"query":{"match":{"make":"ford"}},"size":0,"aggs":{"fordAvg":{"avg":{"field":"price"}},"avg":{"global":{},"aggs":{"avgPrice":{"avg":{"field":"price"}}}}}}
     *
     * @return
     */
    @Override
    public Map<String, Object> getFordAvgAndGlobelAvg() {
        SearchRequestBuilder searchRequestBuilder = getTransactionSearchReq(0);
        searchRequestBuilder.setQuery(QueryBuilders.matchQuery("make", "ford"))
                .addAggregation(AggregationBuilders.avg("fordAvgPrice").field("price"))
                .addAggregation(AggregationBuilders.global("global")
                        .subAggregation(AggregationBuilders.avg("avgPrice").field("price")));

        SearchResponse response = searchRequestBuilder.get();
        Avg fordAvg = response.getAggregations().get("fordAvgPrice");
        Global global = response.getAggregations().get("global");
        Avg globalAvg = global.getAggregations().get("avgPrice");
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("fordAvg", fordAvg.getValue());
        resMap.put("globalAvg", globalAvg.getValue());
        return resMap;
    }

    /**
     * {"query":{"bool":{"filter":{"range":{"price":{"gte":10000}}}}},"aggs":{"avgPrice":{"avg":{"field":"price"}}}}
     *
     * @param minPrice
     * @return
     */
    @Override
    public Map<String, Object> getGlobalAvg(double minPrice) {
        SearchRequestBuilder searchReq = getTransactionSearchReq(0);
        searchReq.setQuery(QueryBuilders.boolQuery()
                .filter(QueryBuilders.rangeQuery("price")
                        .gt(minPrice)))
                .addAggregation(AggregationBuilders.avg("avgPrice")
                        .field("price"));
        SearchResponse res = searchReq.get();

        Avg avg = res.getAggregations().get("avgPrice");
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("avgPrie", avg.getValue());
        return resMap;
    }

    /**
     * {"query":{"bool":{"filter":{"match":{"make":"ford"}}}},"aggs":{"priceGt20000":{"filter":{"range":{"price":{"gt":20000}}},"aggs":{"avgPrice":{"avg":{"field":"price"}}}}}}     * @param minPrice
     *
     * @return
     */
    @Override
    public Map<String, Object> getFordAndAvg(double minPrice) {
        SearchRequestBuilder req = getTransactionSearchReq(0);
        req.setQuery(QueryBuilders.boolQuery().filter(QueryBuilders.matchQuery("make", "ford")));
        req.addAggregation(AggregationBuilders.filter("priceFord", QueryBuilders
                .rangeQuery("price").gte(minPrice))
                .subAggregation(AggregationBuilders.avg("avgPrice").field("price")));
        SearchResponse res = req.get();
        InternalFilter priceFord = res.getAggregations().get("priceFord");
        Avg avg = priceFord.getAggregations().get("avgPrice");
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("fordAvgPrice", avg.getValue());
        return resMap;
    }

    private SearchRequestBuilder getTransactionSearchReq(int resSize) {
        return client.prepareSearch(Constant.ELASTIC_INDEX_CATS).setTypes(Constant.ELASTIC_TYPE_TRANSACTIONS).setSize(resSize);
    }
}
