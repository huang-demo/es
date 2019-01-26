##elasticsearch 配置
# ik插件安装[url](https://github.com/medcl/elasticsearch-analysis-ik)
- 安装命令:elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v6.0.0/elasticsearch-analysis-ik-6.0.0.zip
# pinyin插件 [url](https://github.com/medcl/elasticsearch-analysis-pinyin)
1. 项目剪下来根据自己改pom 中es版本
2. mvn打包
3. 打包好的zip 中的jar 等一并复制到es安装路径的 plugins/analysis-pinyin 目录下
x-pack 插件安装(非必须)
- 安装命令: elasticsearch-plugin install x-pack(默认账户:elastic,密码:changeme)

###master:
```$xslt
cluster.name: DEM
node.name: master
network.host: 127.0.0.1
http.port: 9200
http.cors.enabled: true 
http.cors.allow-origin: "*"
node.master: true
node.data: true
```

####saler
```$xslt
cluster.name: DEM
node.name: master
network.host: 127.0.0.1
http.port: 8200
discovery.zen.ping.unicast.hosts: ["127.0.0.1"]
node.master: false
node.data: true
http.cors.enabled: true
http.cors.allow-origin: "*"
```

    
    
 #kibana
 x-pack 插件安装(非必须)
 - 安装命令: kibana-plugin install x-pack
 配置
 ```$xslt
server.port: 5601
server.host: "127.0.0.1"
elasticsearch.url: "http://127.0.0.1:9200"
#elasticsearch.username: "elastic"
#elasticsearch.password: "changeme"
```
localhost:5601
查询所有索引
GET _cat/indices

lostash
