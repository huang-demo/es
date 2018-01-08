package com.dem.es.service;

import java.util.List;
import java.util.Map;

public interface TransactionsService {
    /**
     * 统计各颜色的数量
     * @param size
     * @return
     */
    List<Map<String,Object>> getCountsByColor(int size);

    /**
     * 获取各汽车产商平均价格
     * @return
     */
    List<Map<String,Object>> getAvgPriceByMake(int size);

    /**
     * 按颜色统计每个汽车生成商计算最低和最高的价格
     * @return
     */
    List<Map<String,Object>> getAvgAndMinPriceByColorAndMake(int size);

    /**
     * 按月统计总和
     * @return
     */
    List<Map<String,Object>> getAmountByMonth();

    /**
     * 获取各价格区间的数量
     * @param interval
     * @return
     */
    List<Map<String,Object>> getPriceHistogram(double interval);

    /**
     * 获取个颜色的极值
     * @param size
     * @return
     */
    List<Map<String,Object>> getMinMaxPriceByColor(int size);

    /**
     *
     * @param size
     * @return
     */
    List<Map<String,Object>> getStatasByColors(int size);

    /**
     * 获取全局平均值于福特产商的平均价
     * @return
     */
    Map<String,Object> getFordAvgAndGlobelAvg();

    /**
     * $10,000 美元之上的所有汽车同时也为这些车计算平均售价
     * @param minPrice
     * @return
     */
    Map<String,Object> getGlobalAvg(double minPrice);

    /**
     * 查询 福特汽车并统计价格在20000以上的平均售价
     * @param minPrice
     * @return
     */
    Map<String,Object> getFordAndAvg(double minPrice);

    /**
     * 获取经销商数量
     * @return
     */
    long getMakeCount();

    /**
     * 每月有多少颜色的车被售出
     * @return
     */
    List<Map<String,Object>> getColorCountHistogram4Month();
}
