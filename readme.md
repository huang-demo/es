## 环境:
1. java8
2. mysql
3. elastic:9300,9301
4. rabbitmq :virtual-host=dev
5. redis

## 技术实现:
1. 实现mysql数据到es的同步存储,
```aidl
监听器同步entity结构-> elastic mappings
定时器->数据增量同步,
mq ->单个更新删除操作
```

2. swagger 接口api 文档  /swagger-ui.html
3. es高级查询聚合函数实现
4. jpa 



    
