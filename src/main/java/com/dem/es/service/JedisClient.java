package com.dem.es.service;

public interface JedisClient {
    /**
     * 设置字符串
     * @param key
     * @param value
     * @return
     */
    public String set(String key, String value);

    /**
     * 获取
     * @param key
     * @return
     */
    public String get(String key);
    /**
     *设置 key 指定的哈希集中指定字段的值。

     如果 key 指定的哈希集不存在，会创建一个新的哈希集并与 key 关联。

     如果字段在哈希集中存在，它将被重写
     */
    public Long hset(String key, String item, String value);

    /**
     * 返回 key 指定的哈希集中该字段所关联的值
     * @param key
     * @param item
     * @return
     */
    public String hget(String key,String item);

    /**
     * 对存储在指定key的数值执行原子的加1操作。
     * @param key
     * @return
     */
    public Long incr(String key);

    /**
     * 对存储在指定key的数值执行原子的减1操作。
     * @param key
     * @return
     */
    public Long decr(String key);

    /**
     *设置指定key的有效期
     * @param key
     * @param second
     * @return
     */
    public Long expire(String key, int second);

    /**
     * 返回key剩余的过期时间。 这种反射能力允许Redis客户端检查指定key在数据集里面剩余的有效期。
     * @param key
     * @return
     */
    public Long ttl(String key);

    /**
     * 如果删除的key不存在，则直接忽略。
     * @param key
     * @return
     */
    public Long del(String key);

    /**
     * 从 key 指定的哈希集中移除指定的域
     * @param key
     * @param item
     * @return
     */
    public Long hdel(String key,String item);
}
