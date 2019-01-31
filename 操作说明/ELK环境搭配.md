##elasticsearch 配置
# ik插件安装[url](https://github.com/medcl/elasticsearch-analysis-ik)
- 安装命令:elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v6.0.0/elasticsearch-analysis-ik-6.0.0.zip
# pinyin插件 [url](https://github.com/medcl/elasticsearch-analysis-pinyin)
1. 项目剪下来根据自己改pom 中es版本
2. mvn打包
3. 打包好的zip 中的jar 等一并复制到es安装路径的 plugins/analysis-pinyin 目录下
x-pack 插件安装(非必须)
- 安装命令: elasticsearch-plugin install x-pack(默认账户:elastic,密码:changeme)
4.创建ES用户和组（创建elsearch用户组及elsearch用户），因为使用root用户执行ES程序，将会出现错误；所以这里需要创建单独的用户去执行ES 文件；命令如下：
```
groupadd es
useradd es -g es
chown -R es:es /usr/local/es 
```
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


## 常见错误
- JVM虚拟机内存不足

错误：“JavaHotSpot(TM) 64-Bit Server VM warning: INFO: error='Cannotallocate memory' (errno=12)”表示内存不足，
其配置文件为config目录下的jvm.options,默认为2g，可以修改为1g。

 - max_map_count过小
错误“max virtual memory areas vm.max_map_count [65530]is too low, increase to at least [262144]”，max_map_count文件包含限制一个进程可以拥有的VMA(虚拟内存区域)的数量，系统默认是65530，修改成262144。
解决方法是修改/etc/sysctl.conf配置文件，
```jshelllanguage
vm.max_map_count=262144
```
记得需要重启机器才起作用，修改后配置如下图所示：

 - max file descriptors过小
 错误“max file descriptors [65535] for elasticsearchprocess is too low, increase to at least [65536]”，maxfile descriptors为最大文件描述符，设置其大于65536即可。
 解决方法是修改/etc/security/limits.conf文件，
 添加
 ```
* - nofile 65536 
* - memlock unlimited
 ```
 ，“*”表示给所有用户起作用，修改后的配置如下图所示：
