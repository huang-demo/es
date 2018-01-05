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
}
